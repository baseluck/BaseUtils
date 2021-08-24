package com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/28 13:40
 * @描述:
 */
public class UpNoticeModel {
    private String vcTitle;
    private String vcSendUser;
    private int intType;
    private int intStatus;
    private int isReply;
    private List<String> vcImg;
    private List<String> vcVideo;
    private String vcContent;
    private String textMark;

    public String getVcTitle() {
        return vcTitle;
    }

    public void setVcTitle(String vcTitle) {
        this.vcTitle = vcTitle;
    }

    public String getVcSendUser() {
        return vcSendUser;
    }

    public void setVcSendUser(String vcSendUser) {
        this.vcSendUser = vcSendUser;
    }

    public int getIntType() {
        return intType;
    }

    public void setIntType(int intType) {
        this.intType = intType;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }

    public int getIsReply() {
        return isReply;
    }

    public void setIsReply(int isReply) {
        this.isReply = isReply;
    }

    public List<String> getVcImg() {
        return vcImg;
    }

    public void setVcImg(List<String> vcImg) {
        this.vcImg = vcImg;
    }

    public List<String> getVcVideo() {
        return vcVideo;
    }

    public void setVcVideo(List<String> vcVideo) {
        this.vcVideo = vcVideo;
    }

    public String getVcContent() {
        return vcContent;
    }

    public void setVcContent(String vcContent) {
        this.vcContent = vcContent;
    }

    public String getTextMark() {
        return textMark;
    }

    public void setTextMark(String textMark) {
        this.textMark = textMark;
    }
}
