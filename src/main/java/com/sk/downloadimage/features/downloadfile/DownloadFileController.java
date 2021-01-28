package com.sk.downloadimage.features.downloadfile;

import cn.hutool.core.util.StrUtil;
import com.sk.downloadimage.base.BaseController;
import com.sk.downloadimage.base.Constants;
import com.sk.downloadimage.utils.DownloadUtils;

public class DownloadFileController extends BaseController {
    @Override
    protected void initView() {
        System.out.println("请输入文件路径:");
        System.out.println("输入(0)返回:");
        System.out.print("请输入:");
    }

    @Override
    protected void HandleEvent(String input) {
        if ("0".equals(input)){
            back();
        }else {
            Constants.URLSFile = input;
            new DownloadUtils().startDownload(new DownloadUtils.OnDownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    System.out.println("输入(0)返回:");
                }
            });
        }
    }
}
