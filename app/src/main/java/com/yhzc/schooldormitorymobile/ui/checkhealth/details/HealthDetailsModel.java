package com.yhzc.schooldormitorymobile.ui.checkhealth.details;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/17 16:45
 * @描述:
 */
public class HealthDetailsModel {

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
        private List<String> jsonPic;
        private List<ListBean> list;

        public List<String> getJsonPic() {
            return jsonPic;
        }

        public void setJsonPic(List<String> jsonPic) {
            this.jsonPic = jsonPic;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String name;
            private boolean isSelect;
            private List<ChildBean> child;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                private int intSortCode;
                private int intScoreMax;
                private String vcDetailsTitle;
                private String vcId;
                private int intScoreMin;
                private int intStruts;
                private int intCheckStruts;
                private int intScoreDef;

                public String getVcId() {
                    return vcId;
                }

                public void setVcId(String vcId) {
                    this.vcId = vcId;
                }

                public int getIntSortCode() {
                    return intSortCode;
                }

                public void setIntSortCode(int intSortCode) {
                    this.intSortCode = intSortCode;
                }

                public int getIntScoreMax() {
                    return intScoreMax;
                }

                public void setIntScoreMax(int intScoreMax) {
                    this.intScoreMax = intScoreMax;
                }

                public String getVcDetailsTitle() {
                    return vcDetailsTitle;
                }

                public void setVcDetailsTitle(String vcDetailsTitle) {
                    this.vcDetailsTitle = vcDetailsTitle;
                }

                public int getIntScoreMin() {
                    return intScoreMin;
                }

                public void setIntScoreMin(int intScoreMin) {
                    this.intScoreMin = intScoreMin;
                }

                public int getIntStruts() {
                    return intStruts;
                }

                public void setIntStruts(int intStruts) {
                    this.intStruts = intStruts;
                }

                public int getIntCheckStruts() {
                    return intCheckStruts;
                }

                public void setIntCheckStruts(int intCheckStruts) {
                    this.intCheckStruts = intCheckStruts;
                }

                public int getIntScoreDef() {
                    return intScoreDef;
                }

                public void setIntScoreDef(int intScoreDef) {
                    this.intScoreDef = intScoreDef;
                }
            }
        }
    }
}
