package ua.knu.csc.studera.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QueriesController {

    @GetMapping("/queries")
    public String getMenu() {
        return "queries";
    }
}