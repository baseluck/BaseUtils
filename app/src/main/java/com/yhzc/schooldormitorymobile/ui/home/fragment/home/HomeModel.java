package com.yhzc.schooldormitorymobile.ui.home.fragment.home;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/29 15:37
 * @描述:
 */
public class HomeModel {


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
        private List<BannerBean> banner;
        private List<NoticeBean> notice;
        private List<NewsBean> news;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<NoticeBean> getNotice() {
            return notice;
        }

        public void setNotice(List<NoticeBean> notice) {
            this.notice = notice;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class BannerBean {
            private String picUrl;
            private String bannerId;
            private String bannerName;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getBannerId() {
                return bannerId;
            }

            public void setBannerId(String bannerId) {
                this.bannerId = bannerId;
            }

            public String getBannerName() {
                return bannerName;
            }

            public void setBannerName(String bannerName) {
                this.bannerName = bannerName;
            }
        }

        public static class NoticeBean {
            private String noticeId;
            private String noticeName;

            public String getNoticeId() {
                return noticeId;
            }

            public void setNoticeId(String noticeId) {
                this.noticeId = noticeId;
            }

            public String getNoticeName() {
                return noticeName;
            }

            public void setNoticeName(String noticeName) {
                this.noticeName = noticeName;
            }
        }

        public static class NewsBean {
            private String newsId;
            private String newsTitle;
            private String newsPicUrl;
            private String newsContent;
            private String newsTime;

            public String getNewsId() {
                return newsId;
            }

            public void setNewsId(String newsId) {
                this.newsId = newsId;
            }

            public String getNewsTitle() {
                return newsTitle;
            }

            public void setNewsTitle(String newsTitle) {
                this.newsTitle = newsTitle;
            }

            public String getNewsPicUrl() {
                return newsPicUrl;
            }

            public void setNewsPicUrl(String newsPicUrl) {
                this.newsPicUrl = newsPicUrl;
            }

            public String getNewsContent() {
                return newsContent;
            }

            public void setNewsContent(String newsContent) {
                this.newsContent = newsContent;
            }

            public String getNewsTime() {
                return newsTime;
            }

            public void setNewsTime(String newsTime) {
                this.newsTime = newsTime;
            }
        }
    }
}
