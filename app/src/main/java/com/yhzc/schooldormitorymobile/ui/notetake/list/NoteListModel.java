package com.yhzc.schooldormitorymobile.ui.notetake.list;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/22 16:29
 * @描述:
 */
public class NoteListModel {

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
        private String vcCateName;
        private String vcIcon;
        private List<DateListBean> dateList;
        private String vcMemoCateId;
        private int isSys;
        private int intTotalNum;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getVcCateName() {
            return vcCateName;
        }

        public void setVcCateName(String vcCateName) {
            this.vcCateName = vcCateName;
        }

        public String getVcIcon() {
            return vcIcon;
        }

        public void setVcIcon(String vcIcon) {
            this.vcIcon = vcIcon;
        }

        public List<DateListBean> getDateList() {
            return dateList;
        }

        public void setDateList(List<DateListBean> dateList) {
            this.dateList = dateList;
        }

        public String getVcMemoCateId() {
            return vcMemoCateId;
        }

        public void setVcMemoCateId(String vcMemoCateId) {
            this.vcMemoCateId = vcMemoCateId;
        }

        public int getIsSys() {
            return isSys;
        }

        public void setIsSys(int isSys) {
            this.isSys = isSys;
        }

        public int getIntTotalNum() {
            return intTotalNum;
        }

        public void setIntTotalNum(int intTotalNum) {
            this.intTotalNum = intTotalNum;
        }

        public static class DateListBean {
            private List<RecordListBean> recordList;
            private int totalNum;
            private String recordDay;

            public List<RecordListBean> getRecordList() {
                return recordList;
            }

            public void setRecordList(List<RecordListBean> recordList) {
                this.recordList = recordList;
            }

            public int getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(int totalNum) {
                this.totalNum = totalNum;
            }

            public String getRecordDay() {
                return recordDay;
            }

            public void setRecordDay(String recordDay) {
                this.recordDay = recordDay;
            }

            public static class RecordListBean {
                private String textMark;
                private String intNum;
                private String dtCreateTime;
                private String dtRecordDay;
                private String vcId;

                public String getTextMark() {
                    return textMark;
                }

                public void setTextMark(String textMark) {
                    this.textMark = textMark;
                }

                public String getIntNum() {
                    return intNum;
                }

                public void setIntNum(String intNum) {
                    this.intNum = intNum;
                }

                public String getDtCreateTime() {
                    return dtCreateTime;
                }

                public void setDtCreateTime(String dtCreateTime) {
                    this.dtCreateTime = dtCreateTime;
                }

                public String getDtRecordDay() {
                    return dtRecordDay;
                }

                public void setDtRecordDay(String dtRecordDay) {
                    this.dtRecordDay = dtRecordDay;
                }

                public String getVcId() {
                    return vcId;
                }

                public void setVcId(String vcId) {
                    this.vcId = vcId;
                }
            }
        }
    }
}
