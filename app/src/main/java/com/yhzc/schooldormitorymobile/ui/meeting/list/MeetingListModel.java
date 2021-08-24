package com.yhzc.schooldormitorymobile.ui.meeting.list;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/12 17:57
 * @描述:
 */
public class MeetingListModel {

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
        private List<ListBean> list;
        private PaginationBean pagination;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public static class PaginationBean {
            private double currentPage;
            private double pageSize;
            private double total;

            public double getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(double currentPage) {
                this.currentPage = currentPage;
            }

            public double getPageSize() {
                return pageSize;
            }

            public void setPageSize(double pageSize) {
                this.pageSize = pageSize;
            }

            public double getTotal() {
                return total;
            }

            public void setTotal(double total) {
                this.total = total;
            }
        }

        public static class ListBean {
            private String vcId;
            private boolean auth;
            private String vcCode;
            private String vcTitle;
            private String vcJoinUser;
            private String dtStartTime;
            private String dtEndTime;
            private String vcSignUser;
            private String dtSignInStartTime;
            private String dtSignInEndTime;
            private String dtSignOutStartTime;
            private String dtSignOutEndTime;
            private String dtCreateTime;
            private String isSignIn;
            private String isSignOut;
            private int blSend;
            private String vcCreateUserId;
            private String textMark;
            private double intDuration;
            private String checkType;

            public String getIsSignIn() {
                return isSignIn;
            }

            public void setIsSignIn(String isSignIn) {
                this.isSignIn = isSignIn;
            }

            public String getIsSignOut() {
                return isSignOut;
            }

            public void setIsSignOut(String isSignOut) {
                this.isSignOut = isSignOut;
            }

            public int getBlSend() {
                return blSend;
            }

            public void setBlSend(int blSend) {
                this.blSend = blSend;
            }

            public String getCheckType() {
                return checkType;
            }

            public void setCheckType(String checkType) {
                this.checkType = checkType;
            }

            public String getVcId() {
                return vcId;
            }

            public void setVcId(String vcId) {
                this.vcId = vcId;
            }

            public boolean isAuth() {
                return auth;
            }

            public void setAuth(boolean auth) {
                this.auth = auth;
            }

            public String getVcCode() {
                return vcCode;
            }

            public void setVcCode(String vcCode) {
                this.vcCode = vcCode;
            }

            public String getVcTitle() {
                return vcTitle;
            }

            public void setVcTitle(String vcTitle) {
                this.vcTitle = vcTitle;
            }

            public String getVcJoinUser() {
                return vcJoinUser;
            }

            public void setVcJoinUser(String vcJoinUser) {
                this.vcJoinUser = vcJoinUser;
            }

            public String getDtStartTime() {
                return dtStartTime;
            }

            public void setDtStartTime(String dtStartTime) {
                this.dtStartTime = dtStartTime;
            }

            public String getDtEndTime() {
                return dtEndTime;
            }

            public void setDtEndTime(String dtEndTime) {
                this.dtEndTime = dtEndTime;
            }

            public String getVcSignUser() {
                return vcSignUser;
            }

            public void setVcSignUser(String vcSignUser) {
                this.vcSignUser = vcSignUser;
            }

            public String getDtSignInStartTime() {
                return dtSignInStartTime;
            }

            public void setDtSignInStartTime(String dtSignInStartTime) {
                this.dtSignInStartTime = dtSignInStartTime;
            }

            public String getDtSignInEndTime() {
                return dtSignInEndTime;
            }

            public void setDtSignInEndTime(String dtSignInEndTime) {
                this.dtSignInEndTime = dtSignInEndTime;
            }

            public String getDtSignOutStartTime() {
                return dtSignOutStartTime;
            }

            public void setDtSignOutStartTime(String dtSignOutStartTime) {
                this.dtSignOutStartTime = dtSignOutStartTime;
            }

            public String getDtSignOutEndTime() {
                return dtSignOutEndTime;
            }

            public void setDtSignOutEndTime(String dtSignOutEndTime) {
                this.dtSignOutEndTime = dtSignOutEndTime;
            }

            public String getDtCreateTime() {
                return dtCreateTime;
            }

            public void setDtCreateTime(String dtCreateTime) {
                this.dtCreateTime = dtCreateTime;
            }

            public String getVcCreateUserId() {
                return vcCreateUserId;
            }

            public void setVcCreateUserId(String vcCreateUserId) {
                this.vcCreateUserId = vcCreateUserId;
            }

            public String getTextMark() {
                return textMark;
            }

            public void setTextMark(String textMark) {
                this.textMark = textMark;
            }

            public double getIntDuration() {
                return intDuration;
            }

            public void setIntDuration(double intDuration) {
                this.intDuration = intDuration;
            }
        }
    }
}
