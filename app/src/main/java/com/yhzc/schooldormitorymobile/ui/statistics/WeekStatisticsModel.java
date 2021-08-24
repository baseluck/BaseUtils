package com.yhzc.schooldormitorymobile.ui.statistics;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/3 15:16
 * @描述:
 */
public class WeekStatisticsModel {

    private double code;
    private String msg;
    private List<DataBean> data;

    public double getCode() {
        return code;
    }

    public void setCode(double code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String day;
        private double num;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public double getNum() {
            return num;
        }

        public void setNum(double num) {
            this.num = num;
        }

    }
}
