package com.yhzc.schooldormitorymobile.ui.notetake.take;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/22 15:31
 * @描述:
 */
public class TimeModel {
    private String time;
    private boolean selected;

    public TimeModel() {
    }

    public TimeModel(String time, boolean selected) {
        this.time = time;
        this.selected = selected;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
