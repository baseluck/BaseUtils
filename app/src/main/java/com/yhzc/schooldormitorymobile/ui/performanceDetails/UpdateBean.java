package com.yhzc.schooldormitorymobile.ui.performanceDetails;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/16 17:18
 * @描述:
 */
public class UpdateBean {

    private List<ContentBean> content;
    private String planId;
    private String isSubmit;

    public String getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }


    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public static class ContentBean {
        private String textMark;
        private String vcXzId;
        private String textZp;
        private String intSize;
        private String dlZpScore;

        public String getTextMark() {
            return textMark;
        }

        public void setTextMark(String textMark) {
            this.textMark = textMark;
        }

        public String getVcXzId() {
            return vcXzId;
        }

        public void setVcXzId(String vcXzId) {
            this.vcXzId = vcXzId;
        }

        public String getTextZp() {
            return textZp;
        }

        public void setTextZp(String textZp) {
            this.textZp = textZp;
        }

        public String getIntSize() {
            return intSize;
        }

        public void setIntSize(String intSize) {
            this.intSize = intSize;
        }

        public String getDlZpScore() {
            return dlZpScore;
        }

        public void setDlZpScore(String dlZpScore) {
            this.dlZpScore = dlZpScore;
        }
    }
}
