package com.role.implementation.controller;

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

import com.role.implementation.DTO.FarmerDetailsDTO;
import com.role.implementation.model.User;
import com.role.implementation.repository.UserRepository;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public String displayDashboard(Model model) {
		String user = returnUsername();
		model.addAttribute("userDetails", user);
		return "dashboard";
	}

	private String returnUsername() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
		User users = userRepository.findByEmail(user.getUsername());
		return users.getName();
	}

	@PostMapping("/updateMilkProduction")
	public String updateMilkProduction(@RequestParam("milkUnitsPerDay") double milkUnitsPerDay) {
		String userEmail = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUsername();
		User user = userRepository.findByEmail(userEmail);
		user.setMilkUnitsPerDay(milkUnitsPerDay);
		userRepository.save(user);
		return "redirect:/dashboard";
	}
}
