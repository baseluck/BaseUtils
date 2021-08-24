package com.yhzc.schooldormitorymobile.ui.backbedroom.send;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/18 11:28
 * @描述:
 */
public class StudentModel {

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
        private String vcAreaId;
        private String vcClassId;
        private String vcGradeId;
        private String vcNumber;
        private String vcRealName;
        private String vcSex;
        private long dtEnrol;
        private String vcGeneralCardNum;
        private String vcIdPhoto;
        private String vcDromNum;
        private String vcParentPhone;
        private String vcIdcard;
        private String vcSchoolNum;
        private long dtBirthday;
        private String vcNativePlace;
        private String vcNativePlaceAll;
        private String vcAddress;
        private String className;
        private String vcNation;
        private String vcType;
        private long dtGraduate;
        private int intYear;
        private String vcTags;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
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

        public String getVcIdPhoto() {
            return vcIdPhoto;
        }

        public void setVcIdPhoto(String vcIdPhoto) {
            this.vcIdPhoto = vcIdPhoto;
        }

        public String getVcDromNum() {
            return vcDromNum;
        }

        public void setVcDromNum(String vcDromNum) {
            this.vcDromNum = vcDromNum;
        }

        public String getVcParentPhone() {
            return vcParentPhone;
        }

        public void setVcParentPhone(String vcParentPhone) {
            this.vcParentPhone = vcParentPhone;
        }

        public String getVcIdcard() {
            return vcIdcard;
        }

        public void setVcIdcard(String vcIdcard) {
            this.vcIdcard = vcIdcard;
        }

        public String getVcSchoolNum() {
            return vcSchoolNum;
        }

        public void setVcSchoolNum(String vcSchoolNum) {
            this.vcSchoolNum = vcSchoolNum;
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

        public String getVcNativePlaceAll() {
            return vcNativePlaceAll;
        }

        public void setVcNativePlaceAll(String vcNativePlaceAll) {
            this.vcNativePlaceAll = vcNativePlaceAll;
        }

        public String getVcAddress() {
            return vcAddress;
        }

        public void setVcAddress(String vcAddress) {
            this.vcAddress = vcAddress;
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

        public long getDtGraduate() {
            return dtGraduate;
        }

        public void setDtGraduate(long dtGraduate) {
            this.dtGraduate = dtGraduate;
        }

        public int getIntYear() {
            return intYear;
        }

        public void setIntYear(int intYear) {
            this.intYear = intYear;
        }

        public String getVcTags() {
            return vcTags;
        }

        public void setVcTags(String vcTags) {
            this.vcTags = vcTags;
        }
    }
}
