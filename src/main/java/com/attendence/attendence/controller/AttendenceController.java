package com.attendence.attendence.controller;

import com.attendence.attendence.dtos.StudentAttendanceDTO;
import com.attendence.attendence.entity.Student;
import com.attendence.attendence.service.AttendenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendence")
public class AttendenceController {

    @Autowired
    private AttendenceService attendenceService;

    @GetMapping("/{cardId}")
    public ResponseEntity<?> markAttendence(@PathVariable long cardId){
       Student std= attendenceService.markAttendence(cardId);
        return new ResponseEntity<>(std, HttpStatus.CREATED);
    }

    @GetMapping("student/{id}")
    public ResponseEntity<StudentAttendanceDTO> getStudentAttendanceDTO(@PathVariable Long id) {
        StudentAttendanceDTO dto = attendenceService.getStudentAttendanceDTO(id);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        Student std=attendenceService.addStudent(student);
        return new ResponseEntity<>(std,HttpStatus.CREATED);
    }


}
