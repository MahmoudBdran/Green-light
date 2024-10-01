package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.service.FinancialReportService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/financial-status")
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})
public class FinancialReportController {

    @Autowired
    private FinancialReportService financialReportService;

    @GetMapping("/overall")
    public ResponseEntity<Object> getOverallFinancialStatus(@RequestParam LocalDateTime fromDate, @RequestParam LocalDateTime toDate) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK, financialReportService.getOverallFinancialStatus(fromDate,toDate) , true);
    }
}

