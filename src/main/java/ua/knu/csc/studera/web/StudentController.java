package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.knu.csc.studera.domain.Student;
import ua.knu.csc.studera.repository.StudentRepository;
import ua.knu.csc.studera.web.dto.CreateStudentDTO;
import ua.knu.csc.studera.web.mapper.StudentMapper;

import javax.validation.Valid;
import java.util.List;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentController(
        StudentRepository studentRepository,
        StudentMapper studentMapper
    ) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @GetMapping("/students")
    public String getAll(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/students/save")
    public String getForm(Model model) {
        model.addAttribute("student", new CreateStudentDTO());
        return "forms/student-form";
    }

    @PostMapping("/students/save")
    public String save(
        @Valid @ModelAttribute("student") CreateStudentDTO studentDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "forms/student-form";
        }
        studentRepository.save(studentMapper.toEntity(studentDTO));
        return "redirect:/students";
    }

}
