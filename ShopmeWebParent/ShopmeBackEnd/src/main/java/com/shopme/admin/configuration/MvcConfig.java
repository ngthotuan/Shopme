package com.shopme.admin.configuration;

import com.shopme.admin.utils.OperatingSystem;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String rootPath = OperatingSystem.isWindows() ? "file:/" : "file://";

        String userPhotosDirName = "user-photos";
        Path userPhotosDirPath = Path.of(userPhotosDirName);
        String userPhotosPath = userPhotosDirPath.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + userPhotosDirName + "/**")
                .addResourceLocations(rootPath + userPhotosPath + "/");

        String categoryImageDirName = "category-images";
        Path categoryImageDirPath = Path.of(categoryImageDirName);
        String categoryImagePath = categoryImageDirPath.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + categoryImageDirName + "/**")
                .addResourceLocations(rootPath + categoryImagePath + "/");

        String brandImageDirName = "brand-images";
        Path brandImageDirPath = Path.of(brandImageDirName);
        String brandImagePath = brandImageDirPath.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + brandImageDirName + "/**")
                .addResourceLocations(rootPath + brandImagePath + "/");
    }
}
