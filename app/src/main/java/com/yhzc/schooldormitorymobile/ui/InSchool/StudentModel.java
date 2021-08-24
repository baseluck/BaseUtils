package com.yhzc.schooldormitorymobile.ui.InSchool;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/7 15:24
 * @描述:
 */
public class StudentModel {

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
        private String vcAreaId;
        private String vcClassId;
        private String vcGradeId;
        private String vcNumber;
        private String vcRealName;
        private String vcSex;
        private long dtBirthday;
        private String vcNativePlace;
        private String vcNation;
        private String vcType;
        private long dtEnrol;
        private String vcGeneralCardNum;
        private String vcParentPhone;
        private String vcIdPhoto;
        private int intYear;

        public String getVcIdPhoto() {
            return vcIdPhoto;
        }

        public void setVcIdPhoto(String vcIdPhoto) {
            this.vcIdPhoto = vcIdPhoto;
        }

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public String getVcAreaId() {
            return vcAreaId;
        }

        public void setVcAreaId(String vcAreaId) {
            this.vcAreaId = vcAreaId;
        }

        public String getVcClassId() {
            return vcClassId;
        }

        public void setVcClassId(String vcClassId) {
            this.vcClassId = vcClassId;
        }

        public String getVcGradeId() {
            return vcGradeId;
        }

        public void setVcGradeId(String vcGradeId) {
            this.vcGradeId = vcGradeId;
        }

        public String getVcNumber() {
            return vcNumber;
        }

        public void setVcNumber(String vcNumber) {
            this.vcNumber = vcNumber;
        }

        public String getVcRealName() {
            return vcRealName;
        }

        public void setVcRealName(String vcRealName) {
            this.vcRealName = vcRealName;
        }

        public String getVcSex() {
            return vcSex;
        }

        public void setVcSex(String vcSex) {
            this.vcSex = vcSex;
        }

        public long getDtBirthday() {
            return dtBirthday;
        }

        public void setDtBirthday(long dtBirthday) {
            this.dtBirthday = dtBirthday;
        }

        public String getVcNativePlace() {
            return vcNativePlace;
        }

        public void setVcNativePlace(String vcNativePlace) {
            this.vcNativePlace = vcNativePlace;
        }

        public String getVcNation() {
            return vcNation;
        }

        public void setVcNation(String vcNation) {
            this.vcNation = vcNation;
        }

        public String getVcType() {
            return vcType;
        }

        public void setVcType(String vcType) {
            this.vcType = vcType;
        }

        public long getDtEnrol() {
            return dtEnrol;
        }

        public void setDtEnrol(long dtEnrol) {
            this.dtEnrol = dtEnrol;
        }

        public String getVcGeneralCardNum() {
            return vcGeneralCardNum;
        }

        public void setVcGeneralCardNum(String vcGeneralCardNum) {
            this.vcGeneralCardNum = vcGeneralCardNum;
        }

        public String getVcParentPhone() {
            return vcParentPhone;
        }

        public void setVcParentPhone(String vcParentPhone) {
            this.vcParentPhone = vcParentPhone;
        }

        public int getIntYear() {
            return intYear;
        }

        public void setIntYear(int intYear) {
            this.intYear = intYear;
        }
    }
}
