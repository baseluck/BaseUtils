package com.yhzc.schooldormitorymobile.ui.noticeMsg.newList;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/31 18:49
 * @描述:
 */
public class NoticeNewModel {

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
            private int currentPage;
            private int pageSize;
            private int total;

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }

        public static class ListBean {
            private String vcId;
            private String vcTitle;
            private String vcSendUser;
            private int intType;
            private int intStatus;
            private int isReply;
            private String dtSendTime;
            private int tiEnabledMark;

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

            public String getVcSendUser() {
                return vcSendUser;
            }

            public void setVcSendUser(String vcSendUser) {
                this.vcSendUser = vcSendUser;
            }

            public int getIntType() {
                return intType;
            }

            public void setIntType(int intType) {
                this.intType = intType;
            }

            public int getIntStatus() {
                return intStatus;
            }

            public void setIntStatus(int intStatus) {
                this.intStatus = intStatus;
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

            public int getTiEnabledMark() {
                return tiEnabledMark;
            }

            public void setTiEnabledMark(int tiEnabledMark) {
                this.tiEnabledMark = tiEnabledMark;
            }
        }
    }
}
