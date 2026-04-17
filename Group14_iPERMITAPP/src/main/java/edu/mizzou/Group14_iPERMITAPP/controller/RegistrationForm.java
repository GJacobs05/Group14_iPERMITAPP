package edu.mizzou.Group14_iPERMITAPP.controller;
import edu.mizzou.Group14_iPERMITAPP.model.EnvironmentalPermit;
import edu.mizzou.Group14_iPERMITAPP.model.PermitRequest;
import edu.mizzou.Group14_iPERMITAPP.repository.EnvironmentalPermitRepository;
import edu.mizzou.Group14_iPERMITAPP.repository.PermitRequestRepository;
import edu.mizzou.Group14_iPERMITAPP.service.AcknowledgeEOService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Controller
public class RegistrationForm { // aka ministry's website

    @Autowired
    private AcknowledgeEOService acknowledgeEOService;
    
    @Autowired
    private EnvironmentalPermitRepository permitRepository;

    @GetMapping("/eo/dashboard")
    public String eoDashboard(HttpSession session) {

        String userType = (String) session.getAttribute("userType");

        // optional security check
        if (userType == null || !userType.equals("EO")) {
            return "redirect:/login";
        }

        return "eo/dashboard"; // this is your HTML file name (without .html)
    }

    @GetMapping("/eo/permits")
    public String viewPermits(HttpSession session, Model model) {

        // Optional: ensure only EO can access this page
        String userType = (String) session.getAttribute("userType");

        if (userType == null || !userType.equals("EO")) {
            return "redirect:/login";
        }

        // Get only PAID permits from your service
        List<PermitRequest> permits = acknowledgeEOService.getValidPermitRequests();
        Map<String, String> statusMap = acknowledgeEOService.getLatestStatusMap();

        model.addAttribute("permits", permits);
        model.addAttribute("statusMap", statusMap);
        return "eo/permits";
    }
    
    @GetMapping("/eo/permits/create")
    public String createPermitPage(HttpSession session) {
        String userType = (String) session.getAttribute("userType");
        if (userType == null || !userType.equals("EO")) return "redirect:/login";
        return "eo/create-permit";
    }

    @PostMapping("/eo/permits/create")
    public String savePermit(
            @RequestParam String permitID,
            @RequestParam String permitName,
            @RequestParam Double permitFee,
            @RequestParam String description,
            HttpSession session) {
                
        String userType = (String) session.getAttribute("userType");
        if (userType == null || !userType.equals("EO")) return "redirect:/login";

        EnvironmentalPermit newPermit = new EnvironmentalPermit();
        newPermit.setPermitID(permitID);
        newPermit.setPermitName(permitName);
        newPermit.setPermitFee(permitFee);
        newPermit.setDescription(description);

        permitRepository.save(newPermit);

        return "redirect:/eo/dashboard";
    }

    public void login(String uname, String passw){

    }

    public void validate(String uname, String passw){

    }
}
