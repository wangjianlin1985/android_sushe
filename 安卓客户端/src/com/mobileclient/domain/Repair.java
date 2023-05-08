package com.mobileclient.domain;

import java.io.Serializable;

public class Repair implements Serializable {
    /*报修id*/
    private int repairId;
    public int getRepairId() {
        return repairId;
    }
    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    /*报修类别*/
    private int repaiClassObj;
    public int getRepaiClassObj() {
        return repaiClassObj;
    }
    public void setRepaiClassObj(int repaiClassObj) {
        this.repaiClassObj = repaiClassObj;
    }

    /*故障简述*/
    private String repaitTitle;
    public String getRepaitTitle() {
        return repaitTitle;
    }
    public void setRepaitTitle(String repaitTitle) {
        this.repaitTitle = repaitTitle;
    }

    /*故障详述*/
    private String repairContent;
    public String getRepairContent() {
        return repairContent;
    }
    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    /*上报学生*/
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
        this.studentObj = studentObj;
    }

    /*处理结果*/
    private String handleResult;
    public String getHandleResult() {
        return handleResult;
    }
    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    /*维修状态*/
    private int repairStateObj;
    public int getRepairStateObj() {
        return repairStateObj;
    }
    public void setRepairStateObj(int repairStateObj) {
        this.repairStateObj = repairStateObj;
    }

}