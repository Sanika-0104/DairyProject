package com.role.implementation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.role.implementation.model.User;
import com.role.implementation.repository.UserRepository;
import com.role.implementation.service.DefaultUserService;

@Controller
@RequestMapping("/adminScreen")
public class AdminController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private DefaultUserService userService;

	@GetMapping
	public String displayDashboard(Model model) {
		String user = returnUsername();
		model.addAttribute("userDetails", user);
		return "adminScreen";
	}

	private String returnUsername() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
		User users = userRepository.findByEmail(user.getUsername());
		return users.getName();
	}

	@PostMapping("/updateRatePerLiter")
	public String updateRatePerLiter(@RequestParam("ratePerLiter") double ratePerLiter, Model model) {
		// Retrieve the currently logged-in user
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmail(userDetails.getUsername());

		// Update the ratePerLiter field
		user.setRatePerLiter(ratePerLiter);
		userRepository.save(user);

		// Add a success message to the model
		model.addAttribute("message", "Rate per liter updated successfully!");
		return "redirect:/adminScreen";
	}

	@GetMapping("/displayAvailableFarmers")
	public String displayAvailableMilkCollectors(Model model) {
		List<User> adminUsers = userService.getUsersByRole(2);
		for (User user : adminUsers) {
			System.out.println("Name: " + user.getName());
			System.out.println("Email: " + user.getEmail());
			System.out.println("Mobile No: " + user.getMobileNo());
			System.out.println("Location: " + user.getLocation());
			System.out.println("Milk Units Per Day: " + user.getMilkUnitsPerDay());
			System.out.println("--------------------");
		}
		model.addAttribute("adminUsers", adminUsers);
		return "displayAllFarmers";
	}
}
