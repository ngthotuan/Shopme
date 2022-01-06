package com.shopme.admin.paging;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PagingAndSortingArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(PagingAndSortingParam.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer model,
                                  NativeWebRequest request, WebDataBinderFactory binderFactory)
            throws Exception {
        PagingAndSortingParam annotation = parameter.getParameterAnnotation(PagingAndSortingParam.class);

        String sortField = request.getParameter("sortField");
        String sortType = request.getParameter("sortType");
        String keyword = request.getParameter("keyword");
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortTypeReverse", "asc".equals(sortType) ? "desc" : "asc");
        model.addAttribute("module", annotation.module());

        return new PagingAndSortingHelper(model, annotation.listName(), sortField, sortType, keyword);
    }
}
