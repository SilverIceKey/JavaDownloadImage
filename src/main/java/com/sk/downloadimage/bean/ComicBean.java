package com.sk.downloadimage.bean;

import java.util.List;

public class ComicBean {
    private String comicUrl;
    private String comicName;
    private int comicPage;
    private List<String> comicPageUrl;
    private int curDownloadPage;
    private String downloadStatus;
    private boolean isDownload;

    public String getComicUrl() {
        return comicUrl;
    }

    public void setComicUrl(String comicUrl) {
        this.comicUrl = comicUrl;
    }

    public String getComicName() {
        return comicName.replace(" ","").trim();
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public int getComicPage() {
        return comicPage;
    }

    public void setComicPage(int comicPage) {
        this.comicPage = comicPage;
    }

    public List<String> getComicPageUrl() {
        return comicPageUrl;
    }

    public void setComicPageUrl(List<String> comicPageUrl) {
        this.comicPageUrl = comicPageUrl;
    }

    public int getCurDownloadPage() {
        return curDownloadPage;
    }

    public void setCurDownloadPage(int curDownloadPage) {
        this.curDownloadPage = curDownloadPage;
    }

    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public boolean isDownload() {
        return curDownloadPage==comicPage;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }
}
