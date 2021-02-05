package com.sk.downloadimage.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.thread.GlobalThreadPool;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.thread.lock.LockUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.sk.downloadimage.base.Constants;
import com.sk.downloadimage.bean.ComicBean;
import com.sk.downloadimage.bean.ConfigBean;
import com.sk.downloadimage.features.main.DownloadListener;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadUtils {
    private static final Log log = LogFactory.get("下载工具");
    private ConfigBean configBean;
    private DownloadListener downloadListener;

    private volatile AtomicInteger downloadBookThreadNum = new AtomicInteger(0);
    public void startDownload(DownloadListener listener) {
        downloadListener = listener;
        configBean = ConfigUtils.getConfig();
        GlobalThreadPool.init();
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
        String[] comicUrls = new FileReader(configBean.getUrlsFile()).readString().split("\r\n");
        for (int i = 0; i < comicUrls.length; i++) {
            comicUrls[i] = Constants.getURL(comicUrls[i]);
            ComicBean comicBean = checkComicData(comicUrls[i]);
            GlobalThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    downloadComic(comicBean);
                }
            });
            downloadBookThreadNum.addAndGet(1);
            while (downloadBookThreadNum.get()>=4) {
                continue;
            }
        }
        if (downloadListener != null) {
            downloadListener.onDownloadComplete();
        }
    }

    private void downloadComic(ComicBean comicBean) {
        if (comicBean.getComicPage() == comicBean.getCurDownloadPage()) {
            log.info("下载信息：" + comicBean.getComicName() + " 下载完成");
            return;
        }
        downloadImages(comicBean);
    }

    private volatile AtomicInteger downloadImageThreadNum = new AtomicInteger(0);

    private void downloadImages(ComicBean comicBean) {
        if (!FileUtil.exist(configBean.getDownloadPath() + comicBean.getComicName())) {
            FileUtil.mkdir(configBean.getDownloadPath() + comicBean.getComicName());
        }
        for (int i = 0; i < comicBean.getComicPageUrl().size(); i++) {
            String downloadUrl = comicBean.getComicPageUrl().get(i);
            String fileName = comicBean.getComicPageUrl().get(i).substring(comicBean.getComicPageUrl().get(i).lastIndexOf("/") + 1);
            if (FileUtil.exist(configBean.getDownloadPath() + comicBean.getComicName() + File.separator + fileName) && FileUtil.size(new File(configBean.getDownloadPath() + comicBean.getComicName() + File.separator + fileName)) != 0) {
                continue;
            }
            GlobalThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    if (download(comicBean, downloadUrl, configBean.getDownloadPath() + comicBean.getComicName(), fileName)) {
                        downloadImageThreadNum.addAndGet(-1);
                    }
                }
            });
            downloadImageThreadNum.addAndGet(1);
            while (downloadImageThreadNum.get() >= 7) {
                continue;
            }
        }
        downloadBookThreadNum.addAndGet(-1);
    }

    /**
     * 下载文件到本地
     *
     * @param comicBean
     * @param urlString 被下载的文件地址
     * @param filename  本地文件名
     * @throws Exception 各种异常
     */
    public boolean download(ComicBean comicBean, String urlString, String filePath, String filename) {
        try {
            String finalUrlString = urlString;
            HttpUtils.downloadFile(urlString, new File(filePath + File.separator + filename), new StreamProgress() {
                @Override
                public void start() {
                    log.info("下载信息:链接：" + finalUrlString + " 漫画名称：" + comicBean.getComicName() + " 当前名字：" + filename);
                }

                @Override
                public void progress(long l) {
//                    log.info("下载中：" + comicBean.getComicName() + String.format(" 总下载进度:%.2f%%",(comicBean.getCurDownloadPage() * 1.0 / comicBean.getComicPage() * 100))+"文件下载("+filename+"):"+FileUtil.readableFileSize(l));
                }

                @Override
                public void finish() {
                    LockUtil.getNoLock().lock();
                    comicBean.setCurDownloadPage(comicBean.getCurDownloadPage() + 1);
                    LockUtil.getNoLock().unlock();
                    log.info("下载完成：" + comicBean.getComicName() + String.format(" 总下载进度:%.2f%%", (comicBean.getCurDownloadPage() * 1.0 / comicBean.getComicPage() * 100)));
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (urlString.contains("png")) {
                urlString = urlString.replace("png", "jpg");
            } else if (urlString.contains("jpg")) {
                urlString = urlString.replace("jpg", "png");
            }
            ThreadUtil.sleep(2000);
            return download(comicBean, urlString, filePath, filename);
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    private ComicBean checkComicData(String comicUrl) {
        try {
            log.info("链接：" + comicUrl + " 解析开始");
            TimeInterval timeInterval = DateUtil.timer();
            ComicBean comicBean = new ComicBean();
            Connection connection = Jsoup.connect(comicUrl + (comicUrl.contains(Constants.NHentai) ? "/1/" : "list/1/"));
            if (configBean.isProxyEnable() && comicUrl.contains(Constants.NHentai)) {
                connection.proxy(HttpUtils.getProxy());
            }
            Document document = connection.get();
            comicBean.setComicUrl(comicUrl);
            comicBean.setComicName(getTitle(comicUrl));
            comicBean.setComicPage(getComicPage(comicUrl, document));
            comicBean.setComicPageUrl(getComicPageUrl(comicBean, comicUrl, document));
            comicBean.setCurDownloadPage(getCurrentDownloadPages(comicBean.getComicName()));
            comicBean.setDownload(comicBean.getComicPage() == comicBean.getCurDownloadPage());
            comicBean.setDownloadStatus(comicBean.getComicPage() == comicBean.getCurDownloadPage() ? "下载完成" : "未开始");
            log.info("漫画信息:链接：" + comicUrl + " 漫画名称：" + comicBean.getComicName() + " 漫画页码：" + comicBean.getComicPage() + " 解析时间：" + timeInterval.interval() + "ms");
            return comicBean;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            return checkComicData(comicUrl);
        }
    }

    private int getCurrentDownloadPages(String comicName) {
        int curDownloadPages = 0;
        String filePath = configBean.getDownloadPath() + comicName;
        if (FileUtil.exist(filePath)) {
            curDownloadPages = FileUtil.ls(filePath).length;
        }
        return curDownloadPages;
    }

    private List<String> getComicPageUrl(ComicBean comicBean, String comicUrl, Document document) throws IOException {
        List<String> comicPageUrl = new ArrayList<>();
        String imageSrc;
        String ext;
        if (comicUrl.startsWith(Constants.CNMiaoHentai)) {
            imageSrc = document.select("img.current-img").attr("src");
            ext = imageSrc.substring(imageSrc.lastIndexOf(".") + 1);
            for (int i = 1; i <= comicBean.getComicPage(); i++) {
                comicPageUrl.add(imageSrc.replace("1." + ext, i + "." + ext));
            }
        } else {
            Connection connection = Jsoup.connect(comicUrl + "1/");
            if (configBean.isProxyEnable() && comicUrl.contains(Constants.NHentai)) {
                connection.proxy(HttpUtils.getProxy());
            }
            Document srcDocument = connection.get();
            imageSrc = srcDocument.select("#content>section.fit-both>a>img").attr("src");
            ext = imageSrc.substring(imageSrc.lastIndexOf(".") + 1);
            for (int i = 1; i <= comicBean.getComicPage(); i++) {
                File downloadBookPath = new File(configBean.getDownloadPath() + comicBean.getComicName());
                if (!downloadBookPath.exists()) {
                    downloadBookPath.mkdirs();
                }
                comicPageUrl.add(imageSrc.replace("1." + ext, i + "." + ext));
            }
        }
        return comicPageUrl;
    }

    private int getComicPage(String comicUrl, Document document) {
        int comicPage = 0;
        if (comicUrl.startsWith(Constants.CNMiaoHentai)) {
            comicPage = Integer.parseInt(document.select("span.num-pages").get(0).html());
        } else {
            if (document.select("span.num-pages").size() > 0) {
                comicPage = Integer.parseInt(document.select("span.num-pages").get(0).html());
            }

        }
        return comicPage;
    }

    private String getTitle(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        if (configBean.isProxyEnable() && url.contains(Constants.NHentai)) {
            connection.proxy(HttpUtils.getProxy());
        }
        Document document = connection.get();
        String author;
        String title;
        if (url.startsWith(Constants.CNMiaoHentai)) {
            title = document.select("div#info > h1").html().replace(" - Page 2", "").replace("[中国翻訳]", "").replace("[DL版]", "");
            title = ReUtil.delAll("\\(C\\d{2,4}\\)", title);
            title = ReUtil.delAll("\\(COMIC.*\\d{2,4}\\)", title);
            title = ReUtil.delAll("\\(COMIC.*号\\)", title).trim();
            log.info("漫画名称：" + title);
        } else {
            author = document.select("div#info > h2 > span.before").html().trim();
            author = ReUtil.delAll("\\([^)]*\\)",author).trim();
            title = author+document.select("div#info > h2 > span.pretty").html().trim();
            log.info("漫画名称：" + title);
        }
        title = title.replaceAll("[/\\\\:*?|]", "");
        title = title.replaceAll("[\"<>]", "");
        return title;
    }
}
