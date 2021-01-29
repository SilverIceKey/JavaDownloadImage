package com.sk.downloadimage.base;

import cn.hutool.core.util.StrUtil;

import java.util.Scanner;

public abstract class BaseController {
    private Scanner scan;
    private boolean isPause = false;

    protected abstract void initView();

    protected abstract void HandleEvent(String input);

    public void onCreate() {
        initView();
        listenerInput();
    }

    public void onResume() {
        isPause = false;
        listenerInput();
    }

    public void onPause() {
        isPause = true;
    }

    public void onDestory() {

    }

    private void listenerInput() {
        scan = new Scanner(System.in);
        while (scan.hasNextLine() && !isPause) {
            String in = scan.nextLine();
            if (StrUtil.isEmptyOrUndefined(in)) {
                System.out.println("请重新输入");
                initView();
            } else {
                if (in.startsWith("\"")) {
                    in = in.substring(1, in.length() - 1);
                }
                HandleEvent(in);
            }
        }
    }

    protected <T extends BaseController> void startController(T controller) {
        ControllerManager.getInstance().addController(controller);
    }

    protected void back() {
        ControllerManager.getInstance().removeController();
    }
}
