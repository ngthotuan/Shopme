package com.shopme.common.utils;

import com.shopme.common.entity.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

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
        model.addAttribute("sortTypeReverse", sortType.equals("asc") ? "desc" : "asc");
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
}
