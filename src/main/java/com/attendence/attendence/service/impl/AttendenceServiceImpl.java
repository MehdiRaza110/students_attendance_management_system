package com.attendence.attendence.service.impl;

import com.attendence.attendence.dtos.AttendanceDateTimeDTO;
import com.attendence.attendence.dtos.StudentAttendanceDTO;
import com.attendence.attendence.entity.Attendence;
import com.attendence.attendence.entity.Student;
import com.attendence.attendence.repository.AttendenceRepository;
import com.attendence.attendence.repository.StudentRepository;
import com.attendence.attendence.service.AttendenceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AttendenceServiceImpl implements AttendenceService {
    @Autowired
    private AttendenceRepository attendenceRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Override
    public Student markAttendence(long cardId) {
        Student student = studentRepository.findById(cardId).orElseThrow(() -> new EntityNotFoundException("Student not found with cardId: " + cardId));

        Attendence attendence = new Attendence();
        attendence.setStudent(student);
        attendence.setTimeStamp(LocalDateTime.now());

        attendenceRepository.save(attendence);

        return student;
    }

    @Override
    public Student addStudent(Student student) {
       return studentRepository.save(student);
    }

    @Override
    public StudentAttendanceDTO getStudentAttendanceDTO(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Group by date (LocalDate), pick one Attendence per date (e.g., earliest)
        Map<String, Attendence> earliestPerDate = student.getAttendenceList()
                .stream()
                .collect(Collectors.groupingBy(
                        att -> att.getTimeStamp().toLocalDate().format(dateFormatter),
                        Collectors.collectingAndThen(
                                Collectors.minBy((a1, a2) -> a1.getTimeStamp().compareTo(a2.getTimeStamp())),
                                optional -> optional.orElse(null)
                        )
                ));

        // Map to DTO list
        List<AttendanceDateTimeDTO> attendanceDTOs = earliestPerDate.values()
                .stream()
                .filter(att -> att != null)
                .map(att -> new AttendanceDateTimeDTO(
                        att.getTimeStamp().toLocalDate().format(dateFormatter),
                        att.getTimeStamp().toLocalTime().format(timeFormatter)
                ))
                .collect(Collectors.toList());

        return new StudentAttendanceDTO(student.getName(), attendanceDTOs);
    }
}
