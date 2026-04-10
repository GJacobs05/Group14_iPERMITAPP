package edu.mizzou.Group14_iPERMITAPP.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.mizzou.Group14_iPERMITAPP.model.RE;
import edu.mizzou.Group14_iPERMITAPP.repository.RERepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class REController {
	@Autowired
	private RERepository reRepository;

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

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

	@GetMapping("/re/account")
	public String accountPage(HttpSession session, Model model) {

		String email = (String) session.getAttribute("userEmail");

		if (email == null) {
			return "redirect:/login";
		}

		RE user = reRepository.findByEmail(email);

		if (user == null) {
			return "redirect:/login?error=nouser";
		}

		model.addAttribute("user", user);

		return "re/account";
	}

	@PostMapping("/re/account/update")
	public String updateAccount(@RequestParam String contactPersonName, @RequestParam String organizationName,
			@RequestParam String organizationAddress, @RequestParam String email,
			@RequestParam(required = false) String currentPassword, @RequestParam(required = false) String newPassword,
			HttpSession session) {

		String sessionEmail = (String) session.getAttribute("userEmail");
		RE user = reRepository.findByEmail(sessionEmail);

		if (!EMAIL_PATTERN.matcher(email).matches()) {
			return "redirect:/re/account?error=email";
		}

		RE existing = reRepository.findByEmail(email);
		if (existing != null && !email.equals(sessionEmail)) {
			return "redirect:/re/account?error=exists";
		}

		user.setContactPersonName(contactPersonName);
		user.setOrganizationName(organizationName);
		user.setOrganizationAddress(organizationAddress);
		user.setEmail(email);

		if (newPassword != null && !newPassword.trim().isEmpty()) {

			if (currentPassword == null || !user.getPassword().equals(currentPassword)) {
				return "redirect:/re/account?error=password";
			}

			user.setPassword(newPassword);
		}

		reRepository.save(user);

		session.setAttribute("userEmail", email);

		return "redirect:/re/account?success=true";
	}
}