package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.knu.csc.studera.domain.Lecturer;
import ua.knu.csc.studera.repository.LecturerRepository;
import ua.knu.csc.studera.web.dto.CreateLecturerDTO;
import ua.knu.csc.studera.web.mapper.LecturerMapper;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LecturerController {

    private final LecturerRepository lecturerRepository;
    private final LecturerMapper lecturerMapper;

    public LecturerController(
        LecturerRepository lecturerRepository,
        LecturerMapper lecturerMapper
    ) {
        this.lecturerRepository = lecturerRepository;
        this.lecturerMapper = lecturerMapper;
    }

    @GetMapping("/lecturers")
    public String getAll(Model model) {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        model.addAttribute("lecturers", lecturers);
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
}
