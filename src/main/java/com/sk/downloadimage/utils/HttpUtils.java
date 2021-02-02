package com.sk.downloadimage.utils;

import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.sk.downloadimage.base.Constants;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

public class HttpUtils {
    public static long downloadFile(String url, File destFile, StreamProgress streamProgress) {
        return requestDownloadFile(url, destFile, -1).writeBody(destFile, streamProgress);
    }

    private static HttpResponse requestDownloadFile(String url, File destFile, int timeout) {
        Assert.notBlank(url, "[url] is blank !", new Object[0]);
        Assert.notNull(destFile, "[destFile] is null !", new Object[0]);
        HttpRequest request = HttpRequest.get(url);
        if (url.contains(Constants.NHentai)&&ConfigUtils.getConfig().isProxyEnable()) {
            LogUtils.info("设置代理");
//            request.setHttpProxy("127.0.0.1", ConfigUtils.getConfig().getPort());
            request.setProxy(getProxy());
        }
        HttpResponse response = request.timeout(timeout).executeAsync();
        if (response.isOk()) {
            return response;
        } else {
            throw new HttpException("Server response error with status code: [{}]", new Object[]{response.getStatus()});
        }
    }

    public static Proxy getProxy(){
        InetSocketAddress add = new InetSocketAddress("127.0.0.1", ConfigUtils.getConfig().getPort());
        return new Proxy(Proxy.Type.HTTP, add);
    }
}
