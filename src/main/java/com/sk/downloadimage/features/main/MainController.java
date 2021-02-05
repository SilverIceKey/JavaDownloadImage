package com.sk.downloadimage.features.main;

import cn.hutool.core.io.FileUtil;
import com.sk.downloadimage.base.BaseController;
import com.sk.downloadimage.bean.ConfigBean;
import com.sk.downloadimage.features.downloadfile.DownloadFileController;
import com.sk.downloadimage.features.downloadpath.DownloadPathController;
import com.sk.downloadimage.features.proxy.ProxyController;
import com.sk.downloadimage.features.proxy.ProxyEnableController;
import com.sk.downloadimage.utils.ConfigUtils;
import com.sk.downloadimage.utils.DownloadUtils;
import com.sk.downloadimage.utils.LogUtils;

public class MainController extends BaseController {
    private String BOOT_INF = ".\\BOOT-INF";
    private String META_INF = ".\\META-INF";
    private String org = ".\\org";
    @Override
    protected void initView() {
        FileUtil.del(BOOT_INF);
        FileUtil.del(META_INF);
        FileUtil.del(org);
        ConfigBean configBean = ConfigUtils.getConfig();
        System.out.println("1、设置下载路径 (当前路径:" + configBean.getDownloadPath() + ")");
        System.out.println("2、输入需要下载的文件路径 (当前路径:" + configBean.getUrlsFile() + ")");
        System.out.println("3、输入设置代理端口(当前代理端口:" + configBean.getPort() + ")");
        System.out.println("4、是否开启代理(当前代理是否开启:" + (configBean.isProxyEnable() ? "开启" : "关闭") + ")");
        System.out.println("5、输入开始下载");
        System.out.print("请选择:");
    }

    @Override
    public void onResume() {
        initView();
        super.onResume();
    }

    @Override
    protected void HandleEvent(String input) {
        switch (input) {
            case "1":
                startController(new DownloadPathController());
                break;
            case "2":
                startController(new DownloadFileController());
                break;
            case "3":
                startController(new ProxyController());
                break;
            case "4":
                startController(new ProxyEnableController());
                break;
            case "5":
                new DownloadUtils().startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        initView();
                    }
                });
                break;
            default:
                System.out.println("请选择正确的选项");
                initView();
                break;
        }
    }
}
