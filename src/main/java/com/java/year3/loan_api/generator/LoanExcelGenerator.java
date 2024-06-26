package com.java.year3.loan_api.generator;

import com.java.year3.loan_api.dto.request.LoanRequestDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoanExcelGenerator {
    private final List<LoanRequestDTO> loanRequestDTOList;
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public LoanExcelGenerator(List<LoanRequestDTO> loanRequestDTOList) {
        this.loanRequestDTOList = loanRequestDTOList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("loan");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "REQUEST NO", style);
        createCell(row, 1, "FIRSTNAME", style);
        createCell(row, 2, "LASTNAME", style);
        createCell(row, 3, "GENDER", style);
        createCell(row, 4, "MARITAL STATUS", style);
        createCell(row, 5, "LOAN AMOUNT", style);
        createCell(row, 6, "LOAN TERM", style);
        createCell(row, 7, "DATE OF BIRTH", style);
        createCell(row, 8, "BRANCH", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
        sheet.autoSizeColumn(columnCount); // Auto-fit the data column width
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (LoanRequestDTO record : loanRequestDTOList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, record.getFirstName(), style);
            createCell(row, columnCount++, record.getLastName(), style);
            createCell(row, columnCount++, record.getGender(), style);
            createCell(row, columnCount++, record.getMaritalStatus(), style);
            createCell(row, columnCount++, record.getLoanAmount(), style);
            createCell(row, columnCount++, record.getLoanTerm(), style);
            createCell(row, columnCount++, record.getDateOfBirth(), style);
            createCell(row, columnCount++, record.getBranchCode(), style);
        }
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}