package com.role.implementation.controller;

import com.role.implementation.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.role.implementation.model.User;
import com.role.implementation.repository.UserRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DefaultUserService userService;
	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public String displayDashboard(Model model, RedirectAttributes redirectAttributes) {
		// Retrieve currently logged-in user details
		User userDetails = returnUsername();
		model.addAttribute("userDetails", userDetails);

		// Retrieve flash attributes if available and add to model
		if (redirectAttributes.containsAttribute("message")) {
			String message = (String) redirectAttributes.getAttribute("message");
			model.addAttribute("message", message);
		}
		if (redirectAttributes.containsAttribute("selectedMilkCollector")) {
			User selectedMilkCollector = (User) redirectAttributes.getAttribute("selectedMilkCollector");
			model.addAttribute("selectedMilkCollector", selectedMilkCollector);
		}
		if (redirectAttributes.containsAttribute("successMessage")) {
			String successMessage = (String) redirectAttributes.getAttribute("successMessage");
			model.addAttribute("successMessage", successMessage);
		}

		return "dashboard";
	}

//	@GetMapping
//	public String displayDashboard(Model model) {
//		User userDetails = returnUsername();
//		model.addAttribute("userDetails", userDetails);
//		return "dashboard";
//	}

	private User returnUsername() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
		User user = userRepository.findByEmail(userDetails.getUsername());
		if (user != null) {
			return user;
		} else {
			return null;
			// Alternatively, you can throw an exception or handle it based on your application logic.
		}
	}



	@PostMapping("/updateMilkProduction")
	public String updateMilkProduction(
			@RequestParam("milkProduction") String milkProduction,
			@RequestParam("userID") int userId,
			RedirectAttributes redirectAttributes) {

		// Log incoming parameters
		System.out.println("Received Milk Production: " + milkProduction);
		System.out.println("Received User ID: " + userId);

		// Retrieve user from database based on userId
		Optional<User> userOptional = userRepository.findById(userId);

		// Check if user is present
		if (!userOptional.isPresent()) {
			// Handle the situation when user is not found
			System.out.println("User not found with ID: " + userId);
			return "redirect:/error";
		}

		User user = userOptional.get();

		// Log user object
		System.out.println("User Object: " + user);

		try {
			// Update the milkUnitsPerDay attribute of the User object with the value entered by the user
			user.setMilkUnitsPerDay(Double.parseDouble(milkProduction));
		} catch (NumberFormatException e) {
			// Handle the situation when milkProduction cannot be parsed as a double
			System.out.println("Invalid Milk Production value: " + milkProduction);
			return "redirect:/error";
		}

		// Save the updated User object to the database
		userRepository.save(user);

		// Log success message
		System.out.println("Milk Production updated successfully for user ID: " + userId);

		// Add success message to redirect attributes
		redirectAttributes.addFlashAttribute("successMessage", "Milk Production updated successfully!");

		// Redirect to the dashboard
		return "redirect:/dashboard";
	}


}
