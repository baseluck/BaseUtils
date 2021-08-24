package com.yhzc.schooldormitorymobile.ui.InSchool;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/7 15:43
 * @描述:
 */
public class InSchoolListModel {

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
            private String vcStudentId;
            private String vcGrade;
            private String vcPhone;
            private String vcNumber;
            private String vcIcCardNo;
            private String vcIdcard;
            private String vcIdPhoto;
            private String textMark;
            private String dtBackTime;
            private String vcSex;
            private String vcRealName;
            private String vcClass;
            private String vcId;

            public String getVcIdPhoto() {
                return vcIdPhoto;
            }

            public void setVcIdPhoto(String vcIdPhoto) {
                this.vcIdPhoto = vcIdPhoto;
            }

            public String getVcStudentId() {
                return vcStudentId;
            }

            public void setVcStudentId(String vcStudentId) {
                this.vcStudentId = vcStudentId;
            }

            public String getVcGrade() {
                return vcGrade;
            }

            public void setVcGrade(String vcGrade) {
                this.vcGrade = vcGrade;
            }

            public String getVcPhone() {
                return vcPhone;
            }

            public void setVcPhone(String vcPhone) {
                this.vcPhone = vcPhone;
            }

            public String getVcNumber() {
                return vcNumber;
            }

            public void setVcNumber(String vcNumber) {
                this.vcNumber = vcNumber;
            }

            public String getVcIcCardNo() {
                return vcIcCardNo;
            }

            public void setVcIcCardNo(String vcIcCardNo) {
                this.vcIcCardNo = vcIcCardNo;
            }

            public String getVcIdcard() {
                return vcIdcard;
            }

            public void setVcIdcard(String vcIdcard) {
                this.vcIdcard = vcIdcard;
            }

            public String getTextMark() {
                return textMark;
            }

            public void setTextMark(String textMark) {
                this.textMark = textMark;
            }

            public String getDtBackTime() {
                return dtBackTime;
            }

            public void setDtBackTime(String dtBackTime) {
                this.dtBackTime = dtBackTime;
            }

            public String getVcSex() {
                return vcSex;
            }

            public void setVcSex(String vcSex) {
                this.vcSex = vcSex;
            }

            public String getVcRealName() {
                return vcRealName;
            }

            public void setVcRealName(String vcRealName) {
                this.vcRealName = vcRealName;
            }

            public String getVcClass() {
                return vcClass;
            }

            public void setVcClass(String vcClass) {
                this.vcClass = vcClass;
            }

            public String getVcId() {
                return vcId;
            }

            public void setVcId(String vcId) {
                this.vcId = vcId;
            }
        }
    }
}
