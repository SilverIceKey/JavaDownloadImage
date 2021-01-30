package com.sk.downloadimage.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWrapper;
import cn.hutool.core.io.file.FileWriter;
import com.alibaba.fastjson.JSONObject;
import com.sk.downloadimage.base.Constants;
import com.sk.downloadimage.bean.ConfigBean;

import java.io.File;
import java.nio.charset.Charset;

public class ConfigUtils {
    public static void init() {
        if (!FileUtil.exist(new File(Constants.ConfigPath))) {
            FileUtil.touch(new File(Constants.ConfigPath));
            FileWriter.create(new File(Constants.ConfigPath), Charset.forName("GBK"))
                    .write(JSONObject.toJSONString(new ConfigBean()));
        }
    }

    public static ConfigBean getConfig() {
        return JSONObject
                .parseObject(FileUtil.readString(new File(Constants.ConfigPath),
                        Charset.defaultCharset()),
                        ConfigBean.class);
    }

    public static void save(ConfigBean configBean) {
        FileWriter.create(new File(Constants.ConfigPath), Charset.forName("GBK"))
                .write(JSONObject.toJSONString(configBean));
    }
}
