package com.yhzc.schooldormitorymobile.ui.performance;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 17:53
 * @描述:
 */
public class PerforManceModel {

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String schoolYear;
        private List<AssessmentListBean> assessmentList;

        public String getSchoolYear() {
            return schoolYear;
        }

        public void setSchoolYear(String schoolYear) {
            this.schoolYear = schoolYear;
        }

        public List<AssessmentListBean> getAssessmentList() {
            return assessmentList;
        }

        public void setAssessmentList(List<AssessmentListBean> assessmentList) {
            this.assessmentList = assessmentList;
        }

        public static class AssessmentListBean {
            private boolean isFillResult;
            private String progress;
            private String id;
            private String title;
            private boolean isSubmit;
            private boolean isSpEnd;
            private String examineTime;
            private String fillTime;
            private String status;

            public String getFillTime() {
                return fillTime;
            }

            public void setFillTime(String fillTime) {
                this.fillTime = fillTime;
            }

            public boolean isSpEnd() {
                return isSpEnd;
            }

            public void setSpEnd(boolean spEnd) {
                isSpEnd = spEnd;
            }

            public boolean isSubmit() {
                return isSubmit;
            }

            public void setSubmit(boolean submit) {
                isSubmit = submit;
            }

            public boolean isIsFillResult() {
                return isFillResult;
            }

            public void setIsFillResult(boolean isFillResult) {
                this.isFillResult = isFillResult;
            }

            public String getProgress() {
                return progress;
            }

            public void setProgress(String progress) {
                this.progress = progress;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getExamineTime() {
                return examineTime;
            }

            public void setExamineTime(String examineTime) {
                this.examineTime = examineTime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
