package edu.mizzou.Group14_iPERMITAPP.service;

// Domain models for RE (Resident Engineer) and EO (Environmental Officer)
import edu.mizzou.Group14_iPERMITAPP.model.EO;
import edu.mizzou.Group14_iPERMITAPP.model.RE;
import edu.mizzou.Group14_iPERMITAPP.model.RESite;

// Repository layer for database access
import edu.mizzou.Group14_iPERMITAPP.repository.EORepository;
import edu.mizzou.Group14_iPERMITAPP.repository.RERepository;

// Spring service annotation and dependency injection
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Java utilities
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

// Marks this class as a service responsible for authentication and registration logic
@Service
public class RegisterService {

	// Repository for RE users
	@Autowired
	private RERepository reRepository;

	// Repository for EO users
	@Autowired
	private EORepository eoRepository;

	// Handles login validation for both RE and EO users
	public boolean login(String email, String password) {

		// Attempt to find user as RE
		RE re = reRepository.findByEmail(email);

		// Attempt to find user as EO
		EO eo = eoRepository.findByEmail(email);

		// Debug logging for development/testing purposes
		System.out.println("Debug: Search email: " + email);
		System.out.println("Debug: Found RE: " + (re != null ? re.getEmail() : "null"));
		System.out.println("Debug: Found EO: " + (eo != null ? eo.getEmail() : "null"));

		// If user exists as RE, validate password
		if (re != null) {
			return re.getPassword().equals(password);

			// Otherwise, if user exists as EO, validate password
		} else if (eo != null) {
			return eo.getPassword().equals(password);
		}

		// If no user found, login fails
		return false;
	}

	// Regex pattern used to validate email format
	private static final Pattern EMAIL_PATTERN =
			Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

	// Handles new RE user registration
	public String register(String name,
	                       String orgName,
	                       String address,
	                       String email,
	                       String password,
	                       String siteAddress,
	                       String siteContactPerson) {

		// Validate that no required fields are empty
		if (isEmpty(name) || isEmpty(orgName) || isEmpty(address) ||
				isEmpty(email) || isEmpty(password) ||
				isEmpty(siteAddress) || isEmpty(siteContactPerson)) {
			return "EMPTY_FIELDS";
		}

		// Validate email format
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			return "INVALID_EMAIL";
		}

		// Ensure email is not already registered
		if (reRepository.findByEmail(email) != null) {
			return "EMAIL_EXISTS";
		}

		// Create new RE user object
		RE user = new RE();
		user.setContactPersonName(name);
		user.setOrganizationName(orgName);
		user.setOrganizationAddress(address);
		user.setEmail(email);
		user.setPassword(password);

		// Set account creation date
		user.setCreatedDate(new Date());

		// Create initial site associated with RE user
		RESite site = new RESite();
		site.setSiteAddress(siteAddress);
		site.setSiteContactPerson(siteContactPerson);
		site.setRe(user);

		// Add site to user's list of sites
		List<RESite> siteList = new ArrayList<>();
		siteList.add(site);
		user.setSites(siteList);

		// Save new user to database
		reRepository.save(user);

		// Return success status for controller handling
		return "SUCCESS";
	}

	// Helper method to check if a string is null or empty
	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}
}