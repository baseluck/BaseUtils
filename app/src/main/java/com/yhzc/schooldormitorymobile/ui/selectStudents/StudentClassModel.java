package com.yhzc.schooldormitorymobile.ui.selectStudents;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 16:51
 * @描述:
 */
public class StudentClassModel {
    private String className;
    private boolean isSelected;

    public StudentClassModel() {
    }

    public StudentClassModel(String className, boolean isSelected) {
        this.className = className;
        this.isSelected = isSelected;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
