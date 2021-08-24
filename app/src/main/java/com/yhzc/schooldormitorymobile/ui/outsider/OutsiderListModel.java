package com.yhzc.schooldormitorymobile.ui.outsider;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/7 16:17
 * @描述:
 */
public class OutsiderListModel {


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
            private String dtLeaveTime;
            private String textMark;
            private String dtTime;
            private String vcIdNumber;
            private String vcCarNo;
            private String vcPhone;
            private List<String> vcImg;
            private String vcContactUser;
            private String vcFkUser;
            private String vcId;
            private String vcReason;

            public String getDtLeaveTime() {
                return dtLeaveTime;
            }

            public void setDtLeaveTime(String dtLeaveTime) {
                this.dtLeaveTime = dtLeaveTime;
            }

            public String getTextMark() {
                return textMark;
            }

            public void setTextMark(String textMark) {
                this.textMark = textMark;
            }

            public String getDtTime() {
                return dtTime;
            }

            public void setDtTime(String dtTime) {
                this.dtTime = dtTime;
            }

            public String getVcIdNumber() {
                return vcIdNumber;
            }

            public void setVcIdNumber(String vcIdNumber) {
                this.vcIdNumber = vcIdNumber;
            }

            public String getVcCarNo() {
                return vcCarNo;
            }

            public void setVcCarNo(String vcCarNo) {
                this.vcCarNo = vcCarNo;
            }

            public String getVcPhone() {
                return vcPhone;
            }

            public void setVcPhone(String vcPhone) {
                this.vcPhone = vcPhone;
            }

            public List<String> getVcImg() {
                return vcImg;
            }

            public void setVcImg(List<String> vcImg) {
                this.vcImg = vcImg;
            }

            public String getVcContactUser() {
                return vcContactUser;
            }

            public void setVcContactUser(String vcContactUser) {
                this.vcContactUser = vcContactUser;
            }

            public String getVcFkUser() {
                return vcFkUser;
            }

            public void setVcFkUser(String vcFkUser) {
                this.vcFkUser = vcFkUser;
            }

            public String getVcId() {
                return vcId;
            }

            public void setVcId(String vcId) {
                this.vcId = vcId;
            }

            public String getVcReason() {
                return vcReason;
            }

            public void setVcReason(String vcReason) {
                this.vcReason = vcReason;
            }
        }
    }
}
