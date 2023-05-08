package com.mobileclient.domain;

import java.io.Serializable;

public class RepairClass implements Serializable {
    /*维修类别id*/
    private int repairClassId;
    public int getRepairClassId() {
        return repairClassId;
    }
    public void setRepairClassId(int repairClassId) {
        this.repairClassId = repairClassId;
    }

    /*维修类别名称*/
    private String repairClassName;
    public String getRepairClassName() {
        return repairClassName;
    }
    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

}