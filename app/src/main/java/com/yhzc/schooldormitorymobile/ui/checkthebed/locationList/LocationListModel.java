package com.yhzc.schooldormitorymobile.ui.checkthebed.locationList;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/17 14:49
 * @描述:
 */
public class LocationListModel {

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
        private String ldid;
        private String dymc;
        private String xqid;
        private String xqmc;
        private String ldmc;
        private String dyid;

        public String getLdid() {
            return ldid;
        }

        public void setLdid(String ldid) {
            this.ldid = ldid;
        }

        public String getDymc() {
            return dymc;
        }

        public void setDymc(String dymc) {
            this.dymc = dymc;
        }

        public String getXqid() {
            return xqid;
        }

        public void setXqid(String xqid) {
            this.xqid = xqid;
        }

        public String getXqmc() {
            return xqmc;
        }

        public void setXqmc(String xqmc) {
            this.xqmc = xqmc;
        }

        public String getLdmc() {
            return ldmc;
        }

        public void setLdmc(String ldmc) {
            this.ldmc = ldmc;
        }

        public String getDyid() {
            return dyid;
        }

        public void setDyid(String dyid) {
            this.dyid = dyid;
        }
    }
}
