package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.knu.csc.studera.domain.student.Student;
import ua.knu.csc.studera.domain.service.StudentService;
import ua.knu.csc.studera.web.dto.CreateStudentDTO;
import ua.knu.csc.studera.web.mapper.StudentMapper;

import javax.validation.Valid;
import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping("/students")
    public String getAll(
        @RequestParam(value = "courseId", required = false) Integer courseId,
        Model model
    ) {
        if (courseId == null) {
            model.addAttribute("students", studentService.findAll());
        } else {
            model.addAttribute("students", studentService.findAllByCourseId(courseId));
        }
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
        studentService.save(studentMapper.toEntity(studentDTO));
        return "redirect:/students";
    }

    @GetMapping("/courses/addStudent")
    public String getAddStudentForm(@RequestParam("courseId") Integer courseId, Model model) {
        List<Student> students = studentService.getStudentsThatHaveNotEnrolledCourseYet(courseId);
        model.addAttribute("students", students);
        return "forms/course-student-form";
    }

    @PostMapping("/courses/addStudent")
    public String addStudent(
        @RequestParam("courseId") Integer courseId,
        @RequestParam("studentId") Integer studentId
    ) {
        studentService.addToCourse(studentId, courseId);
        return "redirect:/students?courseId=".concat(String.valueOf(courseId));
    }

    @DeleteMapping("/courses/deleteStudent")
    public String deleteStudentFromCourse(
        @RequestParam("courseId") Integer courseId,
        @RequestParam("studentId") Integer studentId
    ) {
        studentService.deleteFromCourse(studentId, courseId);
        return "redirect:/students?courseId=".concat(String.valueOf(courseId));
    }

    @DeleteMapping("/students")
    public String deleteStudent(@RequestParam("id") Integer studentId) {
        studentService.delete(studentId);
        return "redirect:/students";
    }

}
