package com.java.year3.loan_api.Specification;

import com.java.year3.loan_api.entity.Loan;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class LoanSpecification implements Specification<Loan> {

    private final LoanFilter loanFilter;
    List<Predicate> predicateList = new ArrayList<>();
    @Override
    public Predicate toPredicate(Root<Loan> loanRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (loanFilter.getId() !=null){
            Predicate id = loanRoot.get("id").in(loanFilter.getId());
            predicateList.add(id);
        }
        if (loanFilter.getLoanType()!=null){
            Predicate loanType = loanRoot.get("loanType").in(loanFilter.getLoanType());
            predicateList.add(loanType);
        }
        if (loanFilter.getNationalityId()!=null){
            Predicate nationality = loanRoot.get("nationality").in(loanFilter.getNationalityId());
            predicateList.add(nationality);
        }
        if (loanFilter.getRequestNo()!=null){
            Predicate requestNo = loanRoot.get("requestNo").in(loanFilter.getRequestNo());
            predicateList.add(requestNo);
        }
        Predicate[] array = predicateList.toArray(new Predicate[0]);//convert list to array
        return criteriaBuilder.and(array);
    }
}
