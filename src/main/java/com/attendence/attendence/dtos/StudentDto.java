package com.attendence.attendence.dtos;

public class StudentDto {
    private long id;
    private String name;

    private String phoneNo;
    private String className;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getClassName() {
        return className;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
