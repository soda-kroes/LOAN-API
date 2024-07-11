package com.java.year3.loan_api.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class LoanReportRepository {

    public String findByLoanReport(String fromDate, String toDate, Integer loanTypeId) {
        StringBuilder queryBuilder = new StringBuilder("SELECT l.*, lt.name AS loan_type, b.name AS branch_name FROM LOAN l ");
        queryBuilder.append("INNER JOIN loan_type lt ON l.loan_type_id = lt.id ");
        queryBuilder.append("INNER JOIN branch b ON l.branch_code = b.code WHERE ");

        boolean hasConditions = false;

        if (!StringUtils.isEmpty(fromDate) && !StringUtils.isEmpty(toDate)) {
            queryBuilder.append("DATE(l.created_date) BETWEEN '" + fromDate + "' AND '" + toDate + "' ");
            hasConditions = true;
        }

        if (loanTypeId != null && loanTypeId != 0) {
            if (hasConditions) {
                queryBuilder.append("AND ");
            }
            queryBuilder.append("l.loan_type_id = " + loanTypeId + " ");
            hasConditions = true;
        }

        if (!hasConditions) {
            return "";
        }

        return queryBuilder.toString();
    }
}