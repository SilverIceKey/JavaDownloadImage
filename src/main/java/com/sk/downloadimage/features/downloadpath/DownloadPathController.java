package com.sk.downloadimage.features.downloadpath;

import cn.hutool.core.util.StrUtil;
import com.sk.downloadimage.base.BaseController;

public class DownloadPathController extends BaseController {
    @Override
    protected void initView() {
        System.out.println("请输入下载路径:");
        System.out.println("输入(0)返回:");
        System.out.print("请输入:");
    }

    @Override
    protected void HandleEvent(String input) {
        if ("0".equals(input)){
            back();
        }else {

        }
    }
}