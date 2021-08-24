package com.yhzc.schooldormitorymobile.service;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/14 15:02
 * @描述:
 */
public class PushModel {

    private int unreadNoticeCount;
    private String method;
    private String title;
    private String userId;
    private List<String> toUserId;

    public int getUnreadNoticeCount() {
        return unreadNoticeCount;
    }

    public void setUnreadNoticeCount(int unreadNoticeCount) {
        this.unreadNoticeCount = unreadNoticeCount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getToUserId() {
        return toUserId;
    }

    public void setToUserId(List<String> toUserId) {
        this.toUserId = toUserId;
    }
}
