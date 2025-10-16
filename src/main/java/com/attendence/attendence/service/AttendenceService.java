package com.attendence.attendence.service;

import com.attendence.attendence.dtos.StudentAttendanceDTO;
import com.attendence.attendence.dtos.StudentAttendanceTodayDTO;
import com.attendence.attendence.dtos.StudentDto;
import com.attendence.attendence.entity.Student;

import java.util.List;
import java.util.Set;

public interface AttendenceService {
    StudentAttendanceTodayDTO markAttendence(long cardId);

    Student addStudent(Student student);

    StudentAttendanceDTO getStudentAttendanceDTO(Long id);

    StudentAttendanceTodayDTO getStudentAttendanceToday(Long id);

    Set<Student> getAllStudentHare();

    public List<StudentDto> getAllStudents() ;
}
