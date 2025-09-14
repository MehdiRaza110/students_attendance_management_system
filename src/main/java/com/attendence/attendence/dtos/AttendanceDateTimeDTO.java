package com.attendence.attendence.dtos;

public class AttendanceDateTimeDTO {
    private String date;
    private String time;

    public AttendanceDateTimeDTO() {
    }

    public AttendanceDateTimeDTO(String date, String time) {
        this.date = date;
        this.time = time;
    }

    // Getter for date
    public String getDate() {
        return date;
    }

    // Setter for date
    public void setDate(String date) {
        this.date = date;
    }

    // Getter for time
    public String getTime() {
        return time;
    }

    // Setter for time
    public void setTime(String time) {
        this.time = time;
    }
}
