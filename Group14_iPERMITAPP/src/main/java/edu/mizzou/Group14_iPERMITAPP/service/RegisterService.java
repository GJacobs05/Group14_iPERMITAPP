package edu.mizzou.Group14_iPERMITAPP.service;

import edu.mizzou.Group14_iPERMITAPP.model.RE;
import edu.mizzou.Group14_iPERMITAPP.repository.RERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class RegisterService {

	@Autowired
	private RERepository reRepository;

	public boolean login(String email, String password) {
		RE user = reRepository.findByEmail(email);

		if (user == null)
			return false;

		return user.getPassword().equals(password);
	}

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

	public String register(String name, String orgName, String address, String email, String password) {

		if (isEmpty(name) || isEmpty(orgName) || isEmpty(address) || isEmpty(email) || isEmpty(password)) {
			return "EMPTY_FIELDS";
		}

		if (!EMAIL_PATTERN.matcher(email).matches()) {
			return "INVALID_EMAIL";
		}

		if (reRepository.findByEmail(email) != null) {
			return "EMAIL_EXISTS";
		}

		RE user = new RE();
		user.setContactPersonName(name);
		user.setOrganizationName(orgName);
		user.setOrganizationAddress(address);
		user.setEmail(email);
		user.setPassword(password);
		user.setCreatedDate(new Date());

		reRepository.save(user);

		return "SUCCESS";
	}

	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}
}