package com.yhzc.schooldormitorymobile.ui.performance.rank;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/29 15:55
 * @描述:
 */
public class PerformanceRankModel {

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
        private List<RankBean> rank;
        private String title;
        private String examineTime;

        public List<RankBean> getRank() {
            return rank;
        }

        public void setRank(List<RankBean> rank) {
            this.rank = rank;
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

        public static class RankBean {
            private String vcUserId;
            private String score;
            private String rank;

            public String getVcUserId() {
                return vcUserId;
            }

            public void setVcUserId(String vcUserId) {
                this.vcUserId = vcUserId;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }
        }
    }
}
