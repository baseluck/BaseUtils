package com.yhzc.schooldormitorymobile.ui.discipline.details;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/30 15:45
 * @描述:
 */
public class ImageModel {
    private String path;
    private int type;
    private boolean canChange;

    public ImageModel() {
    }

    public ImageModel(String path, int type, boolean canChange) {
        this.path = path;
        this.type = type;
        this.canChange = canChange;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCanChange() {
        return canChange;
    }

    public void setCanChange(boolean canChange) {
        this.canChange = canChange;
    }
}
