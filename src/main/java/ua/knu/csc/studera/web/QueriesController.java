package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.knu.csc.studera.domain.lecturer.SimpleLecturer;
import ua.knu.csc.studera.domain.provider.SimpleProvider;
import ua.knu.csc.studera.domain.service.LecturerService;
import ua.knu.csc.studera.domain.service.ProviderService;
import ua.knu.csc.studera.domain.student.SimpleStudent;

import java.util.List;

@Controller
public class QueriesController {

    private final ProviderService providerService;
    private final LecturerService lecturerService;

    public QueriesController(ProviderService providerService, LecturerService lecturerService) {
        this.providerService = providerService;
        this.lecturerService = lecturerService;
    }

    @GetMapping("/queries")
    public String getMenu() {
        return "queries";
    }


    @GetMapping("/any-course/students/form")
    public String query1Form(Model model) {
        model.addAttribute("providers", providerService.findAll());
        return "queries/students-enrolled-any-course-of-provider";
    }

    @GetMapping("/any-course/students")
    public String getStudentsEnrolledAnyCourseOfProvider(
        @RequestParam("providerId") Integer providerId,
        Model model
    ) {
        List<SimpleStudent> students =
            providerService.findStudentsThatAppliedAnyCourseFromProvider(providerId);
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/courses/with-no-free-places/form")
    public String query2Form(Model model) {
        model.addAttribute("providers", providerService.findAll());
        return "queries/courses-with-no-free-places";
    }

    @GetMapping("/courses/with-no-free-places")
    public String getCoursesWithNoFreePlaces(
        @RequestParam("providerId") Integer providerId,
        Model model
    ) {
        model.addAttribute("courses", providerService.findCoursesWithNoFreePlaces(providerId));
        return "courses";
    }

    @GetMapping("/lectors/more-than-one-course/form")
    public String query3Form(Model model) {
        model.addAttribute("providers", providerService.findAll());
        return "queries/lecturers-with-more-than-one-course";
    }

    @GetMapping("/lectors/more-than-one-course")
    public String getLectorsAttachedToMoreThanXCourseOfProvider(
        @RequestParam("number") Integer number,
        @RequestParam("providerId") Integer providerId,
        Model model
    ) {
        List<SimpleLecturer> lecturers =
            lecturerService.getLecturersAttachedToMoreCoursesThanGiven(providerId, number);
        model.addAttribute("lecturers", lecturers);
        return "lecturers";
    }

    @GetMapping("/providers/on-whose-courses-lector-is-attached/form")
    public String queryForm4(Model model) {
        model.addAttribute("lecturers", lecturerService.findAll());
        return "queries/find-providers-on-whose-courses-lector-is-attached";
    }

    @GetMapping("/providers/on-whose-courses-lector-is-attached")
    public String getProvidersOnWhoseCoursesLecturerIsAttached(
        @RequestParam("lecturerId") Integer lecturerId,
        Model model
    ) {
        List<SimpleProvider> providers = providerService
            .findProvidersOnWhoseCoursesGivenLecturerWorks(lecturerId);
        model.addAttribute("providers", providers);
        return "providers";
    }

}
