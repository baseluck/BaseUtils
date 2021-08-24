package com.yhzc.schooldormitorymobile.utils;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.yhzc.schooldormitorymobile.ui.login.LoginModel;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/19 14:22
 * @描述:
 */
public class Cache {

    public static final String NFC_KEY = "FC7ECA1570E3644A7E95524C5EEA7174";

    public static void saveAccount(String account) {
        SPStaticUtils.put("account", account);
    }

    public static String getAccount() {
        return SPStaticUtils.getString("account");
    }

    public static void savePassword(String password) {
        SPStaticUtils.put("password", password);
    }

    public static String getPassword() {
        return SPStaticUtils.getString("password");
    }

    /**
     * 保存登录信息
     *
     * @param loginResult
     */
    public static void saveLogin(String loginResult) {
        SPStaticUtils.put("login_result", loginResult);
    }

    public static LoginModel getLogin() {
        return GsonUtils.fromJson(SPStaticUtils.getString("login_result"), LoginModel.class);
    }

    public static void saveToken(String token) {
        SPStaticUtils.put("token", token);
    }

    public static void saveBaiduToken(String baiduToken) {
        SPStaticUtils.put("baiduToken", baiduToken);
    }

    public static String getBaiduToken() {
        return SPStaticUtils.getString("baiduToken");
    }

    public static void saveFaceToken(String faceToken) {
        SPStaticUtils.put("faceToken", faceToken);
    }

    public static String getFaceToken() {
        return SPStaticUtils.getString("faceToken");
    }

    public static void saveWqly(String wqly) {
        SPStaticUtils.put("wqly", wqly);
    }

    public static String getWqly() {
        return SPStaticUtils.getString("wqly");
    }

    public static String getToken() {
        return SPStaticUtils.getString("token");
    }

    public static void saveHasNewNotice(boolean has) {
        SPStaticUtils.put("hasNewNotice", has);
    }

    public static boolean getHasNewNotice() {
        return SPStaticUtils.getBoolean("hasNewNotice");
    }

    public static void saveFaceActive(int activeCode) {
        SPStaticUtils.put("activeCode", activeCode);
    }

    public static int getFaceActive() {
        return SPStaticUtils.getInt("activeCode", 0);
    }

    public static void saveBaseHttp(String url) {
        SPStaticUtils.put("base_url", url);
    }

    public static String getBaseHttp() {
        return SPStaticUtils.getString("base_url");
    }

    public static String getHttp() {

        if (SPStaticUtils.getString("base_url").contains("http://")) {
            return SPStaticUtils.getString("base_url");
        } else {
            return "http://" + SPStaticUtils.getString("base_url");
        }
    }
}
