package com.java.year3.loan_api.utils.services;

import com.java.year3.loan_api.dto.response.LoanResponseDTO;
import com.java.year3.loan_api.entity.Loan;

import java.util.Arrays;
import java.util.Base64;

public class LoanResponseService {
    public static LoanResponseDTO createLoanResponseDTO(Loan loan) {
        LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
        loanResponseDTO.setId(loan.getId());
        loanResponseDTO.setFirstName(loan.getFirstName());
        loanResponseDTO.setLastName(loan.getLastName());
        loanResponseDTO.setGender(loan.getGender());
        loanResponseDTO.setEmail(loan.getEmail());
        loanResponseDTO.setAddress(loan.getAddress());
        loanResponseDTO.setMaritalStatus(loan.getMaritalStatus());
        loanResponseDTO.setDateOfBirth(loan.getDateOfBirth());
        loanResponseDTO.setPhoneNumber(loan.getPhoneNumber());
        loanResponseDTO.setNationalityId(loan.getNationalityId());
        loanResponseDTO.setNationalityId(loan.getNationalityId());
        loanResponseDTO.setNationalityImage(loan.getNationalityImage());
        loanResponseDTO.setCreatedDate(loan.getCreatedDate());
        loanResponseDTO.setUpdatedDate(loan.getUpdatedDate());
        loanResponseDTO.setLoanAmount(loan.getLoanAmount());
        loanResponseDTO.setCurrency(loan.getCurrency());
        loanResponseDTO.setMonthlyPayment(loan.getMonthlyPayment());
        loanResponseDTO.setTotalOfPayment(loan.getTotalOfPayment());
        loanResponseDTO.setTotalInterest(loan.getTotalInterest());
        loanResponseDTO.setBranch(loan.getBranch());
        loanResponseDTO.setLoanType(loan.getLoanType());
        loanResponseDTO.setLoanTerm(loan.getLoanTerm());
        return loanResponseDTO;
    }



    public static LoanResponseDTO convertToLoanResponseDTO(Loan loan) {
        LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
        loanResponseDTO.setId(loan.getId());
        loanResponseDTO.setFirstName(loan.getFirstName());
        loanResponseDTO.setLastName(loan.getLastName());
        loanResponseDTO.setEmail(loan.getEmail());
        loanResponseDTO.setAddress(loan.getAddress());
        loanResponseDTO.setMaritalStatus(loan.getMaritalStatus());
        loanResponseDTO.setDateOfBirth(loan.getDateOfBirth());
        loanResponseDTO.setPhoneNumber(loan.getPhoneNumber());
        loanResponseDTO.setNationalityId(loan.getNationalityId());
        loanResponseDTO.setNationalityImage(loan.getNationalityImage());
        loanResponseDTO.setSelfieImage(loan.getSelfieImage());
        loanResponseDTO.setCreatedDate(loan.getCreatedDate());
        loanResponseDTO.setUpdatedDate(loan.getUpdatedDate());
        loanResponseDTO.setLoanAmount(loan.getLoanAmount());
        loanResponseDTO.setCurrency(loan.getCurrency());
        loanResponseDTO.setMonthlyPayment(loan.getMonthlyPayment());
        loanResponseDTO.setTotalOfPayment(loan.getTotalOfPayment());
        loanResponseDTO.setTotalInterest(loan.getTotalInterest());
        loanResponseDTO.setBranch(loan.getBranch());
        loanResponseDTO.setLoanType(loan.getLoanType());
        return loanResponseDTO;
    }

    private static byte[] decodeBase64Image(String base64Image) {
        if (base64Image == null || base64Image.isEmpty()) {
            return null;
        }

        try {
            return Base64.getDecoder().decode(base64Image);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid base64Image provided", e);
        }
    }
}
