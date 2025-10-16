package com.attendence.attendence.controller;

import com.attendence.attendence.dtos.StudentAttendanceDTO;
import com.attendence.attendence.dtos.StudentAttendanceTodayDTO;
import com.attendence.attendence.dtos.StudentDto;
import com.attendence.attendence.entity.Student;
import com.attendence.attendence.service.AttendenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/students")
public class AttendenceController {

    @Autowired
    private AttendenceService attendenceService;

    @GetMapping("attendance/{cardId}")
    public ResponseEntity<?> markAttendence(@PathVariable long cardId){
       StudentAttendanceTodayDTO std= attendenceService.markAttendence(cardId);
        return new ResponseEntity<>(std, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentAttendanceDTO> getStudentAttendanceDTO(@PathVariable Long id) {
        StudentAttendanceDTO dto = attendenceService.getStudentAttendanceDTO(id);
        return ResponseEntity.ok(dto);
    }
    @GetMapping()
    public ResponseEntity<List<StudentDto>> getAllStudent() {
        List<StudentDto> students= attendenceService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        Student std=attendenceService.addStudent(student);
        return new ResponseEntity<>(std,HttpStatus.CREATED);
    }
//    @GetMapping("/student")
//    public ResponseEntity<?> getAllStudentAttendence(){
//        Set<Student> std=attendenceService.getAllStudentHare();
//        return new ResponseEntity<>(std,HttpStatus.OK);
//    }


}
