package com.yhzc.schooldormitorymobile.ui.notetake.list;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/22 11:08
 * @描述:
 */
public class NoteTypeListModel {

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

    public static class DataBean {
        private String vcIcon;
        private String vcName;
        private int isSys;
        private String vcId;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getVcIcon() {
            return vcIcon;
        }

        public void setVcIcon(String vcIcon) {
            this.vcIcon = vcIcon;
        }

        public String getVcName() {
            return vcName;
        }

        public void setVcName(String vcName) {
            this.vcName = vcName;
        }

        public int getIsSys() {
            return isSys;
        }

        public void setIsSys(int isSys) {
            this.isSys = isSys;
        }

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }
    }
}
