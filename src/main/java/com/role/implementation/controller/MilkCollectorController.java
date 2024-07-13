package com.role.implementation.controller;
import com.role.implementation.model.User;
import com.role.implementation.service.DefaultUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MilkCollectorController {

    @Autowired
    private DefaultUserService userService;  // Assumed service to handle user operations


    @GetMapping("/seeFarmers")
    public String seeFarmers(@RequestParam("collectorId") int collectorId, Model model) {
        List<User> farmers = userService.getFarmersByCollectorId(collectorId);
        model.addAttribute("farmers", farmers);
        return "farmersList";
    }
    @PostMapping("/selectMilkCollector")
    public String selectMilkCollector(@RequestParam("milkCollectorId") int milkCollectorId, RedirectAttributes redirectAttributes, Model model) throws Exception {
        User currentUser = userService.getCurrentUser();
        if (0 != currentUser.getSelectedMilkCollector()) {
            redirectAttributes.addFlashAttribute("message", "You have already selected a milk collector.");
            return "redirect:/dashboard";
        }
        userService.updateSelectedMilkCollector(milkCollectorId);
        User selectedMilkCollector = userService.findUserById(milkCollectorId);
        redirectAttributes.addFlashAttribute("message", "Milk collector selected successfully.");
        // Pass the selected milk collector id as a flash attribute
        redirectAttributes.addFlashAttribute("selectedMilkCollectorId", selectedMilkCollector.getId());
        return "redirect:/dashboard";
    }

    @GetMapping("/selectedMilkCollector")
    public String selectedMilkCollector(@ModelAttribute("selectedMilkCollectorId") Integer selectedMilkCollectorId, Model model) throws Exception {
        if (selectedMilkCollectorId != null) {
            User selectedMilkCollector = userService.findUserById(selectedMilkCollectorId);
            model.addAttribute("selectedMilkCollector", selectedMilkCollector);
        }
        return "selectedMilkCollector";
    }


    @PostMapping("/removeMilkCollector")
    @ResponseBody
    public String removeMilkCollector(@RequestParam("milkCollectorId") int  milkCollectorId) {
        userService.removeMilkCollector(milkCollectorId);
        return "redirect:/displayMilkCollectors";  // Redirect to the page displaying all milk collectors
    }
}
