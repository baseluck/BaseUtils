package com.luck.basemodule.utils;

import android.graphics.Rect;
import android.os.Parcel;

import androidx.annotation.Nullable;

import com.previewlibrary.enitity.IThumbViewInfo;

/**
* @author: Procyonlotor
* @创建日期: 2021/5/8 20:02
* @描述: 图标放大展示bean
*/
public class CompanyImgInfo implements IThumbViewInfo {
    private String url;  
    private Rect mBounds; 
    private String user = "用户字段";
    private String videoUrl;

    public CompanyImgInfo(String url) {
        this.url = url;
    }
    public CompanyImgInfo(String videoUrl, String url) {
        this.url = url;
        this.videoUrl = videoUrl;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getUrl() {//将你的图片地址字段返回
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Rect getBounds() {//将你的图片显示坐标字段返回
        return mBounds;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mBounds, flags);
        dest.writeString(this.user);
        dest.writeString(this.videoUrl);
    }

    protected CompanyImgInfo(Parcel in) {
        this.url = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
        this.user = in.readString();
        this.videoUrl = in.readString();
    }

    public static final Creator<CompanyImgInfo> CREATOR = new Creator<CompanyImgInfo>() {
        @Override
        public CompanyImgInfo createFromParcel(Parcel source) {
            return new CompanyImgInfo(source);
        }

        @Override
        public CompanyImgInfo[] newArray(int size) {
            return new CompanyImgInfo[size];
        }
    };
}
