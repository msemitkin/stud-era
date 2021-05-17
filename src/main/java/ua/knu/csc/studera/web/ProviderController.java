package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.knu.csc.studera.domain.Provider;
import ua.knu.csc.studera.repository.ProviderRepository;

import java.util.List;

@Controller
public class ProviderController {

    private final ProviderRepository providerRepository;

    public ProviderController(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }


    @GetMapping("/providers")
    public String getAll(Model model) {
        List<Provider> providers = providerRepository.findAll();
        model.addAttribute("providers", providers);
        return "providers";
    }
}
