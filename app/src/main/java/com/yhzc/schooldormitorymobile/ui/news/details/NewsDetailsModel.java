package com.yhzc.schooldormitorymobile.ui.news.details;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/19 18:46
 * @描述:
 */
public class NewsDetailsModel {

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
        private int blBold;
        private int blItalic;
        private int blOut;
        private int blRecommend;
        private int blSendAd;
        private int blTop;
        private long dtDownTime;
        private long dtSendTime;
        private int intOrder;
        private int intShowCount;
        private String textContent;
        private String textMark;
        private String vcAdTypeId;
        private String vcAuthor;
        private String vcCatalogId;
        private String vcFrom;
        private String vcFromUrl;
        private String vcImportPic;
        private String vcKeys;
        private String vcOutUrl;
        private String vcSpecialId;
        private String vcTitle;

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public int getBlBold() {
            return blBold;
        }

        public void setBlBold(int blBold) {
            this.blBold = blBold;
        }

        public int getBlItalic() {
            return blItalic;
        }

        public void setBlItalic(int blItalic) {
            this.blItalic = blItalic;
        }

        public int getBlOut() {
            return blOut;
        }

        public void setBlOut(int blOut) {
            this.blOut = blOut;
        }

        public int getBlRecommend() {
            return blRecommend;
        }

        public void setBlRecommend(int blRecommend) {
            this.blRecommend = blRecommend;
        }

        public int getBlSendAd() {
            return blSendAd;
        }

        public void setBlSendAd(int blSendAd) {
            this.blSendAd = blSendAd;
        }

        public int getBlTop() {
            return blTop;
        }

        public void setBlTop(int blTop) {
            this.blTop = blTop;
        }

        public long getDtDownTime() {
            return dtDownTime;
        }

        public void setDtDownTime(long dtDownTime) {
            this.dtDownTime = dtDownTime;
        }

        public long getDtSendTime() {
            return dtSendTime;
        }

        public void setDtSendTime(long dtSendTime) {
            this.dtSendTime = dtSendTime;
        }

        public int getIntOrder() {
            return intOrder;
        }

        public void setIntOrder(int intOrder) {
            this.intOrder = intOrder;
        }

        public int getIntShowCount() {
            return intShowCount;
        }

        public void setIntShowCount(int intShowCount) {
            this.intShowCount = intShowCount;
        }

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public String getTextMark() {
            return textMark;
        }

        public void setTextMark(String textMark) {
            this.textMark = textMark;
        }

        public String getVcAdTypeId() {
            return vcAdTypeId;
        }

        public void setVcAdTypeId(String vcAdTypeId) {
            this.vcAdTypeId = vcAdTypeId;
        }

        public String getVcAuthor() {
            return vcAuthor;
        }

        public void setVcAuthor(String vcAuthor) {
            this.vcAuthor = vcAuthor;
        }

        public String getVcCatalogId() {
            return vcCatalogId;
        }

        public void setVcCatalogId(String vcCatalogId) {
            this.vcCatalogId = vcCatalogId;
        }

        public String getVcFrom() {
            return vcFrom;
        }

        public void setVcFrom(String vcFrom) {
            this.vcFrom = vcFrom;
        }

        public String getVcFromUrl() {
            return vcFromUrl;
        }

        public void setVcFromUrl(String vcFromUrl) {
            this.vcFromUrl = vcFromUrl;
        }

        public String getVcImportPic() {
            return vcImportPic;
        }

        public void setVcImportPic(String vcImportPic) {
            this.vcImportPic = vcImportPic;
        }

        public String getVcKeys() {
            return vcKeys;
        }

        public void setVcKeys(String vcKeys) {
            this.vcKeys = vcKeys;
        }

        public String getVcOutUrl() {
            return vcOutUrl;
        }

        public void setVcOutUrl(String vcOutUrl) {
            this.vcOutUrl = vcOutUrl;
        }

        public String getVcSpecialId() {
            return vcSpecialId;
        }

        public void setVcSpecialId(String vcSpecialId) {
            this.vcSpecialId = vcSpecialId;
        }

        public String getVcTitle() {
            return vcTitle;
        }

        public void setVcTitle(String vcTitle) {
            this.vcTitle = vcTitle;
        }
    }
}
