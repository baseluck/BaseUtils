package com.yhzc.schooldormitorymobile.ui.suppliesapply.returnslist;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/28 12:05
 * @描述:
 */
public class ReturnsListModel {

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

        public static class ListBean{
            private String vcId;
            private String vcUserId;
            private String vcType;
            private String vcTitle;
            private String textMark;
            private String dtRecordTime;

            public String getVcId() {
                return vcId;
            }

            public void setVcId(String vcId) {
                this.vcId = vcId;
            }

            public String getVcUserId() {
                return vcUserId;
            }

            public void setVcUserId(String vcUserId) {
                this.vcUserId = vcUserId;
            }

            public String getVcType() {
                return vcType;
            }

            public void setVcType(String vcType) {
                this.vcType = vcType;
            }

            public String getVcTitle() {
                return vcTitle;
            }

            public void setVcTitle(String vcTitle) {
                this.vcTitle = vcTitle;
            }

            public String getTextMark() {
                return textMark;
            }

            public void setTextMark(String textMark) {
                this.textMark = textMark;
            }

            public String getDtRecordTime() {
                return dtRecordTime;
            }

            public void setDtRecordTime(String dtRecordTime) {
                this.dtRecordTime = dtRecordTime;
            }
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
    }
}
