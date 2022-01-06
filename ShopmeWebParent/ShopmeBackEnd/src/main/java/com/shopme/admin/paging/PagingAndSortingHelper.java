package com.shopme.admin.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class PagingAndSortingHelper {
    private ModelAndViewContainer model;
    private String list;
    private String sortField;
    private String sortType;
    private String keyword;

    public void updateModelAttributes(Page<?> page, int pageNum, int perPage) {
        model.addAttribute(list, page.getContent());

        long startCount = (long) (pageNum - 1) * perPage + 1;
        long endCount = startCount + perPage - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
    }

    public void updateModelAttributes(List<?> data, int pageNum, int perPage, long totalItems, int totalPages) {
        model.addAttribute(list, data);

        long startCount = (long) (pageNum - 1) * perPage + 1;
        long endCount = startCount + perPage - 1;
        if (endCount > totalItems) {
            endCount = totalItems;
        }
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);
    }

    public Sort getSort() {
        Sort sort = Sort.by(sortField).ascending();
        if (Objects.equals(sortType, "desc")) {
            sort = Sort.by(sortField).descending();
        }
        return sort;
    }

    public PageRequest getPageable(int pageNum, int perPage) {
        return PageRequest.of(pageNum, perPage, getSort());
    }

    public void listEntities(SearchRepository<?, ?> repo, int pageNum, int perPage) {
        PageRequest pageable = getPageable(pageNum, perPage);
        Page<?> pageModel;

        if (keyword != null && !keyword.isEmpty()) {
            pageModel = repo.findAll(keyword, pageable);
        } else {
            pageModel = repo.findAll(pageable);
        }

        updateModelAttributes(pageModel, pageNum, perPage);
    }
}
