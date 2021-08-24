package com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/12 15:21
 * @描述:
 */
public class BedroomDetailsModel {

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

        private List<String> jsonPic;
        private List<ListBean> list;

        public List<String> getJsonPic() {
            return jsonPic;
        }

        public void setJsonPic(List<String> jsonPic) {
            this.jsonPic = jsonPic;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }


        public static class ListBean {
            private String vcId;
            private String vcBedName;
            private String vcManageName;
            private String vcStudentName;
            private int intCheckin;
            private int intError;
            private boolean selected;
            private int blInSchool;

            public int getBlInSchool() {
                return blInSchool;
            }

            public void setBlInSchool(int blInSchool) {
                this.blInSchool = blInSchool;
            }

            public int getIntError() {
                return intError;
            }

            public void setIntError(int intError) {
                this.intError = intError;
            }

            public String getVcStudentName() {
                return vcStudentName;
            }

            public void setVcStudentName(String vcStudentName) {
                this.vcStudentName = vcStudentName;
            }

            public int getIntCheckin() {
                return intCheckin;
            }

            public void setIntCheckin(int intCheckin) {
                this.intCheckin = intCheckin;
            }

            public String getVcId() {
                return vcId;
            }

            public void setVcId(String vcId) {
                this.vcId = vcId;
            }

            public String getVcBedName() {
                return vcBedName;
            }

            public void setVcBedName(String vcBedName) {
                this.vcBedName = vcBedName;
            }

            public String getVcManageName() {
                return vcManageName;
            }

            public void setVcManageName(String vcManageName) {
                this.vcManageName = vcManageName;
            }

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }
        }
    }
}
