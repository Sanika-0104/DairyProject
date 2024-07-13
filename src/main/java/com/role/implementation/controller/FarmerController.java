package com.role.implementation.controller;


import com.role.implementation.DTO.UserLoginDTO;
import com.role.implementation.DTO.UserRegisteredDTO;
import com.role.implementation.model.User;
import com.role.implementation.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/displayAllMilkCollectors")
public class FarmerController
{
    @Autowired
    DefaultUserService userService;

//    @GetMapping
//    public String display()
//    {
//        return "displayAllMilkCollectors";
//    }
@GetMapping("/currentMilkCollector")
public String currentSelectedMilkCollectors(Model model,@RequestParam("farmerId") int farmerId,@RequestParam("farmerName") String farmerName) throws Exception {
    User currentFarmer=userService.findUserById(farmerId);
    User currentMilkCollector=userService.findUserById(currentFarmer.getSelectedMilkCollector());
    model.addAttribute("currMilkCollector",currentMilkCollector);
    model.addAttribute("currentFarmer",currentFarmer);
    model.addAttribute("farmerId", farmerId);
    model.addAttribute("farmerName", farmerName);
    return "currentMilkCollectorInfo";
}
    @PostMapping("/selectedMilkCollector")
    public String displaySelectedMilkCollectors(Model model)
    {
//        List<User> adminUsers = userService.getUsersByRole(1);
//        model.addAttribute("adminUsers", adminUsers);
        return "selectedMilkCollector";
    }
    @GetMapping
    public String displayAvailableMilkCollectors(Model model)
    {
        List<User> adminUsers = userService.getUsersByRole(1);
        model.addAttribute("adminUsers", adminUsers);
        return "displayAllMilkCollectors";
    }
}
