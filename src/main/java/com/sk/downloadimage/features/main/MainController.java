package com.sk.downloadimage.features.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSONObject;
import com.sk.downloadimage.base.BaseController;
import com.sk.downloadimage.base.Constants;
import com.sk.downloadimage.bean.ConfigBean;
import com.sk.downloadimage.features.downloadfile.DownloadFileController;
import com.sk.downloadimage.features.downloadpath.DownloadPathController;
import com.sk.downloadimage.utils.ConfigUtils;
import com.sk.downloadimage.utils.DownloadUtils;
import com.sk.downloadimage.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class MainController extends BaseController {

    @Override
    protected void initView() {
        LogUtils.info("输出日志");
        ConfigBean configBean = ConfigUtils.getConfig();
        System.out.println("1、设置下载路径 (当前路径:"+configBean.getDownloadPath()+")");
        System.out.println("2、输入需要下载的文件路径 (当前路径:"+configBean.getUrlsFile()+")");
        System.out.println("3、输入开始下载");
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
