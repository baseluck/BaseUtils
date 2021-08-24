package com.yhzc.schooldormitorymobile.ui.backbedroom.details;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/29 18:09
 * @描述:
 */
public class BackBedroomDetailsModel {

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
        private String title;
        private List<ContentBean> content;
        private List<PicsBean> pics;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public List<PicsBean> getPics() {
            return pics;
        }

        public void setPics(List<PicsBean> pics) {
            this.pics = pics;
        }

        public static class ContentBean {
            private String type;
            private String detail;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class PicsBean {
            private String picUrl;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }
        }
    }
}
