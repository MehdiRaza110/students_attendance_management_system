package com.attendence.attendence.service.impl;

import com.attendence.attendence.dtos.AttendanceDateTimeDTO;
import com.attendence.attendence.dtos.StudentAttendanceDTO;
import com.attendence.attendence.dtos.StudentAttendanceTodayDTO;
import com.attendence.attendence.dtos.StudentDto;
import com.attendence.attendence.entity.Attendence;
import com.attendence.attendence.entity.Student;
import com.attendence.attendence.repository.AttendenceRepository;
import com.attendence.attendence.repository.StudentRepository;
import com.attendence.attendence.service.AttendenceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendenceServiceImpl implements AttendenceService {
    @Autowired
    private AttendenceRepository attendenceRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Override
    public StudentAttendanceTodayDTO markAttendence(long cardId) {
        Student student = studentRepository.findById(cardId).orElseThrow(() -> new EntityNotFoundException("Student not found with cardId: " + cardId));

        Attendence attendence = new Attendence();
        attendence.setStudent(student);
        attendence.setTimeStamp(LocalDateTime.now());

        attendenceRepository.save(attendence);
        StudentAttendanceTodayDTO studentAttendanceDTO = getStudentAttendanceToday(cardId);

        return studentAttendanceDTO;
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

    @Override
    public StudentAttendanceTodayDTO getStudentAttendanceToday(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));

        LocalDate today = LocalDate.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Filter attendances for today
        Optional<Attendence> todayAttendance = student.getAttendenceList()
                .stream()
                .filter(att -> att.getTimeStamp().toLocalDate().equals(today))
                .min(Comparator.comparing(Attendence::getTimeStamp)); // Get earliest today

        // Build response
        if (todayAttendance.isPresent()) {
            Attendence att = todayAttendance.get();
            AttendanceDateTimeDTO attendanceDTO = new AttendanceDateTimeDTO(
                    att.getTimeStamp().toLocalDate().format(dateFormatter),
                    att.getTimeStamp().toLocalTime().format(timeFormatter)
            );
            return new StudentAttendanceTodayDTO(student.getName(), attendanceDTO);
        } else {
            // If no attendance found today, return with null or empty attendance
            return new StudentAttendanceTodayDTO(student.getName(), null);
        }
    }

    @Override
    public Set<Student> getAllStudentHare() {
        studentRepository.findAll();
        return null;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> all = studentRepository.findAll();
        List<StudentDto> studentDtos=new ArrayList<>();
        for (Student student:all) {
         StudentDto studentDto=new StudentDto();
         studentDto.setId(student.getId());
         studentDto.setName(student.getName());
         studentDto.setClassName(student.getClassName());
         studentDto.setPhoneNo(student.getPhoneNo());
         studentDtos.add(studentDto);
        }
        return studentDtos;
    }
}
