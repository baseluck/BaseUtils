package com.yhzc.schooldormitorymobile.ui.mobileOffice.editor;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/2 16:31
 * @描述:
 */
public class EditorModel {
    private String id;
    private String fullName;
    private String enCode;
    private String appIcon;
    private boolean mNew;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public boolean isNew() {
        return mNew;
    }

    public void setNew(boolean aNew) {
        mNew = aNew;
    }
}
