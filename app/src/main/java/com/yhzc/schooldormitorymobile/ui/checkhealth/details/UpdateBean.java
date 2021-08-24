package com.yhzc.schooldormitorymobile.ui.checkhealth.details;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/17 17:48
 * @描述:
 */
public class UpdateBean {

    private String id;
    private List<ListBean> list;
    private List<String> jsonPic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<String> getJsonPic() {
        return jsonPic;
    }

    public void setJsonPic(List<String> jsonPic) {
        this.jsonPic = jsonPic;
    }

    public static class ListBean {
        private String id;
        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
