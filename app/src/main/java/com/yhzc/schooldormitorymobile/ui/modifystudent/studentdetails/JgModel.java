package com.yhzc.schooldormitorymobile.ui.modifystudent.studentdetails;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/20 10:21
 * @描述:
 */
public class JgModel {

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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String id;
            private String fullName;
            private boolean isLeaf;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public boolean isIsLeaf() {
                return isLeaf;
            }

            public void setIsLeaf(boolean isLeaf) {
                this.isLeaf = isLeaf;
            }
        }
    }
}
