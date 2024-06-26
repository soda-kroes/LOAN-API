package com.java.year3.loan_api.dto.response;

import com.java.year3.loan_api.dto.request.PaginationRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponseDTO {
    private List<?> list;
    private PaginationRequestDTO paginationRequestDTO;

    public PageResponseDTO(Page<?> page){
        this.list = page.getContent();
        this.paginationRequestDTO =PaginationRequestDTO.builder()
                .empty(page.isEmpty())
                .first(page.isFirst())
                .last(page.isLast())
                .pageSize(page.getPageable().getPageSize())
                .pageNumber(page.getPageable().getPageNumber() + 1)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .build();
    }
}
