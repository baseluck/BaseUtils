package com.yhzc.schooldormitorymobile.ui.suppliesapply.apply;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/26 17:21
 * @描述:
 */
public class WzListModel {

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
        private String vcId;
        private int intLockStock;
        private String vcMaterialId;
        private String vcCode;
        private String vcName;
        private String textDesc;
        private String vcSpecification;
        private String vcUnit;
        private int intNum;

        public String getVcId() {
            return vcId;
        }

        public void setVcId(String vcId) {
            this.vcId = vcId;
        }

        public int getIntLockStock() {
            return intLockStock;
        }

        public void setIntLockStock(int intLockStock) {
            this.intLockStock = intLockStock;
        }

        public String getVcMaterialId() {
            return vcMaterialId;
        }

        public void setVcMaterialId(String vcMaterialId) {
            this.vcMaterialId = vcMaterialId;
        }

        public String getVcCode() {
            return vcCode;
        }

        public void setVcCode(String vcCode) {
            this.vcCode = vcCode;
        }

        public String getVcName() {
            return vcName;
        }

        public void setVcName(String vcName) {
            this.vcName = vcName;
        }

        public String getTextDesc() {
            return textDesc;
        }

        public void setTextDesc(String textDesc) {
            this.textDesc = textDesc;
        }


        public String getVcSpecification() {
            return vcSpecification;
        }

        public void setVcSpecification(String vcSpecification) {
            this.vcSpecification = vcSpecification;
        }

        public String getVcUnit() {
            return vcUnit;
        }

        public void setVcUnit(String vcUnit) {
            this.vcUnit = vcUnit;
        }

        public int getIntNum() {
            return intNum;
        }

        public void setIntNum(int intNum) {
            this.intNum = intNum;
        }

        @Override
        public String getPickerViewText() {
            return vcName;
        }
    }
}
