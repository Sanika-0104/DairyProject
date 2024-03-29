package com.role.implementation.controller;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.role.implementation.model.Role;
import com.role.implementation.model.User;
import com.role.implementation.repository.UserRepository;
import com.role.implementation.service.DefaultUserService;

@Controller
@RequestMapping("/displayAllMilkCollectors")
public class FarmerController
{
    @Autowired
    DefaultUserService userService;
    
    @Autowired
	private UserRepository userRepository;


//    @GetMapping
//    public String display()
//    {
//        return "displayAllMilkCollectors";
//    }

    @GetMapping
    public String displayAvailableMilkCollectors(Model model)
    {
        List<User> adminUsers = userService.getUsersByRole(1);
        model.addAttribute("adminUsers", adminUsers);
        return "displayAllMilkCollectors";
    }
    
}
