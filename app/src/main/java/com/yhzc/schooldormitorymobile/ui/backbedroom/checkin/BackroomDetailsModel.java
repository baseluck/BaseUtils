package com.yhzc.schooldormitorymobile.ui.backbedroom.checkin;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/31 17:54
 * @描述:
 */
public class BackroomDetailsModel {

    private double code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String vcId;
        private String vcCode;
        private String vcTeacherId;
        private String vcStudentId;
        private String vcClassId;
        private String vcDromNum;
        private String textContent;
        private double blSp;
        private double intStatus;
        private String vcAttachmentPath;
        private double dtCreateTime;

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public String getVcCode() {
            return vcCode;
        }

        public void setVcCode(String vcCode) {
            this.vcCode = vcCode;
        }

        public String getVcTeacherId() {
            return vcTeacherId;
        }

        public void setVcTeacherId(String vcTeacherId) {
            this.vcTeacherId = vcTeacherId;
        }

        public String getVcStudentId() {
            return vcStudentId;
        }

        public void setVcStudentId(String vcStudentId) {
            this.vcStudentId = vcStudentId;
        }

        public String getVcClassId() {
            return vcClassId;
        }

        public void setVcClassId(String vcClassId) {
            this.vcClassId = vcClassId;
        }

        public String getVcDromNum() {
            return vcDromNum;
        }

        public void setVcDromNum(String vcDromNum) {
            this.vcDromNum = vcDromNum;
        }

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public double getBlSp() {
            return blSp;
        }

        public void setBlSp(double blSp) {
            this.blSp = blSp;
        }

        public double getIntStatus() {
            return intStatus;
        }

        public void setIntStatus(double intStatus) {
            this.intStatus = intStatus;
        }

        public String getVcAttachmentPath() {
            return vcAttachmentPath;
        }

        public void setVcAttachmentPath(String vcAttachmentPath) {
            this.vcAttachmentPath = vcAttachmentPath;
        }

        public double getDtCreateTime() {
            return dtCreateTime;
        }

        public void setDtCreateTime(double dtCreateTime) {
            this.dtCreateTime = dtCreateTime;
        }
    }
}
