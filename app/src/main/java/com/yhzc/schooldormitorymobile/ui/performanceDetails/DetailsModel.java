package com.yhzc.schooldormitorymobile.ui.performanceDetails;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/15 16:52
 * @描述:
 */
public class DetailsModel {

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
        private String title;
        private String rank;
        private int totalScore;
        private List<ContentBean> content;

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            private String firstTitle;
            private boolean isSelect;
            private boolean isResult;
            private List<FirstListBean> firstList;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public boolean isResult() {
                return isResult;
            }

            public void setResult(boolean result) {
                isResult = result;
            }

            public String getFirstTitle() {
                return firstTitle;
            }

            public void setFirstTitle(String firstTitle) {
                this.firstTitle = firstTitle;
            }

            public List<FirstListBean> getFirstList() {
                return firstList;
            }

            public void setFirstList(List<FirstListBean> firstList) {
                this.firstList = firstList;
            }

            public static class FirstListBean {
                private List<SecondListBean> secondList;
                private boolean isSelect;
                private boolean isResult;
                private String secondTitle;
                private int sort;

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public boolean isSelect() {
                    return isSelect;
                }

                public void setSelect(boolean select) {
                    isSelect = select;
                }

                public boolean isResult() {
                    return isResult;
                }

                public void setResult(boolean result) {
                    isResult = result;
                }

                public List<SecondListBean> getSecondList() {
                    return secondList;
                }

                public void setSecondList(List<SecondListBean> secondList) {
                    this.secondList = secondList;
                }

                public String getSecondTitle() {
                    return secondTitle;
                }

                public void setSecondTitle(String secondTitle) {
                    this.secondTitle = secondTitle;
                }

                public static class SecondListBean {
                    private String vcXzId;
                    private int dlItemScore;
                    private String kpxz;
                    private String zpnr;
                    private String textZp;
                    private String zpfs;
                    private String vcResultId;
                    private String intItemIntSize;
                    private String intItemMaxSize;
                    private String sl;
                    private String bz;
                    private int sort;
                    private String dlEndScore;
                    private String intExamineSize;
                    private String intSize;
                    private String textExamineRemark;

                    public String getDlEndScore() {
                        return dlEndScore;
                    }

                    public void setDlEndScore(String dlEndScore) {
                        this.dlEndScore = dlEndScore;
                    }

                    public String getIntExamineSize() {
                        return intExamineSize;
                    }

                    public void setIntExamineSize(String intExamineSize) {
                        this.intExamineSize = intExamineSize;
                    }

                    public String getTextExamineRemark() {
                        return textExamineRemark;
                    }

                    public void setTextExamineRemark(String textExamineRemark) {
                        this.textExamineRemark = textExamineRemark;
                    }

                    public String getIntItemIntSize() {
                        return intItemIntSize;
                    }

                    public void setIntItemIntSize(String intItemIntSize) {
                        this.intItemIntSize = intItemIntSize;
                    }

                    public String getIntItemMaxSize() {
                        return intItemMaxSize;
                    }

                    public void setIntItemMaxSize(String intItemMaxSize) {
                        this.intItemMaxSize = intItemMaxSize;
                    }

                    public String getVcResultId() {
                        return vcResultId;
                    }

                    public void setVcResultId(String vcResultId) {
                        this.vcResultId = vcResultId;
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

                    public int getSort() {
                        return sort;
                    }

                    public void setSort(int sort) {
                        this.sort = sort;
                    }

                    public int getDlItemScore() {
                        return dlItemScore;
                    }

                    public void setDlItemScore(int dlItemScore) {
                        this.dlItemScore = dlItemScore;
                    }

                    public String getZpnr() {
                        return zpnr;
                    }

                    public void setZpnr(String zpnr) {
                        this.zpnr = zpnr;
                    }

                    public String getZpfs() {
                        return zpfs;
                    }

                    public void setZpfs(String zpfs) {
                        this.zpfs = zpfs;
                    }

                    public String getSl() {
                        return sl;
                    }

                    public void setSl(String sl) {
                        this.sl = sl;
                    }

                    public String getBz() {
                        return bz;
                    }

                    public void setBz(String bz) {
                        this.bz = bz;
                    }

                    public String getVcXzId() {
                        return vcXzId;
                    }

                    public void setVcXzId(String vcXzId) {
                        this.vcXzId = vcXzId;
                    }

                    public String getKpxz() {
                        return kpxz;
                    }

                    public void setKpxz(String kpxz) {
                        this.kpxz = kpxz;
                    }
                }
            }
        }
    }
}
