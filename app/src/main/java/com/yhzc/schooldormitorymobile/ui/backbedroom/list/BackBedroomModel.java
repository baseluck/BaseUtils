package com.yhzc.schooldormitorymobile.ui.backbedroom.list;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/29 17:22
 * @描述:
 */
public class BackBedroomModel {

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
            private String vcCode;
            private String vcTeacherId;
            private String vcStudentId;
            private String vcClassId;
            private String vcDromNum;
            private String textContent;
            private String dtCreateTime;
            private String dtBackTime;
            private String dtLeaveTime;
            private String vcDromManagerId;
            private String textMark;

            public String getVcId() {
                return vcId;
            }

            public void setVcId(String vcId) {
                this.vcId = vcId;
            }

            public String getVcCode() {
                return vcCode;
            }

            public void setVcCode(String vcCode) {
                this.vcCode = vcCode;
            }

            public String getVcTeacherId() {
                return vcTeacherId;
            }

            public void setVcTeacherId(String vcTeacherId) {
                this.vcTeacherId = vcTeacherId;
            }

            public String getVcStudentId() {
                return vcStudentId;
            }

            public void setVcStudentId(String vcStudentId) {
                this.vcStudentId = vcStudentId;
            }

            public String getVcClassId() {
                return vcClassId;
            }

            public void setVcClassId(String vcClassId) {
                this.vcClassId = vcClassId;
            }

            public String getVcDromNum() {
                return vcDromNum;
            }

            public void setVcDromNum(String vcDromNum) {
                this.vcDromNum = vcDromNum;
            }

            public String getTextContent() {
                return textContent;
            }

            public void setTextContent(String textContent) {
                this.textContent = textContent;
            }

            public String getDtCreateTime() {
                return dtCreateTime;
            }

            public void setDtCreateTime(String dtCreateTime) {
                this.dtCreateTime = dtCreateTime;
            }

            public String getDtBackTime() {
                return dtBackTime;
            }

            public void setDtBackTime(String dtBackTime) {
                this.dtBackTime = dtBackTime;
            }

            public String getDtLeaveTime() {
                return dtLeaveTime;
            }

            public void setDtLeaveTime(String dtLeaveTime) {
                this.dtLeaveTime = dtLeaveTime;
            }

            public String getVcDromManagerId() {
                return vcDromManagerId;
            }

            public void setVcDromManagerId(String vcDromManagerId) {
                this.vcDromManagerId = vcDromManagerId;
            }

            public String getTextMark() {
                return textMark;
            }

            public void setTextMark(String textMark) {
                this.textMark = textMark;
            }
        }
    }
}
