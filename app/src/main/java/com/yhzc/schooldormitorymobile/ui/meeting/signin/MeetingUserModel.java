package com.yhzc.schooldormitorymobile.ui.meeting.signin;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/13 16:14
 * @描述:
 */
public class MeetingUserModel {

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
        private String vcId;
        private String vcMeetId;
        private String vcJoinUserId;
        private String vcJoinUserName;
        private int intStatus;
        private int blSingle;
        private int blSignOut;



        public int getBlSingle() {
            return blSingle;
        }

        public void setBlSingle(int blSingle) {
            this.blSingle = blSingle;
        }

        public int getBlSignOut() {
            return blSignOut;
        }

        public void setBlSignOut(int blSignOut) {
            this.blSignOut = blSignOut;
        }

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public String getVcMeetId() {
            return vcMeetId;
        }

        public void setVcMeetId(String vcMeetId) {
            this.vcMeetId = vcMeetId;
        }

        public String getVcJoinUserId() {
            return vcJoinUserId;
        }

        public void setVcJoinUserId(String vcJoinUserId) {
            this.vcJoinUserId = vcJoinUserId;
        }

        public String getVcJoinUserName() {
            return vcJoinUserName;
        }

        public void setVcJoinUserName(String vcJoinUserName) {
            this.vcJoinUserName = vcJoinUserName;
        }

        public int getIntStatus() {
            return intStatus;
        }

        public void setIntStatus(int intStatus) {
            this.intStatus = intStatus;
        }
    }
}
