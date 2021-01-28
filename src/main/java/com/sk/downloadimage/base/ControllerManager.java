package com.sk.downloadimage.base;

import java.util.Stack;

public class ControllerManager<T extends BaseController> {
    private Stack<T> mControllerStack;

    public ControllerManager() {
        mControllerStack = new Stack<>();
    }

    private static class ControllerManagerHolder {
        public static ControllerManager mInstance = new ControllerManager();
    }

    public static ControllerManager getInstance() {
        return ControllerManagerHolder.mInstance;
    }

    public void addController(T controller) {
        if (!mControllerStack.empty()) {
            mControllerStack.peek().onPause();
        }
        mControllerStack.push(controller);
        controller.onCreate();
    }

    public void removeController() {
        mControllerStack.peek().onDestory();
        if (!mControllerStack.empty()) {
            mControllerStack.pop();
            mControllerStack.peek().onResume();
        }
    }

    public void removeAllController() {
        for (int i = mControllerStack.size() - 1; i > 0; i--) {
            mControllerStack.peek().onDestory();
            if (!mControllerStack.empty()) {
                mControllerStack.pop();
                mControllerStack.peek().onResume();
            }
        }
    }
}
