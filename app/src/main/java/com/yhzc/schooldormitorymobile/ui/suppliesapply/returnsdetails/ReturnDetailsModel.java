package com.yhzc.schooldormitorymobile.ui.suppliesapply.returnsdetails;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/7 16:44
 * @描述:
 */
public class ReturnDetailsModel {

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
        private String vcUserId;
        private String vcFiles;
        private String vcType;
        private String vcTitle;
        private String textMark;
        private String dtRecordTime;
        private List<DetailsBean> details;

        public String getVcFiles() {
            return vcFiles;
        }

        public void setVcFiles(String vcFiles) {
            this.vcFiles = vcFiles;
        }

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public String getVcUserId() {
            return vcUserId;
        }

        public void setVcUserId(String vcUserId) {
            this.vcUserId = vcUserId;
        }

        public String getVcType() {
            return vcType;
        }

        public void setVcType(String vcType) {
            this.vcType = vcType;
        }

        public String getVcTitle() {
            return vcTitle;
        }

        public void setVcTitle(String vcTitle) {
            this.vcTitle = vcTitle;
        }

        public String getTextMark() {
            return textMark;
        }

        public void setTextMark(String textMark) {
            this.textMark = textMark;
        }

        public String getDtRecordTime() {
            return dtRecordTime;
        }

        public void setDtRecordTime(String dtRecordTime) {
            this.dtRecordTime = dtRecordTime;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public static class DetailsBean {
            private String vcStockRecordId;
            private String vcMaterialId;
            private String vcMaterialCode;
            private String vcMaterialName;
            private String textDesc;
            private List<JsonPicBean> jsonPic;
            private String vcSpecification;
            private String vcUnit;
            private int intNum;

            public String getVcStockRecordId() {
                return vcStockRecordId;
            }

            public void setVcStockRecordId(String vcStockRecordId) {
                this.vcStockRecordId = vcStockRecordId;
            }

            public String getVcMaterialId() {
                return vcMaterialId;
            }

            public void setVcMaterialId(String vcMaterialId) {
                this.vcMaterialId = vcMaterialId;
            }

            public String getVcMaterialCode() {
                return vcMaterialCode;
            }

            public void setVcMaterialCode(String vcMaterialCode) {
                this.vcMaterialCode = vcMaterialCode;
            }

            public String getVcMaterialName() {
                return vcMaterialName;
            }

            public void setVcMaterialName(String vcMaterialName) {
                this.vcMaterialName = vcMaterialName;
            }

            public String getTextDesc() {
                return textDesc;
            }

            public void setTextDesc(String textDesc) {
                this.textDesc = textDesc;
            }

            public List<JsonPicBean> getJsonPic() {
                return jsonPic;
            }

            public void setJsonPic(List<JsonPicBean> jsonPic) {
                this.jsonPic = jsonPic;
            }

            public String getVcSpecification() {
                return vcSpecification;
            }

            public void setVcSpecification(String vcSpecification) {
                this.vcSpecification = vcSpecification;
            }

            public String getVcUnit() {
                return vcUnit;
            }

            public void setVcUnit(String vcUnit) {
                this.vcUnit = vcUnit;
            }

            public int getIntNum() {
                return intNum;
            }

            public void setIntNum(int intNum) {
                this.intNum = intNum;
            }

            public static class JsonPicBean {
                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
