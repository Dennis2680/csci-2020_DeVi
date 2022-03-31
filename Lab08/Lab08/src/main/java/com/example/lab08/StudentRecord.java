package com.example.lab08;

public class StudentRecord {
    private String studentID;
    private float midTerm;
    private float assignments;
    private float finalExam;


    private double finalMark;
    private String letterGrade;

    public StudentRecord(String studentID, float midTerm, float assignments, float finalExam) {
        this.studentID = studentID;
        this.midTerm = midTerm;
        this.assignments = assignments;
        this.finalExam = finalExam;
        this.finalMark = this.getFinalMark();
        this.letterGrade = this.getLetterGrade();
    }

    public String getStudentID() {
        return studentID;
    }

    public float getMidTerm() {
        return midTerm;
    }

    public float getAssignments() {
        return assignments;
    }

    public float getFinalExam() {
        return finalExam;
    }

    public double getFinalMark() {
        finalMark = (this.assignments * 0.2) + (this.midTerm * 0.3) + (this.finalMark * 0.5);
        return finalMark;
    }

    public String getLetterGrade() {
        if (this.finalMark >= 0 && this.finalMark <= 49) {
            letterGrade = "F";
        } else if (this.finalMark >= 50 && this.finalMark <= 59) {
            letterGrade = "D";
        } else if (this.finalMark >= 60 && this.finalMark <= 69) {
            letterGrade = "C";
        } else if (this.finalMark >= 70 && this.finalMark <= 79) {
            letterGrade = "B";
        } else if (this.finalMark >= 80 && this.finalMark <= 100) {
            letterGrade = "A";
        }

        return letterGrade;
    }

}
