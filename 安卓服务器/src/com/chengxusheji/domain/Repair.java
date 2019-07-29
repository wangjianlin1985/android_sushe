package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Repair {
    /*报修id*/
    private int repairId;
    public int getRepairId() {
        return repairId;
    }
    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    /*报修类别*/
    private RepairClass repaiClassObj;
    public RepairClass getRepaiClassObj() {
        return repaiClassObj;
    }
    public void setRepaiClassObj(RepairClass repaiClassObj) {
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
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
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
    private RepairState repairStateObj;
    public RepairState getRepairStateObj() {
        return repairStateObj;
    }
    public void setRepairStateObj(RepairState repairStateObj) {
        this.repairStateObj = repairStateObj;
    }

}