package com.java.year3.loan_api.controller;


import com.java.year3.loan_api.dto.request.LoanRequestDTO;
import com.java.year3.loan_api.dto.request.LoanTypeRequestDTO;
import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.entity.Loan;
import com.java.year3.loan_api.entity.LoanType;
import com.java.year3.loan_api.entity.PaymentMethod;
import com.java.year3.loan_api.services.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loan-type")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoanTypeController {
    private final LoanTypeService loanTypeService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody LoanTypeRequestDTO loanTypeRequest) throws Exception {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        LoanType data = loanTypeService.addLoanType(loanTypeRequest);
        if (data != null) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Loan Type created successfully");
            response.put("data", data);
        } else {
            statusResponseDTO.setErrorCode(204); // No Content
            statusResponseDTO.setErrorMessage("Your Content Unexceptionable!");
            response.put("data", null);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        Map<String,Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        List<LoanType> data = loanTypeService.getAllLoanTypes();
        if (data.isEmpty()){
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("Loan type List is empty");
            response.put("data", null);
        }else{
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Loan type list retrieved successfully");
            response.put("data", data);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-interest-rate-by-id/{id}")
    public ResponseEntity<?> getInterestRateById(@PathVariable(name = "id") Long id) throws Exception {
        Map<String,Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        Double interestRateById = loanTypeService.getInterestRateById(id);
        statusResponseDTO.setErrorCode(200);
        statusResponseDTO.setErrorMessage("successfully");
        response.put("data", interestRateById);
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-by-code/{code}")
    public ResponseEntity<?> getByCode(@PathVariable(name = "code") Long code) throws Exception {
        Map<String,Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        LoanType data = loanTypeService.getLoanTypeById(code);
        if (data == null){
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("Loan type is empty");
            response.put("data", null);
        }else{
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Loan type retrieved successfully");
            response.put("data", data);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
