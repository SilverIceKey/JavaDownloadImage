package com.sk.downloadimage.features.proxy;

import com.sk.downloadimage.base.BaseController;
import com.sk.downloadimage.bean.ConfigBean;
import com.sk.downloadimage.utils.ConfigUtils;

public class ProxyEnableController extends BaseController {
    private ConfigBean configBean;

    @Override
    protected void initView() {
        configBean = ConfigUtils.getConfig();
        System.out.println("是否开启代理(1、开启,2、关闭) (当前代理是否开启:" + (configBean.isProxyEnable() ? "开启" : "关闭") + "):");
        System.out.println("输入(0)返回:");
        System.out.print("请输入:");
    }

    @Override
    protected void HandleEvent(String input) {
        if ("0".equals(input)) {
            back();
        } else if ("1".equals(input)){
            try {
                configBean.setProxyEnable(true);
                ConfigUtils.save(configBean);
                System.out.println("设置完成");
                System.out.println("输入(0)返回:");
                System.out.print("请输入:");
            } catch (Exception e) {
                System.out.println("请输入纯数字");
            }
        }else if ("2".equals(input)){
            try {
                configBean.setProxyEnable(false);
                ConfigUtils.save(configBean);
                System.out.println("设置完成");
                System.out.println("输入(0)返回:");
                System.out.print("请输入:");
            } catch (Exception e) {
                System.out.println("请输入纯数字");
            }
        }else {
            System.out.println("请输入对应的数字");
        }
    }
}
