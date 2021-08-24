package com.yhzc.schooldormitorymobile.ui.leaveList;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 15:47
 * @描述:
 */
public class LeaveListModel {

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
        private String sendTime;
        private String leaveType;
        private String leaveTitle;
        private String startTime;
        private String vcProcId;
        private String leaveReason;
        private String endTime;
        private String vcId;
        private int leaveStatus;

        public String getLeaveReason() {
            return leaveReason;
        }

        public void setLeaveReason(String leaveReason) {
            this.leaveReason = leaveReason;
        }

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public String getVcProcId() {
            return vcProcId;
        }

        public void setVcProcId(String vcProcId) {
            this.vcProcId = vcProcId;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getLeaveType() {
            return leaveType;
        }

        public void setLeaveType(String leaveType) {
            this.leaveType = leaveType;
        }

        public String getLeaveTitle() {
            return leaveTitle;
        }

        public void setLeaveTitle(String leaveTitle) {
            this.leaveTitle = leaveTitle;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getLeaveStatus() {
            return leaveStatus;
        }

        public void setLeaveStatus(int leaveStatus) {
            this.leaveStatus = leaveStatus;
        }
    }
}
