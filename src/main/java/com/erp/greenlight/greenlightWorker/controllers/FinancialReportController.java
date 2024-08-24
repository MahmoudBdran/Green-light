package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/financial-status")
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})
public class FinancialReportController {

    @Autowired
    private FinancialReportService financialReportService;

    @GetMapping("/overall")
    public Map<String, Object> getOverallFinancialStatus() {
        return financialReportService.getOverallFinancialStatus();
    }
}

