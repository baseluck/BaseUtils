package com.yhzc.schooldormitorymobile.ui.home.fragment.home;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/6 16:02
 * @描述:
 */
public class UnReadMsgModel {

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

    public static class DataBean {
        private boolean isUnRead;
        private int num;

        public boolean isIsUnRead() {
            return isUnRead;
        }

        public void setIsUnRead(boolean isUnRead) {
            this.isUnRead = isUnRead;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
