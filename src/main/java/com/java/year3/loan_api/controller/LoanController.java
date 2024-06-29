package com.java.year3.loan_api.controller;

import com.java.year3.loan_api.dto.request.LoanRequestDTO;
import com.java.year3.loan_api.dto.response.LoanResponseDTO;
import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.entity.Loan;
import com.java.year3.loan_api.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody LoanRequestDTO loanRequestDTO) throws Exception {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        LoanResponseDTO data = loanService.createLoan(loanRequestDTO);
        if (data != null) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Loan created successfully");
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
    public ResponseEntity<?> getAllLoans() throws Exception {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        List<LoanResponseDTO> data = loanService.getLoans();
        if (data.isEmpty()){
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("Loan List is empty");
            response.put("data", null);
        }else{
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Loan list retrieved successfully");
            response.put("data", data);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all-loan")
    public ResponseEntity<?> getAll(@RequestParam Map<String, String> params) throws Exception {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        Page<LoanResponseDTO> data = loanService.getAllLoans(params);
        if (data.isEmpty()){
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("Loan List is empty");
            response.put("data", null);
        }else{
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Loan list retrieved successfully");
            response.put("data", data);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-loan-by-nationalityId/{id}")
    public ResponseEntity<?> getLoanByNationalityId(@PathVariable String id) throws Exception {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        LoanResponseDTO data = loanService.getLoanByNationalityId(id);
        if (data != null) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Loan list retrieved successfully");
            response.put("data", data);
        }else{
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("Your Content Unexceptionable!");
            response.put("data", null);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/get-loan-by-id/{id}")
    public ResponseEntity<?> getLoanByRequestNo(@PathVariable(name = "id") Long loanId) throws Exception {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        LoanResponseDTO data = loanService.getLoanById(loanId);
        if (data != null) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Loan list retrieved successfully");
            response.put("data", data);
        }else{
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("Your Content Unexceptionable!");
            response.put("data", null);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLoan(@PathVariable(name = "id") Long requestNo, @RequestBody LoanRequestDTO loanRequestDTO)throws Exception {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        LoanResponseDTO data = loanService.updateLoan(requestNo, loanRequestDTO);
        if (data != null) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Loan updated successfully");
            response.put("data", data);
        }else{
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("Your Content Unexceptionable!");
            response.put("data", null);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable(name = "id") Long loanId) throws Exception {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        Boolean isDelete = loanService.deleteLoanById(loanId);
        if (isDelete) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Loan deleted successfully");
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/export-all", method = RequestMethod.GET)
    public void  getLoanByCID(HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv; charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=loan.csv");
        List<LoanResponseDTO> loans = loanService.getLoans();

        loanService.writeCustomerToCsv(loans, response.getWriter());
    }
}
