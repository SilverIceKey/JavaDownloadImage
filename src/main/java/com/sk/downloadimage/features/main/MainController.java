package com.sk.downloadimage.features.main;

import com.sk.downloadimage.base.BaseController;
import com.sk.downloadimage.features.downloadfile.DownloadFileController;
import com.sk.downloadimage.features.downloadpath.DownloadPathController;
import com.sk.downloadimage.utils.LogUtils;

public class MainController extends BaseController {

    @Override
    protected void initView() {
        LogUtils.info("输出日志");
//        User.getUser();
        System.out.println("1、设置下载路径");
        System.out.println("2、输入需要下载的文件路径");
        System.out.print("请选择:");
    }

    @Override
    public void onResume() {
        initView();
        super.onResume();
    }

    @Override
    protected void HandleEvent(String input) {
        switch (input){
            case "1":
                startController(new DownloadPathController());
                break;
            case "2":
                startController(new DownloadFileController());
                break;
            default:
                System.out.println("请选择正确的选项");
                initView();
                break;
        }
    }
}
