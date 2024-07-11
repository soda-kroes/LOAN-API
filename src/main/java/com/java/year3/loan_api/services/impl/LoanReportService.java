package com.java.year3.loan_api.services.impl;

import com.java.year3.loan_api.database.PostgrestConnection;
import com.java.year3.loan_api.dto.response.LoanResponse;
import com.java.year3.loan_api.dto.response.LoanResponseDTO;
import com.java.year3.loan_api.repository.LoanReportRepository;
import com.java.year3.loan_api.result_set.LoanReportResultSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanReportService {
    private final LoanReportRepository loanReportRepository;
    private final LoanReportResultSet loanReportResultSet;

    private PostgrestConnection postgrestConnection = new PostgrestConnection();

    public List<LoanResponse> getAllLoanReport(String fromDate, String toDate, Integer loanTypeId) {
        try {
            String query = loanReportRepository.findByLoanReport(fromDate, toDate, loanTypeId);
            System.out.println("================================");
            System.out.println(query);
            System.out.println("================================");
            if (query == null || query == "") {
                // Handle the case when the query is null
                return Collections.emptyList();
            }

            ResultSet resultSet = postgrestConnection.selectAll(query);
            if (resultSet == null) {
                // Handle the case when the resultSet is null
                return Collections.emptyList();
            }

            return loanReportResultSet.getLoanReport(resultSet);
        } catch (SQLException e) {
            // Handle the SQLException
            e.printStackTrace(); // or log the error
            return Collections.emptyList();
        }
    }
}