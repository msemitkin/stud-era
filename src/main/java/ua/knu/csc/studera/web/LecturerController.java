package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.knu.csc.studera.domain.Lecturer;
import ua.knu.csc.studera.repository.LecturerRepository;

import java.util.List;

@Controller
public class LecturerController {

    private final LecturerRepository lecturerRepository;

    public LecturerController(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    @GetMapping("/lecturers")
    public String getAll(Model model) {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        model.addAttribute("lecturers", lecturers);
        return "lecturers";
    }
}
