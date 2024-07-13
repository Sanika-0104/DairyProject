package com.role.implementation.controller;

import com.role.implementation.model.User;
import com.role.implementation.service.DefaultUserService;
import com.role.implementation.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;

@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    DefaultUserService userService;


    @PostMapping("/createReport")
    public String createReport(@ModelAttribute("currentFarmer") User currentFarmer, @RequestParam("date") String datestr,@RequestParam("currMilkCollectorId") int currMilkCollectorId
                               ,RedirectAttributes redirectAttributes) throws Exception {
        int milkUnits=(int)currentFarmer.getMilkUnitsPerDay();
        System.out.println("mcid"+currMilkCollectorId);
        System.out.println("id"+currentFarmer.getId());
        User milkCollector=userService.findUserById(currMilkCollectorId);

        int price=(int) milkCollector.getRatePerLiter();
        int amount=milkUnits*price;
        userService.saveReport(datestr, milkUnits, price,amount);
        redirectAttributes.addFlashAttribute("successMessage", "Report created successfully.");
       return "redirect:displayAllMilkCollectors/currentMilkCollector?farmerId=" +currentFarmer.getId() +"&farmerName=" +currentFarmer.getName();
        //return "currentMilkCollectorInfo";
    }

    @GetMapping("/report/{format}")
    @ResponseBody
    public String generateReport(@PathVariable String format, Model model) throws FileNotFoundException, JRException {
        String reportPath = reportService.exportReport(format);
        model.addAttribute("reportPath", reportPath);
        return "Report generated at: " + reportPath;
    }
}
