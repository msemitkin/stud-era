package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.knu.csc.studera.domain.Course;
import ua.knu.csc.studera.domain.Student;
import ua.knu.csc.studera.repository.CourseRepository;
import ua.knu.csc.studera.repository.StudentRepository;
import ua.knu.csc.studera.web.dto.CreateStudentDTO;
import ua.knu.csc.studera.web.mapper.StudentMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseRepository courseRepository;

    public StudentController(
        StudentRepository studentRepository,
        StudentMapper studentMapper,
        CourseRepository courseRepository
    ) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/students")
    public String getAll(
        @RequestParam(value = "courseId", required = false) Integer courseId,
        Model model
    ) {
        if (courseId == null) {
            List<Student> students = studentRepository.findAll();
            model.addAttribute("students", students);
        } else {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (courseOptional.isPresent()) {
                model.addAttribute("students", courseOptional.get().getStudents());
            } else {
                throw new EntityNotFoundException("Course with given id does not exist");
            }
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
        studentRepository.save(studentMapper.toEntity(studentDTO));
        return "redirect:/students";
    }

    @GetMapping("/courses/addStudent")
    public String getAddStudentForm(@RequestParam("courseId") Integer courseId, Model model) {
        List<Student> students = studentRepository.findAll().stream()
            .filter(student -> !alreadyApplied(student, courseId))
            .collect(Collectors.toList());
        model.addAttribute("students", students);
        return "forms/course-student-form";
    }

    private boolean alreadyApplied(Student student, Integer courseId) {
        return student.getCourses().stream()
            .map(Course::getId)
            .anyMatch(id -> id.equals(courseId));
    }

    @PostMapping("/courses/addStudent")
    public String addStudent(
        @RequestParam("courseId") Integer courseId,
        @RequestParam("studentId") Integer studentId
    ) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (studentOptional.isPresent() && courseOptional.isPresent()) {
            Course course = courseOptional.get();
            if (studentRepository.studentAppliedCourse(studentId, course)) {
                throw new IllegalArgumentException("Student already applied this course");
            }
            if (course.getStudents().size() >= course.getStudentsLimit()) {
                throw new StudentsLimitExceeded("Maximum students number limit have been reached");
            }
            Student student = studentOptional.get();
            student.addCourse(course);
            studentRepository.save(student);
        } else {
            throw new EntityNotFoundException("Course or student with given id does not exist");
        }
        return "redirect:/students?courseId=".concat(String.valueOf(courseId));
    }

    @DeleteMapping("/courses/deleteStudent")
    public String deleteStudent(@RequestParam("courseId") Integer courseId, @RequestParam("studentId") Integer studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (courseOptional.isPresent() && studentOptional.isPresent()) {
            Course course = courseOptional.orElseThrow();
            Student student = studentOptional.orElseThrow();
            student.removeCourse(course);
            studentRepository.save(student);
        } else {
            throw new EntityNotFoundException("Student or course with given id does not exist");
        }
        return "redirect:/students?courseId=".concat(String.valueOf(courseId));
    }

}
