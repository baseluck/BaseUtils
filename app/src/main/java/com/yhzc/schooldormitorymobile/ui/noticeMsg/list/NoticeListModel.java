package com.yhzc.schooldormitorymobile.ui.noticeMsg.list;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/22 16:17
 * @描述:
 */
public class NoticeListModel {

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
        private String createTime;
        private String vcTitle;
        private String vcId;
        private String status;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getVcTitle() {
            return vcTitle;
        }

        public void setVcTitle(String vcTitle) {
            this.vcTitle = vcTitle;
        }

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
