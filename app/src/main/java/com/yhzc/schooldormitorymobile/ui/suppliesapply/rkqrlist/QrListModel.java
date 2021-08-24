package com.yhzc.schooldormitorymobile.ui.suppliesapply.rkqrlist;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/7 13:46
 * @描述:
 */
public class QrListModel {

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
            private String vcUserId;
            private String vcType;
            private String vcProcId;
            private String vcTitle;
            private String textMark;
            private String dtRecordTime;

            public String getVcProcId() {
                return vcProcId;
            }

            public void setVcProcId(String vcProcId) {
                this.vcProcId = vcProcId;
            }

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
    }
}
