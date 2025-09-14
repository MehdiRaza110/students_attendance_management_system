package com.attendence.attendence.service;

import com.attendence.attendence.dtos.StudentAttendanceDTO;
import com.attendence.attendence.entity.Student;

public interface AttendenceService {
    Student markAttendence(long cardId);

    Student addStudent(Student student);

    StudentAttendanceDTO getStudentAttendanceDTO(Long id);
}
