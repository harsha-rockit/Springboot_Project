package com.example.demo6;
import java.util.List;
import javax.validation.Valid;

import com.example.demo6.beans.Student;
import com.example.demo6.beans.StudentRepository;
import com.example.demo6.exceptions.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    Student std;
    @GetMapping("/students")
    public List<Student> findAllStudentDetails(){
        return studentRepository.findAll();
    }
    @GetMapping("/student/{id}")
    public Student findOneStudentDetails(@PathVariable int id) {
        return studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException());
    }
    @PostMapping("/student/add")
    public Student saveStudentDetails(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }
    @PostMapping("/student/updateOrSave")
    public Student updateStudentData(@Valid @RequestBody Student student) {
        if(student.getStudent_id()==std.getStudent_id()) {
            std.setStudent_name(student.getStudent_name());
            std.setStudent_age(student.getStudent_age());
            std=studentRepository.save(std);
            return std;
        }else {
            student=studentRepository.save(student);
            return student;
        }
    }
    @DeleteMapping("/student/delete/{id}")
    public void deleteStudent(@PathVariable int id) {
        if(std.getStudent_id()==id) {
            studentRepository.deleteById(id);
        }
        else {
            throw new StudentNotFoundException();
        }
    }
}
