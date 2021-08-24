package com.yhzc.schooldormitorymobile.ui.home.fragment.home;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/19 16:52
 * @描述:
 */
public class ContentList {

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
            private String vcImportPic;
            private String textMark;
            private int blRecommend;
            private int blTop;
            private String dtSendTime;
            private int intShowCount;
            private String vcCatalogId;
            private String vcSpecialId;
            private String vcTitle;

            public String getVcId() {
                return vcId;
            }

            public void setVcId(String vcId) {
                this.vcId = vcId;
            }

            public String getVcImportPic() {
                return vcImportPic;
            }

            public void setVcImportPic(String vcImportPic) {
                this.vcImportPic = vcImportPic;
            }

            public String getTextMark() {
                return textMark;
            }

            public void setTextMark(String textMark) {
                this.textMark = textMark;
            }

            public int getBlRecommend() {
                return blRecommend;
            }

            public void setBlRecommend(int blRecommend) {
                this.blRecommend = blRecommend;
            }

            public int getBlTop() {
                return blTop;
            }

            public void setBlTop(int blTop) {
                this.blTop = blTop;
            }

            public String getDtSendTime() {
                return dtSendTime;
            }

            public void setDtSendTime(String dtSendTime) {
                this.dtSendTime = dtSendTime;
            }

            public int getIntShowCount() {
                return intShowCount;
            }

            public void setIntShowCount(int intShowCount) {
                this.intShowCount = intShowCount;
            }

            public String getVcCatalogId() {
                return vcCatalogId;
            }

            public void setVcCatalogId(String vcCatalogId) {
                this.vcCatalogId = vcCatalogId;
            }

            public String getVcSpecialId() {
                return vcSpecialId;
            }

            public void setVcSpecialId(String vcSpecialId) {
                this.vcSpecialId = vcSpecialId;
            }

            public String getVcTitle() {
                return vcTitle;
            }

            public void setVcTitle(String vcTitle) {
                this.vcTitle = vcTitle;
            }
        }
    }
}
