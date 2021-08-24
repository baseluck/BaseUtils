package com.yhzc.schooldormitorymobile.ui.discipline.details;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/6 15:34
 * @描述:
 */
public class DisciplineLevelModel {

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
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String getPickerViewText() {
            return name;
        }
    }
}
