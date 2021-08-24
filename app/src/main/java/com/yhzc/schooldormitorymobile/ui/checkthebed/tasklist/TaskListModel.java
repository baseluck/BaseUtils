package com.yhzc.schooldormitorymobile.ui.checkthebed.tasklist;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/9 15:26
 * @描述:
 */
public class TaskListModel {

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
            private String vcManageName;
            private String vcUnitName;
            private String vcBuildingName;
            private String vcStoreyName;
            private String vcAreaName;
            private String dtCheckDate;
            private String dtCheckStartTime;
            private String dtCheckEndTime;
            private String vcUserName;
            private int intRule;
            private int intCheckStruts;
            private int intStruts;
            private int intRecord;

            public String getVcStoreyName() {
                return vcStoreyName;
            }

            public void setVcStoreyName(String vcStoreyName) {
                this.vcStoreyName = vcStoreyName;
            }

            public String getVcUserName() {
                return vcUserName;
            }

            public void setVcUserName(String vcUserName) {
                this.vcUserName = vcUserName;
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

            public String getVcManageName() {
                return vcManageName;
            }

            public void setVcManageName(String vcManageName) {
                this.vcManageName = vcManageName;
            }

            public String getVcUnitName() {
                return vcUnitName;
            }

            public void setVcUnitName(String vcUnitName) {
                this.vcUnitName = vcUnitName;
            }

            public String getVcBuildingName() {
                return vcBuildingName;
            }

            public void setVcBuildingName(String vcBuildingName) {
                this.vcBuildingName = vcBuildingName;
            }

            public String getVcAreaName() {
                return vcAreaName;
            }

            public void setVcAreaName(String vcAreaName) {
                this.vcAreaName = vcAreaName;
            }

            public String getDtCheckDate() {
                return dtCheckDate;
            }

            public void setDtCheckDate(String dtCheckDate) {
                this.dtCheckDate = dtCheckDate;
            }

            public String getDtCheckStartTime() {
                return dtCheckStartTime;
            }

            public void setDtCheckStartTime(String dtCheckStartTime) {
                this.dtCheckStartTime = dtCheckStartTime;
            }

            public String getDtCheckEndTime() {
                return dtCheckEndTime;
            }

            public void setDtCheckEndTime(String dtCheckEndTime) {
                this.dtCheckEndTime = dtCheckEndTime;
            }

            public int getIntRule() {
                return intRule;
            }

            public void setIntRule(int intRule) {
                this.intRule = intRule;
            }

            public int getIntCheckStruts() {
                return intCheckStruts;
            }

            public void setIntCheckStruts(int intCheckStruts) {
                this.intCheckStruts = intCheckStruts;
            }

            public int getIntStruts() {
                return intStruts;
            }

            public void setIntStruts(int intStruts) {
                this.intStruts = intStruts;
            }

            public int getIntRecord() {
                return intRecord;
            }

            public void setIntRecord(int intRecord) {
                this.intRecord = intRecord;
            }
        }
    }
}
