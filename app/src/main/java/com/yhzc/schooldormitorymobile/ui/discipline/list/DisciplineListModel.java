package com.yhzc.schooldormitorymobile.ui.discipline.list;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/30 11:25
 * @描述:
 */
public class DisciplineListModel {

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
            private String textContent;
            private List<?> jsonPic;
            private int intPicNum;
            private List<JsonStudentBean> jsonStudent;
            private List<String> jsonStudentIds;
            private String vcStudentNames;
            private String vcUserId;
            private String vcUserName;
            private String dtTime;
            private int intStruts;

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

            public String getTextContent() {
                return textContent;
            }

            public void setTextContent(String textContent) {
                this.textContent = textContent;
            }

            public List<?> getJsonPic() {
                return jsonPic;
            }

            public void setJsonPic(List<?> jsonPic) {
                this.jsonPic = jsonPic;
            }

            public int getIntPicNum() {
                return intPicNum;
            }

            public void setIntPicNum(int intPicNum) {
                this.intPicNum = intPicNum;
            }

            public List<JsonStudentBean> getJsonStudent() {
                return jsonStudent;
            }

            public void setJsonStudent(List<JsonStudentBean> jsonStudent) {
                this.jsonStudent = jsonStudent;
            }

            public List<String> getJsonStudentIds() {
                return jsonStudentIds;
            }

            public void setJsonStudentIds(List<String> jsonStudentIds) {
                this.jsonStudentIds = jsonStudentIds;
            }

            public String getVcStudentNames() {
                return vcStudentNames;
            }

            public void setVcStudentNames(String vcStudentNames) {
                this.vcStudentNames = vcStudentNames;
            }

            public String getVcUserId() {
                return vcUserId;
            }

            public void setVcUserId(String vcUserId) {
                this.vcUserId = vcUserId;
            }

            public String getVcUserName() {
                return vcUserName;
            }

            public void setVcUserName(String vcUserName) {
                this.vcUserName = vcUserName;
            }

            public String getDtTime() {
                return dtTime;
            }

            public void setDtTime(String dtTime) {
                this.dtTime = dtTime;
            }

            public int getIntStruts() {
                return intStruts;
            }

            public void setIntStruts(int intStruts) {
                this.intStruts = intStruts;
            }

            public static class JsonStudentBean {
                private String gradeName;
                private String gradeId;
                private String className;
                private String classId;
                private String name;
                private String id;

                public String getGradeName() {
                    return gradeName;
                }

                public void setGradeName(String gradeName) {
                    this.gradeName = gradeName;
                }

                public String getGradeId() {
                    return gradeId;
                }

                public void setGradeId(String gradeId) {
                    this.gradeId = gradeId;
                }

                public String getClassName() {
                    return className;
                }

                public void setClassName(String className) {
                    this.className = className;
                }

                public String getClassId() {
                    return classId;
                }

                public void setClassId(String classId) {
                    this.classId = classId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }
    }
}
