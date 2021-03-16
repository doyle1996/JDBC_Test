package com.jdbc.bean;



public class Student {
    public int FlowId;
    private int Type;
    private String IdCard;
    private String ExamCard;
    private String StudentName;
    private String Location;
    private int Grade;

    public Student() {
    }

    public int getFlowId() {
        return FlowId;
    }

    public void setFlowId(int flowId) {
        FlowId = flowId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public String getExamCard() {
        return ExamCard;
    }

    public void setExamCard(String examCard) {
        ExamCard = examCard;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    public Student(int flowId, int type, String idCard, String examCard, String studentName, String location, int grade) {
        FlowId = flowId;
        Type = type;
        IdCard = idCard;
        ExamCard = examCard;
        StudentName = studentName;
        Location = location;
        Grade = grade;
    }

    @Override
    public String toString() {
        System.out.println("======查询结果======");
        return info();
    }

    private String info() {
        return "流水号:  " + FlowId + "\n四级/六级:   " + Type + "\n身份证号" + IdCard + "\n准考证号:" + ExamCard + "\n学生姓名：" + StudentName
                + "\n城市:" + Location + "\n成绩:" + Grade;
    }
}

