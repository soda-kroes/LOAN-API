package com.java.year3.loan_api.controller;

import com.java.year3.loan_api.dto.response.LoanResponse;
import com.java.year3.loan_api.dto.response.StatusResponse;
import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.services.impl.LoanReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoanReportController {
    private final LoanReportService loanReportService;

    @RequestMapping(value = "/get-loan-report", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate, @RequestParam(required = false) Integer loanTypeId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StatusResponseDTO status = new StatusResponseDTO();
        try {
            List<LoanResponse> response = loanReportService.getAllLoanReport(fromDate, toDate, loanTypeId);

            if (response != null) {
                status.setErrorCode(200);
                status.setErrorMessage("Your Request was successful");
                map.put("data", response);
                map.put("status", status);
            }
        } catch (Exception e) {
            status.setErrorCode(204);
            status.setErrorMessage("Your Content Unacceptable!");
            map.put("data", null);
            map.put("status", status);
            e.printStackTrace();
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
