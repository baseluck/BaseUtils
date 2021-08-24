package com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/28 12:30
 * @描述:
 */
public class UserModel {
    private String userName;
    private String userId;

    public UserModel() {
    }

    public UserModel(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
