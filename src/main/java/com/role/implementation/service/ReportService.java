package com.role.implementation.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.role.implementation.model.Report;
import com.role.implementation.repository.ReportRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReportRepository repository;

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\sanik\\Desktop\\RestartNew\\1\\ui_updated\\RoleBasedAuthentication-main\\src\\main\\resources";
        List<Report> reports = repository.findAll();
        // Load file and compile it
        File file = ResourceUtils.getFile("classpath:Simple_Blue.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reports);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        String reportPath = "";
        if (reportFormat.equalsIgnoreCase("html")) {
            reportPath = path + "\\reports.html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportPath);
        } else if (reportFormat.equalsIgnoreCase("pdf")) {
            reportPath = path + "\\reports.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath);
        }
        return reportPath;
    }
}