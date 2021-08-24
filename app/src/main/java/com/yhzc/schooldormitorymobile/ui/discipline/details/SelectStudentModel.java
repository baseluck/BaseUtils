package com.yhzc.schooldormitorymobile.ui.discipline.details;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/30 15:43
 * @描述:
 */
public class SelectStudentModel {
    private String name;
    private String id;
    private boolean canChange;

    public SelectStudentModel() {
    }

    public SelectStudentModel(String name, String id, boolean canChange) {
        this.name = name;
        this.id = id;
        this.canChange = canChange;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanChange() {
        return canChange;
    }

    public void setCanChange(boolean canChange) {
        this.canChange = canChange;
    }
}
