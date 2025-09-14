package com.attendence.attendence.dtos;

import java.util.List;

public class StudentAttendanceDTO {
    private String studentName;
    private List<AttendanceDateTimeDTO> attendances;

    public StudentAttendanceDTO() {
    }

    public StudentAttendanceDTO(String studentName, List<AttendanceDateTimeDTO> attendances) {
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

    public List<AttendanceDateTimeDTO> getAttendances() {
        return attendances;
    }
    public void setAttendances(List<AttendanceDateTimeDTO> attendances) {
        this.attendances = attendances;
    }
}
