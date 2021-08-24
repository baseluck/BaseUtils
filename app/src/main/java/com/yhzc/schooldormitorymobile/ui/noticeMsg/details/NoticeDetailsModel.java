package com.yhzc.schooldormitorymobile.ui.noticeMsg.details;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/22 16:29
 * @描述:
 */
public class NoticeDetailsModel {

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
        private String vcId;
        private String vcTitle;
        private String vcCreateUserId;
        private int intType;
        private int isReply;
        private String dtSendTime;
        private List<String> vcImg;
        private String vcVideo;
        private String vcContent;
        private List<ReplyListBean> replyList;

        public String getVcCreateUserId() {
            return vcCreateUserId;
        }

        public void setVcCreateUserId(String vcCreateUserId) {
            this.vcCreateUserId = vcCreateUserId;
        }

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public String getVcTitle() {
            return vcTitle;
        }

        public void setVcTitle(String vcTitle) {
            this.vcTitle = vcTitle;
        }

        public int getIntType() {
            return intType;
        }

        public void setIntType(int intType) {
            this.intType = intType;
        }

        public int getIsReply() {
            return isReply;
        }

        public void setIsReply(int isReply) {
            this.isReply = isReply;
        }

        public String getDtSendTime() {
            return dtSendTime;
        }

        public void setDtSendTime(String dtSendTime) {
            this.dtSendTime = dtSendTime;
        }

        public List<String> getVcImg() {
            return vcImg;
        }

        public void setVcImg(List<String> vcImg) {
            this.vcImg = vcImg;
        }

        public String getVcVideo() {
            return vcVideo;
        }

        public void setVcVideo(String vcVideo) {
            this.vcVideo = vcVideo;
        }

        public String getVcContent() {
            return vcContent;
        }

        public void setVcContent(String vcContent) {
            this.vcContent = vcContent;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class ReplyListBean {
            private String vcId;
            private String createTime;
            private String userName;
            private String content;
            private List<String> jsonPic;

            public List<String> getJsonPic() {
                return jsonPic;
            }

            public void setJsonPic(List<String> jsonPic) {
                this.jsonPic = jsonPic;
            }

            public String getVcId() {
                return vcId;
            }

            public void setVcId(String vcId) {
                this.vcId = vcId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
