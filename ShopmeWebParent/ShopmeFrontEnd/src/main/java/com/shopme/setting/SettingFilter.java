package com.shopme.setting;

import com.shopme.common.entity.Setting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SettingFilter implements Filter {
    private final SettingService service;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if (uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".jpeg")
                || uri.endsWith(".css") || uri.endsWith(".js")
        ) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        List<Setting> settings = service.getGeneralSettings();
        settings.forEach(setting -> request.setAttribute(setting.getKey(), setting.getValue()));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
