package com.yhzc.schooldormitorymobile.ui.discipline.details;

import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/30 14:31
 * @描述:
 */
public class StudentListModel {

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

    public static class DataBean implements IPickerViewData {
        private String name;
        private String id;
        private List<ChildBean> child;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        @Override
        public String getPickerViewText() {
            return name;
        }

        public static class ChildBean implements IPickerViewData {
            private String name;
            private String id;
            @SerializedName("child")
            private List<ChildsBean> child;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<ChildsBean> getChild() {
                return child;
            }

            public void setChild(List<ChildsBean> child) {
                this.child = child;
            }

            @Override
            public String getPickerViewText() {
                return name;
            }

            public static class ChildsBean implements IPickerViewData {
                private String name;
                private String id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                @Override
                public String getPickerViewText() {
                    return name;
                }
            }
        }
    }
}
