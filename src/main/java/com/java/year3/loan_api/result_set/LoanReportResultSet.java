package com.java.year3.loan_api.result_set;

import com.java.year3.loan_api.dto.response.LoanResponse;
import com.java.year3.loan_api.dto.response.LoanResponseDTO;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoanReportResultSet {

    public List<LoanResponse> getLoanReport(ResultSet resultSet) throws SQLException {
        List<LoanResponse> loans = new ArrayList<>();

        while (resultSet.next()) {
            LoanResponse loan = mapLoanResponseDTO(resultSet);
            loans.add(loan);
        }
        return loans;
    }

    private LoanResponse mapLoanResponseDTO(ResultSet resultSet) throws SQLException {
        LoanResponse loan = new LoanResponse();
        loan.setId(resultSet.getLong("id"));
        loan.setFirstName(resultSet.getString("first_name"));
        loan.setLastName(resultSet.getString("last_name"));
        loan.setGender(resultSet.getString("gender"));
        loan.setEmail(resultSet.getString("email"));
        loan.setAddress(resultSet.getString("address"));
        loan.setPhoneNumber(resultSet.getString("phone_number"));
        loan.setMaritalStatus(resultSet.getString("marital_status"));
        loan.setNationalityId(resultSet.getString("nationality_id"));
        loan.setCurrency(resultSet.getString("currency"));
        loan.setLoanAmount(resultSet.getBigDecimal("loan_amount"));
        loan.setLoanTerm(resultSet.getInt("loan_term"));
        loan.setNationalityImage(resultSet.getBytes("nationality_image"));
        loan.setSelfieImage(resultSet.getBytes("selfie_image"));
        loan.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
        loan.setBranch(resultSet.getString("branch_name"));
        loan.setLoanType(resultSet.getString("loan_type"));
        loan.setCreatedDate(resultSet.getString("created_date"));
        loan.setUpdatedDate(resultSet.getString("updated_date"));
        loan.setMonthlyPayment(resultSet.getDouble("monthly_payment"));
        return loan;
    }
}