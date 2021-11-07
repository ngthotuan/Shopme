package com.shopme.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageInfo {
    private int totalPages;
    private long totalItems;
    private int currentPage;
    private long startCount;
    private long endCount;
}
