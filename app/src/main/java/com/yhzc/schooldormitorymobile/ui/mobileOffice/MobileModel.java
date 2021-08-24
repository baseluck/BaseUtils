package com.yhzc.schooldormitorymobile.ui.mobileOffice;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/2 14:45
 * @描述:
 */
public class MobileModel {

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
        private String id;
        private String fullName;
        private String enCode;
        private String appIcon;
        private List<ChildrenBean> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEnCode() {
            return enCode;
        }

        public void setEnCode(String enCode) {
            this.enCode = enCode;
        }


        public String getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(String appIcon) {
            this.appIcon = appIcon;
        }


        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }


        public static class ChildrenBean {
            private String id;
            private String fullName;
            private String enCode;
            private String appIcon;
            private boolean blMessage;
            private int numMessage;

            public boolean isBlMessage() {
                return blMessage;
            }

            public void setBlMessage(boolean blMessage) {
                this.blMessage = blMessage;
            }

            public int getNumMessage() {
                return numMessage;
            }

            public void setNumMessage(int numMessage) {
                this.numMessage = numMessage;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public String getEnCode() {
                return enCode;
            }

            public void setEnCode(String enCode) {
                this.enCode = enCode;
            }

            public String getAppIcon() {
                return appIcon;
            }

            public void setAppIcon(String appIcon) {
                this.appIcon = appIcon;
            }
        }
    }
}
