package com.yhzc.schooldormitorymobile.ui.backbedroom;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 16:06
 * @描述:
 */
public class ContentItemModel {
    private String title;
    private String hint;
    private String content;
    private boolean canEnter;

    public ContentItemModel() {
    }

    public ContentItemModel(String title, String hint, String content, boolean canEnter) {
        this.title = title;
        this.hint = hint;
        this.content = content;
        this.canEnter = canEnter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCanEnter() {
        return canEnter;
    }

    public void setCanEnter(boolean canEnter) {
        this.canEnter = canEnter;
    }
}
