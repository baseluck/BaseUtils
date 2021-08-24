package com.yhzc.schooldormitorymobile.ui.suppliesapply.apply;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/26 17:54
 * @描述:
 */
public class WzslModel {
    private String vcMaterialId;
    private String vcMaterialCode;
    private String vcMaterialName;
    private String vcSpecification;
    private String vcUnit;
    private int intNum;
    private int kcNum;

    public int getKcNum() {
        return kcNum;
    }

    public void setKcNum(int kcNum) {
        this.kcNum = kcNum;
    }

    public String getVcMaterialId() {
        return vcMaterialId;
    }

    public void setVcMaterialId(String vcMaterialId) {
        this.vcMaterialId = vcMaterialId;
    }

    public String getVcMaterialCode() {
        return vcMaterialCode;
    }

    public void setVcMaterialCode(String vcMaterialCode) {
        this.vcMaterialCode = vcMaterialCode;
    }

    public String getVcMaterialName() {
        return vcMaterialName;
    }

    public void setVcMaterialName(String vcMaterialName) {
        this.vcMaterialName = vcMaterialName;
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
}
