package edu.mizzou.Group14_iPERMITAPP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class REController {

    @GetMapping("/re/dashboard")
    public String dashboard() {
        return "re/dashboard";
    }

    @GetMapping("/re/submit")
    public String submitPermitPage(Model model) {
        return "re/submit-permit";
    }

    @GetMapping("/re/requests")
    public String myRequests(Model model) {
        return "re/my-requests";
    }
}