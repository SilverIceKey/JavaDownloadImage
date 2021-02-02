package com.sk.downloadimage.bean;

public class ConfigBean {
    private String DownloadPath = "";
    private String urlsFile = "";
    private int port = 0;
    private boolean proxyEnable = true;
    public ConfigBean() {

    }

    public ConfigBean(String downloadPath, String urlsFile) {
        DownloadPath = downloadPath;
        this.urlsFile = urlsFile;
    }

    public String getDownloadPath() {
        return DownloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        DownloadPath = downloadPath;
    }

    public String getUrlsFile() {
        return urlsFile;
    }

    public void setUrlsFile(String URLSFile) {
        this.urlsFile = URLSFile;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isProxyEnable() {
        return proxyEnable;
    }

    public void setProxyEnable(boolean proxyEnable) {
        this.proxyEnable = proxyEnable;
    }
}
