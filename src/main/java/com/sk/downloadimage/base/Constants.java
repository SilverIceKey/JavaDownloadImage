package com.sk.downloadimage.base;

public class Constants {
    public static String CNMiaoHentai = "https://zhb.doghentai.com/";
    public static String[] OTHERMiaoHentai = new String[]{"https://zh.nyahentai.fun/","https://en.bughentai.com/"};
    public static String NHentai = "nhentai";
    public static String MYSQLURL = "";
    public static String ConfigPath = ".\\config.txt";

    public static String getURL(String url) {
        String resultUrl = "";
        if (url.contains(NHentai)) {
            resultUrl = url;
        } else {
            for (int i = 0; i < OTHERMiaoHentai.length; i++) {
                if (url.contains(OTHERMiaoHentai[i])) {
                    resultUrl = url.replace(OTHERMiaoHentai[i], CNMiaoHentai);
                    break;
                }
            }
        }
        return resultUrl;
    }
}
