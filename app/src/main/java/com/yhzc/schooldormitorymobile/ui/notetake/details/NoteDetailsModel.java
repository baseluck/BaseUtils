package com.yhzc.schooldormitorymobile.ui.notetake.details;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/23 11:19
 * @描述:
 */
public class NoteDetailsModel {

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
        private String vcId;
        private long dtCreateTime;
        private long dtRecordDay;
        private int intNum;
        private List<String> jsonStr;
        private String textMark;
        private String vcMemoCateId;
        private String vcUserId;

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public long getDtCreateTime() {
            return dtCreateTime;
        }

        public void setDtCreateTime(long dtCreateTime) {
            this.dtCreateTime = dtCreateTime;
        }

        public long getDtRecordDay() {
            return dtRecordDay;
        }

        public void setDtRecordDay(long dtRecordDay) {
            this.dtRecordDay = dtRecordDay;
        }

        public int getIntNum() {
            return intNum;
        }

        public void setIntNum(int intNum) {
            this.intNum = intNum;
        }

        public List<String> getJsonStr() {
            return jsonStr;
        }

        public void setJsonStr(List<String> jsonStr) {
            this.jsonStr = jsonStr;
        }

        public String getTextMark() {
            return textMark;
        }

        public void setTextMark(String textMark) {
            this.textMark = textMark;
        }

        public String getVcMemoCateId() {
            return vcMemoCateId;
        }

        public void setVcMemoCateId(String vcMemoCateId) {
            this.vcMemoCateId = vcMemoCateId;
        }

        public String getVcUserId() {
            return vcUserId;
        }

        public void setVcUserId(String vcUserId) {
            this.vcUserId = vcUserId;
        }
    }
}
