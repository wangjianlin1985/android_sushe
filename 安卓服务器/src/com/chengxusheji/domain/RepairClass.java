package com.chengxusheji.domain;

import java.sql.Timestamp;
public class RepairClass {
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