package com.sk.downloadimage.features.proxy;

import cn.hutool.core.io.FileUtil;
import com.sk.downloadimage.base.BaseController;
import com.sk.downloadimage.bean.ConfigBean;
import com.sk.downloadimage.utils.ConfigUtils;

public class ProxyController extends BaseController {
    private ConfigBean configBean;

    @Override
    protected void initView() {
        configBean = ConfigUtils.getConfig();
        System.out.println("输入代理端口(栗子:10809 纯数字) (当前代理端口:"+configBean.getPort() + "):");
        System.out.println("输入(0)返回:");
        System.out.print("请输入:");
    }

    @Override
    protected void HandleEvent(String input) {
        if ("0".equals(input)) {
            back();
        } else {
            try {
                configBean.setPort(Integer.parseInt(input));
                ConfigUtils.save(configBean);
                System.out.println("设置完成");
                System.out.println("输入(0)返回:");
                System.out.print("请输入:");
            }catch (Exception e){
                System.out.println("请输入纯数字");
            }
        }
    }
}
