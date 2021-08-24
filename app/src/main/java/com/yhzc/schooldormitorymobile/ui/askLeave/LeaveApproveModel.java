package com.yhzc.schooldormitorymobile.ui.askLeave;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 11:24
 * @描述:
 */
public class LeaveApproveModel {

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
        private String approveType;
        private List<ApproveUserBean> approveUser;

        public String getApproveType() {
            return approveType;
        }

        public void setApproveType(String approveType) {
            this.approveType = approveType;
        }

        public List<ApproveUserBean> getApproveUser() {
            return approveUser;
        }

        public void setApproveUser(List<ApproveUserBean> approveUser) {
            this.approveUser = approveUser;
        }

        public static class ApproveUserBean {
            private String headPic;
            private String name;

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
