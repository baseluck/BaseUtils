package com.yhzc.schooldormitorymobile.ui.modifystudent.classlist;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/19 15:06
 * @描述:
 */
public class ClassListModel implements Serializable {


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

    public static class DataBean implements Serializable {
        private List<StuListBean> stuList;
        private String vcCode;
        private String vcName;
        private String vcId;

        public List<StuListBean> getStuList() {
            return stuList;
        }

        public void setStuList(List<StuListBean> stuList) {
            this.stuList = stuList;
        }

        public String getVcCode() {
            return vcCode;
        }

        public void setVcCode(String vcCode) {
            this.vcCode = vcCode;
        }

        public String getVcName() {
            return vcName;
        }

        public void setVcName(String vcName) {
            this.vcName = vcName;
        }

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public static class StuListBean implements Serializable {
            private String vcSchoolNum;
            private String vcSex;
            private String vcNumber;
            private String vcName;
            private String vcParentPhone;
            private String vcId;

            public String getVcSchoolNum() {
                return vcSchoolNum;
            }

            public void setVcSchoolNum(String vcSchoolNum) {
                this.vcSchoolNum = vcSchoolNum;
            }

            public String getVcSex() {
                return vcSex;
            }

            public void setVcSex(String vcSex) {
                this.vcSex = vcSex;
            }

            public String getVcNumber() {
                return vcNumber;
            }

            public void setVcNumber(String vcNumber) {
                this.vcNumber = vcNumber;
            }

            public String getVcName() {
                return vcName;
            }

            public void setVcName(String vcName) {
                this.vcName = vcName;
            }

            public String getVcParentPhone() {
                return vcParentPhone;
            }

            public void setVcParentPhone(String vcParentPhone) {
                this.vcParentPhone = vcParentPhone;
            }

            public String getVcId() {
                return vcId;
            }

            public void setVcId(String vcId) {
                this.vcId = vcId;
            }
        }
    }
}
