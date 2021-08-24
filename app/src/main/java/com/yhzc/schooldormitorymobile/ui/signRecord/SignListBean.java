package com.yhzc.schooldormitorymobile.ui.signRecord;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/21 19:29
 * @描述:
 */
public class SignListBean {

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String signTime;
        private String signStatus;
        private String signType;

        public String getSignTime() {
            return signTime;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }

        public String getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(String signStatus) {
            this.signStatus = signStatus;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }
    }
}
