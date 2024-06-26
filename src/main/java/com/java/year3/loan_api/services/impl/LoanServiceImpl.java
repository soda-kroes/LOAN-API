package com.java.year3.loan_api.services.impl;

import com.java.year3.loan_api.Specification.LoanFilter;
import com.java.year3.loan_api.Specification.LoanSpecification;
import com.java.year3.loan_api.dto.request.LoanRequestDTO;
import com.java.year3.loan_api.entity.*;
import com.java.year3.loan_api.exception.AlreadyExistException;
import com.java.year3.loan_api.exception.NotFoundException;
import com.java.year3.loan_api.repository.LoanRepository;
import com.java.year3.loan_api.repository.PaymentRepository;
import com.java.year3.loan_api.repository.PaymentScheduleRepository;
import com.java.year3.loan_api.services.BranchService;
import com.java.year3.loan_api.services.LoanService;
import com.java.year3.loan_api.services.LoanTypeService;
import com.java.year3.loan_api.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanTypeService loanTypeService;
    private final BranchService branchService;
    private final PaymentScheduleRepository paymentScheduleRepository;

    @Override
    public Loan createLoan(LoanRequestDTO loanRequestDTO) throws Exception {
        Loan loan = new Loan();
        loan.setFirstName(loanRequestDTO.getFirstName());
        loan.setLastName(loanRequestDTO.getLastName());
        loan.setEmail(loanRequestDTO.getEmail());
        loan.setAddress(loanRequestDTO.getAddress());
        loan.setGender(loanRequestDTO.getGender());
        loan.setMaritalStatus(loanRequestDTO.getMaritalStatus());
        loan.setCurrency(loanRequestDTO.getCurrency());

        if (Objects.equals(loanRequestDTO.getCurrency(), "KHR")) {
            if (loanRequestDTO.getLoanAmount().compareTo(new BigDecimal("400000")) < 0) {
                throw new IllegalArgumentException("Loan amount must be at least 400,000 KHR.");
            }
            loan.setLoanAmount(loanRequestDTO.getLoanAmount());
        } else if (Objects.equals(loanRequestDTO.getCurrency(), "USD")) {
            if (loanRequestDTO.getLoanAmount().compareTo(new BigDecimal("100")) < 0) {
                throw new IllegalArgumentException("Loan amount must be at least 100 USD.");
            }
            loan.setLoanAmount(loanRequestDTO.getLoanAmount());
        } else {
            // Handle unsupported currencies (optional)
            throw new IllegalArgumentException("Unsupported currency: " + loanRequestDTO.getCurrency());
        }

        if (loanRequestDTO.getLoanTerm() != null && Long.parseLong(String.valueOf(loanRequestDTO.getLoanTerm())) >= 12) {
            loan.setLoanTerm(loanRequestDTO.getLoanTerm());
        } else {
            // Handle invalid loan term (e.g., throw exception, set error message)
            throw new IllegalArgumentException("Loan term must be a greater than or equal to 12 Month.");
        }
        loan.setDateOfBirth(loanRequestDTO.getDateOfBirth());
        loan.setPhoneNumber(loanRequestDTO.getPhoneNumber());
        loan.setNationalityId(loanRequestDTO.getNationalityId());
        loan.setNationalityImage(loanRequestDTO.getNationalityImage());
        loan.setSelfieImage(loanRequestDTO.getSelfieImage());
        loan.setCreatedDate(LocalDateTime.now()); // Set current date/time
        loan.setUpdatedDate(LocalDateTime.now());

        double loanAmount = loan.getLoanAmount().doubleValue();
        double interestRate = loanTypeService.getLoanTypeById(loanRequestDTO.getLoanTypeId()).getInterestRate();
        int loanTermInMonths = loan.getLoanTerm().intValue();

        // Convert interest rate to decimal form
        double monthlyInterestRate = interestRate / 100 / 12;

        // Convert loan amount from KHR to USD
        double loanAmountInUSD;
        if (Objects.equals(loanRequestDTO.getCurrency(), "KHR")) {
            loanAmountInUSD = loanAmount / 4000;
        } else {
            loanAmountInUSD = loanAmount;
        }

        // Calculate monthly payment in USD
        double monthlyPaymentInUSD = (loanAmountInUSD * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -loanTermInMonths));

        // Convert monthly payment from USD to KHR
        double monthlyPayment;
        if (Objects.equals(loanRequestDTO.getCurrency(), "KHR")) {
            monthlyPayment = monthlyPaymentInUSD * 4000;
        } else {
            monthlyPayment = monthlyPaymentInUSD;
        }

        loan.setMonthlyPayment(monthlyPayment);

        //find total of payment
        double paymentOfMonthly = loan.getMonthlyPayment();
        Integer loanTerm = loan.getLoanTerm();
        loan.setTotalOfPayment(loanTerm * monthlyPayment);

        //find total interest
        double totalOfPayment = loan.getTotalOfPayment();
        BigDecimal amount = loan.getLoanAmount();
        double amountDouble = amount.doubleValue();
        loan.setTotalInterest(totalOfPayment- amountDouble);

        LoanType loanTypeById = loanTypeService.getLoanTypeById(loanRequestDTO.getLoanTypeId());
        loan.setLoanType(loanTypeById);

        Branch branchByBranchCode = branchService.getBranchByBranchCode(loanRequestDTO.getBranchCode());
        loan.setBranch(branchByBranchCode);

        boolean find = loanRepository.existsByNationalityId(loan.getNationalityId());
        if (find) {
            throw new AlreadyExistException("Loan nationality id " + loan.getNationalityId() + " not available");
        }
        try{
            Loan savedLoan = loanRepository.save(loan);

            // Generate payment schedule
            List<PaymentSchedule> paymentSchedule = generatePaymentSchedule(savedLoan);

            // Insert payment objects into the table for the loan
            paymentScheduleRepository.saveAll(paymentSchedule);
            return savedLoan;
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    private List<PaymentSchedule> generatePaymentSchedule(Loan loan) {
        List<PaymentSchedule> paymentSchedule = new ArrayList<>();
        BigDecimal loanAmount = loan.getLoanAmount();
        double interestRate = loan.getLoanType().getInterestRate();
        int loanTermInMonths = loan.getLoanTerm().intValue();
        LocalDateTime startDate = LocalDateTime.now();

        // Convert interest rate to decimal form
        double monthlyInterestRate = interestRate / 100 / 12;

        // Calculate monthly payment
        double monthlyPayment = (loanAmount.doubleValue() * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -loanTermInMonths));


        // Generate payment schedule
        for (int i = 1; i <= loanTermInMonths; i++) {
            PaymentSchedule payment = new PaymentSchedule();
            LocalDate paymentDate = LocalDate.from(startDate.plusMonths(i));
            int day = paymentDate.getDayOfMonth();
            int month = paymentDate.getMonthValue();
            int year = paymentDate.getYear();

            // Check if loan term exceeds 12 months
            if (loanTermInMonths > 12 && i % 12 == 0) {
                int yearsPassed = i / 12;
                year += yearsPassed - 1; // Increase the year component by the number of years passed minus 1
            }

            payment.setPaymentDate(LocalDate.of(year, month, day));
            payment.setMonthlyPaymentAmount(BigDecimal.valueOf(monthlyPayment));
            payment.setDescription("Payment for " + day + "/" + month + "/" + year);
            payment.setStatus(null);
            payment.setLoan(loan);
            paymentSchedule.add(payment);
        }

        return paymentSchedule;
    }

    @Override
    public List<Loan> getLoans() throws Exception {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanByNationalityId(String nationalityId) throws Exception {
        return loanRepository.findByNationalityId(nationalityId).orElseThrow(()-> new NotFoundException("No loan found with id " + nationalityId));
    }

    @Override
    public Loan getLoanById(Long loanId) throws Exception {
        return loanRepository.findById(loanId).orElseThrow(()-> new NotFoundException("No loan found with id " + loanId));
    }

    @Override
    public Loan updateLoan(Long loanId,LoanRequestDTO loanRequestDTO) throws Exception {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("No loan found with id " + loanId));
        loan.setFirstName(loanRequestDTO.getFirstName());
        loan.setLastName(loanRequestDTO.getLastName());
        loan.setEmail(loanRequestDTO.getEmail());
        loan.setAddress(loanRequestDTO.getAddress());
        loan.setLoanAmount(loanRequestDTO.getLoanAmount()); // Assuming Loan has a setter for loanAmount
        loan.setGender(loanRequestDTO.getGender());
        loan.setMaritalStatus(loanRequestDTO.getMaritalStatus());
        loan.setLoanTerm(loanRequestDTO.getLoanTerm());
        loan.setCurrency(loanRequestDTO.getCurrency());
        loan.setDateOfBirth(loanRequestDTO.getDateOfBirth());
        loan.setPhoneNumber(loanRequestDTO.getPhoneNumber());
        loan.setNationalityId(loanRequestDTO.getNationalityId());
        loan.setNationalityImage(loanRequestDTO.getNationalityImage());
        loan.setSelfieImage(loanRequestDTO.getSelfieImage());

        loan.setUpdatedDate(LocalDateTime.now());
        LoanType loanTypeByCode = loanTypeService.getLoanTypeById(loanRequestDTO.getLoanTypeId());
        loan.setLoanType(loanTypeByCode);

        Branch branchByBranchCode = branchService.getBranchByBranchCode(loanRequestDTO.getBranchCode());
        loan.setBranch(branchByBranchCode);
        try {
            return loanRepository.save(loan);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    @Override
    public Boolean deleteLoanById(Long loanId) throws Exception {
        boolean find = false;
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("No loan found with id " + loanId));
        if (Objects.equals(loan.getId(),loanId)){
            loanRepository.delete(loan);
            find = true;
        }
        return find;
    }

    @Override
    public Page<Loan> getAllLoans(Map<String, String> params) throws Exception {
        LoanFilter loanFilter = new LoanFilter();
        if (params.containsKey("id")){
            String id = params.get("id");
            loanFilter.setId(Long.valueOf(id));
        }
        if (params.containsKey("nationalityId")){
            String nationalityId = params.get("nationalityId");
            loanFilter.setNationalityId(Integer.valueOf(nationalityId));
        }
        if (params.containsKey("requestNo")){
            String requestNo = params.get("requestNo");
            loanFilter.setRequestNo(Long.valueOf(requestNo));
        }
        if (params.containsKey("loanType")){
            String loanType = params.get("loanType");
            loanFilter.setLoanType(loanType);
        }
        //for pagenation
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageUtil.PAGE_LIMIT)) {
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageUtil.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        LoanSpecification loanSpecification = new LoanSpecification(loanFilter);
        Pageable pageable = PageUtil.getPageable(pageNumber, pageLimit);
        Page<Loan> page = loanRepository.findAll(loanSpecification, pageable);
        return page;
    }

    @Override
    public void writeCustomerToCsv(List<Loan> loanList, Writer writer) throws IOException {
        try {

            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            printer.printRecord("REQUEST NO",
                    "FIRSTNAME",
                    "LASTNAME",
                    "GENDER",
                    "MARITAL STATUS",
                    "LOAN AMOUNT",
                    "LOAN TERM",
                    "DATE OF BIRTH",
                    "BRANCH"

            );
            for (Loan loan : loanList) {
                printer.printRecord(
                        loan.getFirstName(),
                        loan.getGender(),
                        loan.getLoanTerm(),
                        loan.getMaritalStatus(),
                        loan.getLoanAmount(),
                        loan.getLoanTerm(),
                        loan.getDateOfBirth(),
                        loan.getBranch().getName()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
