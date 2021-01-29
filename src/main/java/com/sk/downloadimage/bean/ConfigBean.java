package com.sk.downloadimage.bean;

public class ConfigBean {
    private String DownloadPath = "";
    private String urlsFile = "";

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
}
