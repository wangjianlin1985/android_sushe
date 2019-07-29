package com.mobileclient.domain;

import java.io.Serializable;

public class RepairState implements Serializable {
    /*状态id*/
    private int repairStateId;
    public int getRepairStateId() {
        return repairStateId;
    }
    public void setRepairStateId(int repairStateId) {
        this.repairStateId = repairStateId;
    }

    /*状态名称*/
    private String repairStateName;
    public String getRepairStateName() {
        return repairStateName;
    }
    public void setRepairStateName(String repairStateName) {
        this.repairStateName = repairStateName;
    }

}