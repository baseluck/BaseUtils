package com.yhzc.schooldormitorymobile.ui.checkthebed.choosebedroom;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/9 17:23
 * @描述:
 */
public class BedroomListModel {

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
        private String vcStoreyId;
        private String vcStoreyName;
        private List<ChildrenBean> children;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getVcStoreyId() {
            return vcStoreyId;
        }

        public void setVcStoreyId(String vcStoreyId) {
            this.vcStoreyId = vcStoreyId;
        }

        public String getVcStoreyName() {
            return vcStoreyName;
        }

        public void setVcStoreyName(String vcStoreyName) {
            this.vcStoreyName = vcStoreyName;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            private String vcRoomName;
            private int intErrorNum;
            private String vcStoreyId;
            private String vcTitle;
            private int intStruts;
            private int intCheckStruts;
            private String vcId;

            public String getVcTitle() {
                return vcTitle;
            }

            public void setVcTitle(String vcTitle) {
                this.vcTitle = vcTitle;
            }

            public int getIntCheckStruts() {
                return intCheckStruts;
            }

            public void setIntCheckStruts(int intCheckStruts) {
                this.intCheckStruts = intCheckStruts;
            }

            public String getVcRoomName() {
                return vcRoomName;
            }

            public void setVcRoomName(String vcRoomName) {
                this.vcRoomName = vcRoomName;
            }

            public int getIntErrorNum() {
                return intErrorNum;
            }

            public void setIntErrorNum(int intErrorNum) {
                this.intErrorNum = intErrorNum;
            }

            public String getVcStoreyId() {
                return vcStoreyId;
            }

            public void setVcStoreyId(String vcStoreyId) {
                this.vcStoreyId = vcStoreyId;
            }

            public int getIntStruts() {
                return intStruts;
            }

            public void setIntStruts(int intStruts) {
                this.intStruts = intStruts;
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
