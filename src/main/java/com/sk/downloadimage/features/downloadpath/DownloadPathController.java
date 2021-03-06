package com.sk.downloadimage.features.downloadpath;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.sk.downloadimage.base.BaseController;
import com.sk.downloadimage.bean.ConfigBean;
import com.sk.downloadimage.utils.ConfigUtils;

public class DownloadPathController extends BaseController {
    private ConfigBean configBean;

    @Override
    protected void initView() {
        configBean = ConfigUtils.getConfig();
        System.out.println("输入下载路径 (当前路径:" + configBean.getDownloadPath() + "):");
        System.out.println("输入(0)返回:");
        System.out.print("请输入:");
    }

    @Override
    protected void HandleEvent(String input) {
        if ("0".equals(input)) {
            back();
        } else {
            if (!FileUtil.exist(input)) {
                System.out.println("请输入正确的文件路径");
                return;
            }
            if (!input.endsWith("\\")){
                input = input+"\\";
            }
            configBean.setDownloadPath(input);
            ConfigUtils.save(configBean);
            System.out.println("设置完成");
            System.out.println("输入(0)返回:");
            System.out.print("请输入:");
        }
    }
}
