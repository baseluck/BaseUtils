package com.yhzc.schooldormitorymobile.ui.home.fragment.home;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/19 17:09
 * @描述:
 */
public class BannerModel {

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
            private String vcTypeId;
            private String vcTitle;
            private String vcUrl;
            private String dtStartTime;
            private String dtEndTime;
            private int intOrder;
            private int tiEnabledMark;
            private String vcPicPath;
            private String vcType;

            public String getVcType() {
                return vcType;
            }

            public void setVcType(String vcType) {
                this.vcType = vcType;
            }

            public String getVcPicPath() {
                return vcPicPath;
            }

            public void setVcPicPath(String vcPicPath) {
                this.vcPicPath = vcPicPath;
            }

            public String getVcId() {
                return vcId;
            }

            public void setVcId(String vcId) {
                this.vcId = vcId;
            }

            public String getVcTypeId() {
                return vcTypeId;
            }

            public void setVcTypeId(String vcTypeId) {
                this.vcTypeId = vcTypeId;
            }

            public String getVcTitle() {
                return vcTitle;
            }

            public void setVcTitle(String vcTitle) {
                this.vcTitle = vcTitle;
            }

            public String getVcUrl() {
                return vcUrl;
            }

            public void setVcUrl(String vcUrl) {
                this.vcUrl = vcUrl;
            }

            public String getDtStartTime() {
                return dtStartTime;
            }

            public void setDtStartTime(String dtStartTime) {
                this.dtStartTime = dtStartTime;
            }

            public String getDtEndTime() {
                return dtEndTime;
            }

            public void setDtEndTime(String dtEndTime) {
                this.dtEndTime = dtEndTime;
            }

            public int getIntOrder() {
                return intOrder;
            }

            public void setIntOrder(int intOrder) {
                this.intOrder = intOrder;
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
