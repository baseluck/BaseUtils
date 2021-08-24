package com.yhzc.schooldormitorymobile.ui.modifystudent.studentdetails;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/19 17:52
 * @描述:
 */
public class SexModel {

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

        public static class ListBean implements IPickerViewData {
            private String id;
            private String parentId;
            private boolean hasChildren;
            private String fullName;
            private String enCode;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public boolean isHasChildren() {
                return hasChildren;
            }

            public void setHasChildren(boolean hasChildren) {
                this.hasChildren = hasChildren;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public String getEnCode() {
                return enCode;
            }

            public void setEnCode(String enCode) {
                this.enCode = enCode;
            }

            @Override
            public String getPickerViewText() {
                return fullName;
            }
        }
    }
}
