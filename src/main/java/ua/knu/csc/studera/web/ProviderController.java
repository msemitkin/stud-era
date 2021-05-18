package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.knu.csc.studera.domain.Provider;
import ua.knu.csc.studera.repository.ProviderRepository;
import ua.knu.csc.studera.web.dto.CreateProviderDTO;
import ua.knu.csc.studera.web.mapper.ProviderMapper;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProviderController {

    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper;

    public ProviderController(
        ProviderRepository providerRepository,
        ProviderMapper providerMapper
    ) {
        this.providerRepository = providerRepository;
        this.providerMapper = providerMapper;
    }


    @GetMapping("/providers")
    public String getAll(Model model) {
        List<Provider> providers = providerRepository.findAll();
        model.addAttribute("providers", providers);
        return "providers";
    }

    @GetMapping("/providers/save")
    public String getForm(Model model) {
        model.addAttribute("provider", new CreateProviderDTO());
        return "forms/provider-form";
    }

    @PostMapping("/providers/save")
    public String save(
        @Valid @ModelAttribute("provider") CreateProviderDTO providerDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "forms/provider-form";
        }
        providerRepository.save(providerMapper.toEntity(providerDTO));
        return "redirect:/providers";
    }
}
