package com.yhzc.schooldormitorymobile.ui.home.fragment.home;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/29 9:37
 * @描述:
 */
public class HomeNewsModel {
    private String title;
    private String content;
    private String time;

    public HomeNewsModel() {
    }

    public HomeNewsModel(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
