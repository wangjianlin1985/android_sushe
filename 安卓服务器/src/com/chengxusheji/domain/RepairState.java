package com.chengxusheji.domain;

import java.sql.Timestamp;
public class RepairState {
    /*״̬id*/
    private int repairStateId;
    public int getRepairStateId() {
        return repairStateId;
    }
    public void setRepairStateId(int repairStateId) {
        this.repairStateId = repairStateId;
    }

    /*״̬����*/
    private String repairStateName;
    public String getRepairStateName() {
        return repairStateName;
    }
    public void setRepairStateName(String repairStateName) {
        this.repairStateName = repairStateName;
    }

}