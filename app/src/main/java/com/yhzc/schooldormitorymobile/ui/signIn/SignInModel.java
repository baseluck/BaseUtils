package com.yhzc.schooldormitorymobile.ui.signIn;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @描述:
 * @创建日期: 2021/4/14 10:23
 * @author: ProcyonLotor
 */

public class SignInModel {

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
        private SignLocationBean signLocation;
        private List<SignListBean> signList;
        @SerializedName("MultiPointLocation")
        private List<MultiPointLocationBean> multiPointLocation;

        public List<MultiPointLocationBean> getMultiPointLocation() {
            return multiPointLocation;
        }

        public void setMultiPointLocation(List<MultiPointLocationBean> multiPointLocation) {
            this.multiPointLocation = multiPointLocation;
        }

        public SignLocationBean getSignLocation() {
            return signLocation;
        }

        public void setSignLocation(SignLocationBean signLocation) {
            this.signLocation = signLocation;
        }

        public List<SignListBean> getSignList() {
            return signList;
        }

        public void setSignList(List<SignListBean> signList) {
            this.signList = signList;
        }

        public static class MultiPointLocationBean{
            private String latitude;
            private String longitude;

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }

        public static class SignLocationBean {
            private String latitude;
            private int radius;
            private String longitude;

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public int getRadius() {
                return radius;
            }

            public void setRadius(int radius) {
                this.radius = radius;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }

        public static class SignListBean {
            private String signTime;
            private String signStatus;
            private String signType;

            public String getSignTime() {
                return signTime;
            }

            public void setSignTime(String signTime) {
                this.signTime = signTime;
            }

            public String getSignStatus() {
                return signStatus;
            }

            public void setSignStatus(String signStatus) {
                this.signStatus = signStatus;
            }

            public String getSignType() {
                return signType;
            }

            public void setSignType(String signType) {
                this.signType = signType;
            }
        }
    }
}
