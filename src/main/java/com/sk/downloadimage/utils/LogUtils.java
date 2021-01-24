package com.sk.downloadimage.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
    private static Logger mLogger = Logger.getLogger("DownloadImage");
    public static void init(){
        mLogger.isLoggable(Level.INFO);
    }
    public static void info(String message) {
        mLogger.log(Level.INFO,message);
    }
}
