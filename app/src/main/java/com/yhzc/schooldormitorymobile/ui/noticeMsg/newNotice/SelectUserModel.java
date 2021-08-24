package com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice;

import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/25 17:01
 * @描述:
 */
public class SelectUserModel {

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
            private String fullName;
            private boolean hasChildren;
            private int enabledMark;
            private List<ChildrenBean> children;
            private String type;
            private String icon;

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

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public boolean isHasChildren() {
                return hasChildren;
            }

            public void setHasChildren(boolean hasChildren) {
                this.hasChildren = hasChildren;
            }

            public int getEnabledMark() {
                return enabledMark;
            }

            public void setEnabledMark(int enabledMark) {
                this.enabledMark = enabledMark;
            }

            public List<ChildrenBean> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBean> children) {
                this.children = children;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            @Override
            public String getPickerViewText() {
                return fullName;
            }

            public static class ChildrenBean {
                private String id;
                private String parentId;
                private String fullName;
                private boolean hasChildren;
                private int enabledMark;
                @SerializedName("children")
                private List<ChildrensBean> children;
                private String type;
                private String icon;

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

                public String getFullName() {
                    return fullName;
                }

                public void setFullName(String fullName) {
                    this.fullName = fullName;
                }

                public boolean isHasChildren() {
                    return hasChildren;
                }

                public void setHasChildren(boolean hasChildren) {
                    this.hasChildren = hasChildren;
                }

                public int getEnabledMark() {
                    return enabledMark;
                }

                public void setEnabledMark(int enabledMark) {
                    this.enabledMark = enabledMark;
                }

                public List<ChildrensBean> getChildren() {
                    return children;
                }

                public void setChildren(List<ChildrensBean> children) {
                    this.children = children;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public static class ChildrensBean {
                    private String id;
                    private String parentId;
                    private String fullName;
                    private boolean hasChildren;
                    private String type;
                    private String icon;

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

                    public String getFullName() {
                        return fullName;
                    }

                    public void setFullName(String fullName) {
                        this.fullName = fullName;
                    }

                    public boolean isHasChildren() {
                        return hasChildren;
                    }

                    public void setHasChildren(boolean hasChildren) {
                        this.hasChildren = hasChildren;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getIcon() {
                        return icon;
                    }

                    public void setIcon(String icon) {
                        this.icon = icon;
                    }
                }
            }
        }
    }
}
