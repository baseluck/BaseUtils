package com.yhzc.schooldormitorymobile.ui.performanceDetails;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/28 15:03
 * @描述:
 */
public class PerformanceDetailsModel {
    private String title;
    private List<FirstTitle> firstTitles;

    public PerformanceDetailsModel() {
    }

    public PerformanceDetailsModel(String title, List<FirstTitle> firstTitles) {
        this.title = title;
        this.firstTitles = firstTitles;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FirstTitle> getFirstTitles() {
        return firstTitles;
    }

    public void setFirstTitles(List<FirstTitle> firstTitles) {
        this.firstTitles = firstTitles;
    }

    public static class FirstTitle {
        private String title;
        private boolean isSelect;
        private boolean isResult;
        private List<SecondTitle> secondTitles;

        public FirstTitle() {
        }

        public FirstTitle(String title, boolean isSelect, boolean isResult, List<SecondTitle> secondTitles) {
            this.title = title;
            this.isSelect = isSelect;
            this.isResult = isResult;
            this.secondTitles = secondTitles;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public List<SecondTitle> getSecondTitles() {
            return secondTitles;
        }

        public void setSecondTitles(List<SecondTitle> secondTitles) {
            this.secondTitles = secondTitles;
        }

        public static class SecondTitle {
            private String title;
            private boolean isSelect;
            private boolean isResult;
            private List<ThirdTitle> thirdTitles;

            public SecondTitle() {
            }

            public SecondTitle(String title, boolean isSelect, boolean isResult, List<ThirdTitle> thirdTitles) {
                this.title = title;
                this.isSelect = isSelect;
                this.isResult = isResult;
                this.thirdTitles = thirdTitles;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public List<ThirdTitle> getThirdTitles() {
                return thirdTitles;
            }

            public void setThirdTitles(List<ThirdTitle> thirdTitles) {
                this.thirdTitles = thirdTitles;
            }

            public static class ThirdTitle {
                private String content;
                private String zp;
                private String score;

                public ThirdTitle() {
                }

                public ThirdTitle(String content, String zp, String score) {
                    this.content = content;
                    this.zp = zp;
                    this.score = score;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getZp() {
                    return zp;
                }

                public void setZp(String zp) {
                    this.zp = zp;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }
            }

        }
    }
}
