package com.shopme.admin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String userPhotosDirName = "user-photos";
        Path userPhotosDirPath = Path.of(userPhotosDirName);
        String userPhotosPath = userPhotosDirPath.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + userPhotosDirName + "/**")
                .addResourceLocations("file://" + userPhotosPath + "/");

        String categoryImageDirName = "category-images";
        Path categoryImageDirPath = Path.of(categoryImageDirName);
        String categoryImagePath = categoryImageDirPath.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + categoryImageDirName + "/**")
                .addResourceLocations("file://" + categoryImagePath + "/");
    }
}
