package com.java.year3.loan_api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationRequestDTO {
    private int pageSize;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
    private long numberOfElements;
    private boolean first;
    private boolean last;
    private boolean empty;
}
