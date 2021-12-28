package com.shopme.common.utils;

import com.shopme.common.entity.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.Objects;

public class Common {
    public static void setModelListPage(Model model, String module, String sortField, String sortType,
                                        String keyword, PageInfo pageInfo) {
        model.addAttribute("module", module);
        model.addAttribute("startCount", pageInfo.getStartCount());
        model.addAttribute("endCount", pageInfo.getEndCount());
        model.addAttribute("totalItems", pageInfo.getTotalItems());
        model.addAttribute("currentPage", pageInfo.getCurrentPage());
        model.addAttribute("totalPages", pageInfo.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypeReverse", "asc".equals(sortType) ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
    }

    public static void setPageInfo(PageInfo pageInfo, int pageNum, Page<?> page, int perPage) {
        long startCount = (long) (pageNum - 1) * perPage + 1;
        long endCount = startCount + perPage - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        pageInfo.setTotalItems(page.getTotalElements());
        pageInfo.setTotalPages(page.getTotalPages());
        pageInfo.setCurrentPage(pageNum);
        pageInfo.setStartCount(startCount);
        pageInfo.setEndCount(endCount);
    }

    public static Sort createSort(String sortField, String sortType) {
        Sort sort = Sort.by(sortField).ascending();
        if (Objects.equals(sortType, "desc")) {
            sort = Sort.by(sortField).descending();
        }
        return sort;
    }
}
