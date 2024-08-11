package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.service.FinancialReportService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/financial-status")
public class FinancialReportController {

    @Autowired
    private FinancialReportService financialReportService;

    @GetMapping("/overall")
    public ResponseEntity<Object> getOverallFinancialStatus() {
        return AppResponse.generateResponse("all_data", HttpStatus.OK, financialReportService.getOverallFinancialStatus() , true);
    }
}

