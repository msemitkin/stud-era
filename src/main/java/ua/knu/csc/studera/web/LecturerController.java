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
import ua.knu.csc.studera.domain.Lecturer;
import ua.knu.csc.studera.repository.CourseRepository;
import ua.knu.csc.studera.repository.LecturerRepository;
import ua.knu.csc.studera.web.dto.CreateLecturerDTO;
import ua.knu.csc.studera.web.mapper.LecturerMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class LecturerController {

    private final LecturerRepository lecturerRepository;
    private final LecturerMapper lecturerMapper;
    private final CourseRepository courseRepository;

    public LecturerController(
        LecturerRepository lecturerRepository,
        LecturerMapper lecturerMapper,
        CourseRepository courseRepository) {
        this.lecturerRepository = lecturerRepository;
        this.lecturerMapper = lecturerMapper;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/lecturers")
    public String getAll(@RequestParam(value = "courseId", required = false) Integer courseId, Model model) {
        if (courseId == null) {
            List<Lecturer> lecturers = lecturerRepository.findAll();
            model.addAttribute("lecturers", lecturers);
        } else {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            Course course = courseOptional.orElseThrow(() ->
                new EntityNotFoundException("Course with given id does not exist"));
            model.addAttribute("lecturers", course.getLecturers());
        }
        return "lecturers";
    }

    @GetMapping("/lecturers/save")
    public String getForm(Model model) {
        model.addAttribute("lecturer", new CreateLecturerDTO());
        return "forms/lecturer-form";
    }

    @PostMapping("/lecturers/save")
    public String save(
        @Valid @ModelAttribute("lecturer") CreateLecturerDTO lecturerDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "forms/lecturer-form";
        }
        lecturerRepository.save(lecturerMapper.toEntity(lecturerDTO));
        return "redirect:/lecturers";
    }

    @GetMapping("/courses/addLecturer")
    public String getAddLecturerForm(@RequestParam("courseId") Integer courseId, Model model) {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Course with given id does not exist");
        }
        List<Lecturer> lecturers = lecturerRepository.findAll().stream()
            .filter(lecturer -> !isAttachedToCourse(lecturer, courseId))
            .collect(Collectors.toList());
        model.addAttribute("lecturers", lecturers);
        return "forms/course-lecturer-form";
    }

    private boolean isAttachedToCourse(Lecturer lecturer, Integer courseId) {
        return lecturer.getCourses().stream()
            .map(Course::getId)
            .anyMatch(id -> id.equals(courseId));
    }

    @PostMapping("/courses/addLecturer")
    public String addLecturer(
        @RequestParam("lecturerId") Integer lecturerId,
        @RequestParam("courseId") Integer courseId
    ) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow(() ->
            new EntityNotFoundException("Lecturer with given id does not exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
            new EntityNotFoundException("Course with given id does not exist"));
        if (lecturerRepository.lecturerAttachedToCourse(lecturerId, course)) {
            throw new IllegalArgumentException("Lecturer is already attached to this course");
        }
        lecturer.addCourse(course);
        lecturerRepository.save(lecturer);
        return "redirect:/lecturers?courseId=".concat(String.valueOf(courseId));
    }

    @DeleteMapping("/courses/deleteLecturer")
    public String deleteLecturerFromCourse(
        @RequestParam("courseId") Integer courseId,
        @RequestParam("lecturerId") Integer lecturerId
    ) {
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
            new EntityNotFoundException("Course with given id does not exist"));
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow(() ->
            new EntityNotFoundException("Lecturer with given id does not exist"));
        lecturer.removeCourse(course);
        lecturerRepository.save(lecturer);
        return "redirect:/lecturers?courseId=".concat(String.valueOf(courseId));
    }
}
