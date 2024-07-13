package com.role.implementation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.role.implementation.DTO.AdminLoginDTO;
import com.role.implementation.DTO.UserLoginDTO;
import com.role.implementation.model.Admin;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin_o_login")
public class AdminOController {

    @GetMapping
    public String displayDashboard()
    {
        return "admin_o_login";
    }

    @PostMapping("/admin_1")
    public String loginUser(Model model) { // Change "user" to "admin"
        // Check static credentials
        //redirectAttributes.addFlashAttribute("user", model.getAttribute("user"));
        return "admin_o_dashboard";
    }
}