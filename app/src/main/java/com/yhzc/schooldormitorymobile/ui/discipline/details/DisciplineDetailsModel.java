package com.yhzc.schooldormitorymobile.ui.discipline.details;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/30 16:12
 * @描述:
 */
public class DisciplineDetailsModel {

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
        private String vcId;
        private String vcTitle;
        private String textContent;
        private List<String> jsonPic;
        private List<JsonStudentBean> jsonStudent;
        private String vcUserId;
        private String dtTime;
        private int intStruts;
        private String vcUserName;
        private List<String> jsonStudentIds;
        private String vcStudentNames;
        private int intLevel;
        private String vcLevel;
        private int intPicNum;

        public String getVcLevel() {
            return vcLevel;
        }

        public void setVcLevel(String vcLevel) {
            this.vcLevel = vcLevel;
        }

        public int getIntLevel() {
            return intLevel;
        }

        public void setIntLevel(int intLevel) {
            this.intLevel = intLevel;
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

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public List<String> getJsonPic() {
            return jsonPic;
        }

        public void setJsonPic(List<String> jsonPic) {
            this.jsonPic = jsonPic;
        }

        public List<JsonStudentBean> getJsonStudent() {
            return jsonStudent;
        }

        public void setJsonStudent(List<JsonStudentBean> jsonStudent) {
            this.jsonStudent = jsonStudent;
        }

        public String getVcUserId() {
            return vcUserId;
        }

        public void setVcUserId(String vcUserId) {
            this.vcUserId = vcUserId;
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

        public String getVcUserName() {
            return vcUserName;
        }

        public void setVcUserName(String vcUserName) {
            this.vcUserName = vcUserName;
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

        public int getIntPicNum() {
            return intPicNum;
        }

        public void setIntPicNum(int intPicNum) {
            this.intPicNum = intPicNum;
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
