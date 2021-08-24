package com.yhzc.schooldormitorymobile.ui.askLeave;

/**
 * @描述:
 * @创建日期: 2021/4/16 14:09
 * @author: ProcyonLotor
 */
public class LeaveItemModel {

    private String title;
    private String content;
    private String hintContent;

    public LeaveItemModel(String title, String content, String hintContent) {
        this.title = title;
        this.content = content;
        this.hintContent = hintContent;
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

    public String getHintContent() {
        return hintContent;
    }

    public void setHintContent(String hintContent) {
        this.hintContent = hintContent;
    }
}
