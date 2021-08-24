package com.yhzc.schooldormitorymobile.ui.home.fragment.home;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/19 17:45
 * @描述:
 */
public class NoticeModel implements Parcelable {


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

    public static class DataBean implements Parcelable {
        private String id;
        private int type;
        private String title;
        private String bodyText;
        private int enabledMark;
        private String creatorTime;
        private String creatorUser;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBodyText() {
            return bodyText;
        }

        public void setBodyText(String bodyText) {
            this.bodyText = bodyText;
        }

        public int getEnabledMark() {
            return enabledMark;
        }

        public void setEnabledMark(int enabledMark) {
            this.enabledMark = enabledMark;
        }

        public String getCreatorTime() {
            return creatorTime;
        }

        public void setCreatorTime(String creatorTime) {
            this.creatorTime = creatorTime;
        }

        public String getCreatorUser() {
            return creatorUser;
        }

        public void setCreatorUser(String creatorUser) {
            this.creatorUser = creatorUser;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeInt(this.type);
            dest.writeString(this.title);
            dest.writeString(this.bodyText);
            dest.writeInt(this.enabledMark);
            dest.writeString(this.creatorTime);
            dest.writeString(this.creatorUser);
        }

        public void readFromParcel(Parcel source) {
            this.id = source.readString();
            this.type = source.readInt();
            this.title = source.readString();
            this.bodyText = source.readString();
            this.enabledMark = source.readInt();
            this.creatorTime = source.readString();
            this.creatorUser = source.readString();
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.type = in.readInt();
            this.title = in.readString();
            this.bodyText = in.readString();
            this.enabledMark = in.readInt();
            this.creatorTime = in.readString();
            this.creatorUser = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
        dest.writeList(this.data);
    }

    public void readFromParcel(Parcel source) {
        this.code = source.readInt();
        this.msg = source.readString();
        this.data = new ArrayList<>();
        source.readList(this.data, DataBean.class.getClassLoader());
    }

    public NoticeModel() {
    }

    protected NoticeModel(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
        this.data = new ArrayList<>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<NoticeModel> CREATOR = new Parcelable.Creator<NoticeModel>() {
        @Override
        public NoticeModel createFromParcel(Parcel source) {
            return new NoticeModel(source);
        }

        @Override
        public NoticeModel[] newArray(int size) {
            return new NoticeModel[size];
        }
    };
}
