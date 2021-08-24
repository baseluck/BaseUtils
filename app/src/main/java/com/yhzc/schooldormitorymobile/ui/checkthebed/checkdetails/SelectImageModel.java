package com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/17 14:37
 * @描述:
 */
public class SelectImageModel {
    String imgUrl;
    String imgType;

    public SelectImageModel() {
    }

    public SelectImageModel(String imgUrl, String imgType) {
        this.imgUrl = imgUrl;
        this.imgType = imgType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }
}
