package com.yhzc.schooldormitorymobile.ui.signRecord;

import java.io.Serializable;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/3 14:50
 * @描述:
 */
public class CheckStatisticsModel implements Serializable {

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private int month_qdk;
        private int year_ydk;
        private int month_sjdk;
        private int year_sjdk;
        private int week_qdk;
        private int month_ydk;
        private int year_qdk;
        private int week_sjdk;
        private int week_ydk;

        public int getMonth_qdk() {
            return month_qdk;
        }

        public void setMonth_qdk(int month_qdk) {
            this.month_qdk = month_qdk;
        }

        public int getYear_ydk() {
            return year_ydk;
        }

        public void setYear_ydk(int year_ydk) {
            this.year_ydk = year_ydk;
        }

        public int getMonth_sjdk() {
            return month_sjdk;
        }

        public void setMonth_sjdk(int month_sjdk) {
            this.month_sjdk = month_sjdk;
        }

        public int getYear_sjdk() {
            return year_sjdk;
        }

        public void setYear_sjdk(int year_sjdk) {
            this.year_sjdk = year_sjdk;
        }

        public int getWeek_qdk() {
            return week_qdk;
        }

        public void setWeek_qdk(int week_qdk) {
            this.week_qdk = week_qdk;
        }

        public int getMonth_ydk() {
            return month_ydk;
        }

        public void setMonth_ydk(int month_ydk) {
            this.month_ydk = month_ydk;
        }

        public int getYear_qdk() {
            return year_qdk;
        }

        public void setYear_qdk(int year_qdk) {
            this.year_qdk = year_qdk;
        }

        public int getWeek_sjdk() {
            return week_sjdk;
        }

        public void setWeek_sjdk(int week_sjdk) {
            this.week_sjdk = week_sjdk;
        }

        public int getWeek_ydk() {
            return week_ydk;
        }

        public void setWeek_ydk(int week_ydk) {
            this.week_ydk = week_ydk;
        }
    }
}
