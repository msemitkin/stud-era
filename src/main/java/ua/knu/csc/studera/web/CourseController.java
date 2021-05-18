package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.knu.csc.studera.domain.Course;
import ua.knu.csc.studera.domain.Provider;
import ua.knu.csc.studera.repository.CourseRepository;
import ua.knu.csc.studera.repository.ProviderRepository;
import ua.knu.csc.studera.web.dto.CreateCourseDTO;
import ua.knu.csc.studera.web.mapper.CourseMapper;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CourseController {

    private final ProviderRepository providerRepository;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseController(
        ProviderRepository providerRepository,
        CourseRepository courseRepository,
        CourseMapper courseMapper
    ) {
        this.providerRepository = providerRepository;
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @GetMapping("courses")
    public String getAll(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses";
    }


    @GetMapping("/courses/save")
    public String getForm(Model model) {
        model.addAttribute("course", new CreateCourseDTO());
        model.addAttribute("providers", providerRepository.findAll());
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
        Provider provider = providerRepository.findById(providerId).orElseThrow();
        provider.addCourse(course);
        courseRepository.save(course);
        return "redirect:/courses";
    }
}
