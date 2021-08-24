package com.yhzc.schooldormitorymobile.ui.home;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/23 17:16
 * @描述:
 */
public class HomeItemApp {

    private int mResId;
    private String mAppName;
    private boolean showNew;

    public HomeItemApp() {
    }

    public HomeItemApp(int resId, String appName) {
        mResId = resId;
        mAppName = appName;
    }

    public HomeItemApp(int resId, String appName, boolean showNew) {
        mResId = resId;
        mAppName = appName;
        this.showNew = showNew;
    }

    public int getResId() {
        return mResId;
    }

    public void setResId(int resId) {
        mResId = resId;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }

    public boolean isShowNew() {
        return showNew;
    }

    public void setShowNew(boolean showNew) {
        this.showNew = showNew;
    }
}
