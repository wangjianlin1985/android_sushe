package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Repair {
    /*����id*/
    private int repairId;
    public int getRepairId() {
        return repairId;
    }
    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    /*�������*/
    private RepairClass repaiClassObj;
    public RepairClass getRepaiClassObj() {
        return repaiClassObj;
    }
    public void setRepaiClassObj(RepairClass repaiClassObj) {
        this.repaiClassObj = repaiClassObj;
    }

    /*���ϼ���*/
    private String repaitTitle;
    public String getRepaitTitle() {
        return repaitTitle;
    }
    public void setRepaitTitle(String repaitTitle) {
        this.repaitTitle = repaitTitle;
    }

    /*��������*/
    private String repairContent;
    public String getRepairContent() {
        return repairContent;
    }
    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    /*�ϱ�ѧ��*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*������*/
    private String handleResult;
    public String getHandleResult() {
        return handleResult;
    }
    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    /*ά��״̬*/
    private RepairState repairStateObj;
    public RepairState getRepairStateObj() {
        return repairStateObj;
    }
    public void setRepairStateObj(RepairState repairStateObj) {
        this.repairStateObj = repairStateObj;
    }

}