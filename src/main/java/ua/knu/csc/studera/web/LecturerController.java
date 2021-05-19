package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.knu.csc.studera.domain.service.LecturerService;
import ua.knu.csc.studera.web.dto.CreateLecturerDTO;
import ua.knu.csc.studera.web.mapper.LecturerMapper;

import javax.validation.Valid;

@Controller
public class LecturerController {

    private final LecturerService lecturerService;
    private final LecturerMapper lecturerMapper;

    public LecturerController(LecturerService lecturerService, LecturerMapper lecturerMapper) {
        this.lecturerService = lecturerService;
        this.lecturerMapper = lecturerMapper;
    }

    @GetMapping("/lecturers")
    public String getAll(@RequestParam(value = "courseId", required = false) Integer courseId, Model model) {
        if (courseId == null) {
            model.addAttribute("lecturers", lecturerService.findAll());
        } else {
            model.addAttribute("lecturers", lecturerService.findAllByCourse(courseId));
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
        lecturerService.save(lecturerMapper.toEntity(lecturerDTO));
        return "redirect:/lecturers";
    }

    @GetMapping("/courses/addLecturer")
    public String getAddLecturerForm(@RequestParam("courseId") Integer courseId, Model model) {
        model.addAttribute("lecturers", lecturerService.findAllNotAttachedToCourse(courseId));
        return "forms/course-lecturer-form";
    }

    @PostMapping("/courses/addLecturer")
    public String addLecturer(
        @RequestParam("lecturerId") Integer lecturerId,
        @RequestParam("courseId") Integer courseId
    ) {
        lecturerService.addToCourse(lecturerId, courseId);
        return "redirect:/lecturers?courseId=".concat(String.valueOf(courseId));
    }

    @DeleteMapping("/courses/deleteLecturer")
    public String deleteLecturerFromCourse(
        @RequestParam("courseId") Integer courseId,
        @RequestParam("lecturerId") Integer lecturerId
    ) {
        lecturerService.deleteFromCourse(lecturerId, courseId);
        return "redirect:/lecturers?courseId=".concat(String.valueOf(courseId));
    }

    @DeleteMapping("/lecturers")
    public String deleteLecturer(@RequestParam("id") Integer lecturerId) {
        lecturerService.delete(lecturerId);
        return "redirect:/lecturers";
    }
}
