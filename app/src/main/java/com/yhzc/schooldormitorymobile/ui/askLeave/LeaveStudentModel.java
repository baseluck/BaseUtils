package com.yhzc.schooldormitorymobile.ui.askLeave;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 10:57
 * @描述:
 */
public class LeaveStudentModel {

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
        private String vcName;
        private String vcId;

        public String getVcName() {
            return vcName;
        }

        public void setVcName(String vcName) {
            this.vcName = vcName;
        }

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        @Override
        public String getPickerViewText() {
            return vcName;
        }
    }
}
