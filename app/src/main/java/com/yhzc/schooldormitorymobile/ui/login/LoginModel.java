package com.yhzc.schooldormitorymobile.ui.login;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/19 14:02
 * @描述: 登录返回
 */
public class LoginModel {

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
        private String token;
        private String theme;
        private String vcRealName;
        private String vcSex;
        private String vcHeadPic;
        private String vcFacePic;
        private String vcZjkm;
        /**
         * 部门
         */
        private String vcDept;
        private String vcPostion;
        private String vcPhone;
        private String vcEmail;
        private String vcFaceToken;

        public String getVcFaceToken() {
            return vcFaceToken;
        }

        public void setVcFaceToken(String vcFaceToken) {
            this.vcFaceToken = vcFaceToken;
        }

        public String getVcZjkm() {
            return vcZjkm;
        }

        public void setVcZjkm(String vcZjkm) {
            this.vcZjkm = vcZjkm;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getVcRealName() {
            return vcRealName;
        }

        public void setVcRealName(String vcRealName) {
            this.vcRealName = vcRealName;
        }

        public String getVcSex() {
            return vcSex;
        }

        public void setVcSex(String vcSex) {
            this.vcSex = vcSex;
        }

        public String getVcHeadPic() {
            return vcHeadPic;
        }

        public void setVcHeadPic(String vcHeadPic) {
            this.vcHeadPic = vcHeadPic;
        }

        public String getVcFacePic() {
            return vcFacePic;
        }

        public void setVcFacePic(String vcFacePic) {
            this.vcFacePic = vcFacePic;
        }

        public String getVcDept() {
            return vcDept;
        }

        public void setVcDept(String vcDept) {
            this.vcDept = vcDept;
        }

        public String getVcPostion() {
            return vcPostion;
        }

        public void setVcPostion(String vcPostion) {
            this.vcPostion = vcPostion;
        }

        public String getVcPhone() {
            return vcPhone;
        }

        public void setVcPhone(String vcPhone) {
            this.vcPhone = vcPhone;
        }

        public String getVcEmail() {
            return vcEmail;
        }

        public void setVcEmail(String vcEmail) {
            this.vcEmail = vcEmail;
        }
    }
}
