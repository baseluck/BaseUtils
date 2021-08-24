package com.yhzc.schooldormitorymobile.ui.mobileOffice;

import com.yhzc.schooldormitorymobile.ui.home.HomeItemApp;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 14:44
 * @描述:
 */
public class MobileOfficeModel {

    private String title;
    private List<HomeItemApp> mHomeItemApps;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HomeItemApp> getHomeItemApps() {
        return mHomeItemApps;
    }

    public void setHomeItemApps(List<HomeItemApp> homeItemApps) {
        mHomeItemApps = homeItemApps;
    }
}
