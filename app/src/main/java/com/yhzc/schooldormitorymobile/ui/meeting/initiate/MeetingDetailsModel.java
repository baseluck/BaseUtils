package com.yhzc.schooldormitorymobile.ui.meeting.initiate;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/14 18:04
 * @描述:
 */
public class MeetingDetailsModel {

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
        private long dtSignOutStartTime;
        private List<JoinUserListBean> joinUserList;
        private int tiDeleteMark;
        private int blSend;
        private long dtSignInEndTime;
        private String vcPath;
        private int intType;
        private long dtCreateTime;
        private String vcTitle;
        private String vcSignUser;
        private long dtSignInStartTime;
        private String vcCode;
        private String vcSignUserName;
        private int tiEnabledMark;
        private long dtEndTime;
        private long dtSignOutEndTime;
        private long dtStartTime;
        private String vcJoinUser;
        private String vcCreateUserId;
        private int intDuration;
        private String vcId;

        public long getDtSignOutStartTime() {
            return dtSignOutStartTime;
        }

        public void setDtSignOutStartTime(long dtSignOutStartTime) {
            this.dtSignOutStartTime = dtSignOutStartTime;
        }

        public List<JoinUserListBean> getJoinUserList() {
            return joinUserList;
        }

        public void setJoinUserList(List<JoinUserListBean> joinUserList) {
            this.joinUserList = joinUserList;
        }

        public int getTiDeleteMark() {
            return tiDeleteMark;
        }

        public void setTiDeleteMark(int tiDeleteMark) {
            this.tiDeleteMark = tiDeleteMark;
        }

        public int getBlSend() {
            return blSend;
        }

        public void setBlSend(int blSend) {
            this.blSend = blSend;
        }

        public long getDtSignInEndTime() {
            return dtSignInEndTime;
        }

        public void setDtSignInEndTime(long dtSignInEndTime) {
            this.dtSignInEndTime = dtSignInEndTime;
        }

        public String getVcPath() {
            return vcPath;
        }

        public void setVcPath(String vcPath) {
            this.vcPath = vcPath;
        }

        public int getIntType() {
            return intType;
        }

        public void setIntType(int intType) {
            this.intType = intType;
        }

        public long getDtCreateTime() {
            return dtCreateTime;
        }

        public void setDtCreateTime(long dtCreateTime) {
            this.dtCreateTime = dtCreateTime;
        }

        public String getVcTitle() {
            return vcTitle;
        }

        public void setVcTitle(String vcTitle) {
            this.vcTitle = vcTitle;
        }

        public String getVcSignUser() {
            return vcSignUser;
        }

        public void setVcSignUser(String vcSignUser) {
            this.vcSignUser = vcSignUser;
        }

        public long getDtSignInStartTime() {
            return dtSignInStartTime;
        }

        public void setDtSignInStartTime(long dtSignInStartTime) {
            this.dtSignInStartTime = dtSignInStartTime;
        }

        public String getVcCode() {
            return vcCode;
        }

        public void setVcCode(String vcCode) {
            this.vcCode = vcCode;
        }

        public String getVcSignUserName() {
            return vcSignUserName;
        }

        public void setVcSignUserName(String vcSignUserName) {
            this.vcSignUserName = vcSignUserName;
        }

        public int getTiEnabledMark() {
            return tiEnabledMark;
        }

        public void setTiEnabledMark(int tiEnabledMark) {
            this.tiEnabledMark = tiEnabledMark;
        }

        public long getDtEndTime() {
            return dtEndTime;
        }

        public void setDtEndTime(long dtEndTime) {
            this.dtEndTime = dtEndTime;
        }

        public long getDtSignOutEndTime() {
            return dtSignOutEndTime;
        }

        public void setDtSignOutEndTime(long dtSignOutEndTime) {
            this.dtSignOutEndTime = dtSignOutEndTime;
        }

        public long getDtStartTime() {
            return dtStartTime;
        }

        public void setDtStartTime(long dtStartTime) {
            this.dtStartTime = dtStartTime;
        }

        public String getVcJoinUser() {
            return vcJoinUser;
        }

        public void setVcJoinUser(String vcJoinUser) {
            this.vcJoinUser = vcJoinUser;
        }

        public String getVcCreateUserId() {
            return vcCreateUserId;
        }

        public void setVcCreateUserId(String vcCreateUserId) {
            this.vcCreateUserId = vcCreateUserId;
        }

        public int getIntDuration() {
            return intDuration;
        }

        public void setIntDuration(int intDuration) {
            this.intDuration = intDuration;
        }

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public static class JoinUserListBean {
            private String joinUserName;
            private String joinUserId;

            public String getJoinUserName() {
                return joinUserName;
            }

            public void setJoinUserName(String joinUserName) {
                this.joinUserName = joinUserName;
            }

            public String getJoinUserId() {
                return joinUserId;
            }

            public void setJoinUserId(String joinUserId) {
                this.joinUserId = joinUserId;
            }
        }
    }
}
