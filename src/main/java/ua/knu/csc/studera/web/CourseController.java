package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.knu.csc.studera.domain.course.Course;
import ua.knu.csc.studera.domain.service.CourseService;
import ua.knu.csc.studera.domain.service.ProviderService;
import ua.knu.csc.studera.web.dto.CreateCourseDTO;
import ua.knu.csc.studera.web.mapper.CourseMapper;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CourseController {

    private final CourseService courseService;
    private final ProviderService providerService;
    private final CourseMapper courseMapper;

    public CourseController(
        CourseService courseService,
        ProviderService providerService,
        CourseMapper courseMapper
    ) {
        this.courseService = courseService;
        this.providerService = providerService;
        this.courseMapper = courseMapper;
    }

    @GetMapping("/courses")
    public String getAll(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "courses";
    }


    @GetMapping("/courses/save")
    public String getForm(Model model) {
        model.addAttribute("course", new CreateCourseDTO());
        model.addAttribute("providers", providerService.findAll());
        return "forms/course-form";
    }

    @PostMapping("/courses/save")
    public String save(
        @Valid @ModelAttribute("course") CreateCourseDTO createCourseDTO,
        BindingResult bindingResult,
        @RequestParam("providerId") Integer providerId
    ) {
        if (bindingResult.hasErrors()) {
            return "forms/course-form";
        }
        Course course = courseMapper.toEntity(createCourseDTO);
        providerService.addCourse(providerId, course);
        return "redirect:/courses";
    }
}
