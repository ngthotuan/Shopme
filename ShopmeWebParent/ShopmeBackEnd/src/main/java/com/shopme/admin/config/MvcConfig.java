package com.shopme.admin.config;

import com.shopme.admin.utils.OperatingSystem;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("user-photos", registry);
        exposeDirectory("category-images", registry);
        exposeDirectory("brand-images", registry);
        exposeDirectory("product-images", registry);
    }

    private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
        String rootPath = OperatingSystem.isWindows() ? "file:/" : "file://";
        String logicalPath = rootPath + Path.of(pathPattern).toFile().getAbsolutePath() + "/";

        registry.addResourceHandler("/" + pathPattern + "/**")
                .addResourceLocations(logicalPath);
    }

}
