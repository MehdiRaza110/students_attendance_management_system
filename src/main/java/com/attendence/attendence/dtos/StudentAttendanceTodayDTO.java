package com.attendence.attendence.dtos;

import java.util.List;

public class StudentAttendanceTodayDTO {
    private String studentName;
    private AttendanceDateTimeDTO  attendances;

    public StudentAttendanceTodayDTO() {
    }

    public StudentAttendanceTodayDTO(String studentName, AttendanceDateTimeDTO attendances) {
        this.studentName = studentName;
        this.attendances = attendances;
    }

    // getters and setters
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public AttendanceDateTimeDTO getAttendances() {
        return attendances;
    }
    public void setAttendances(AttendanceDateTimeDTO attendances) {
        this.attendances = attendances;
    }
}
