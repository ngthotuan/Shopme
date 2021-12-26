package com.shopme.admin.product;

import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.ProductImage;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ProductSaveHelper {
    static void removeUnusedImages(Product saved) {
        String imageDir = "product-images/" + saved.getId() + "/extras/";
        try {
            Files.list(Paths.get(imageDir)).forEach(file -> {
                if (!saved.containsImageName(file.getFileName().toString())) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void setExistingExtraImageNames(Product entity, String[] imageIDs, String[] imageNames) {
        if (imageIDs != null && imageNames != null) {
            Set<ProductImage> images = new HashSet<>();
            for (int i = 0; i < imageIDs.length; i++) {
                ProductImage image = new ProductImage();
                image.setId(Long.parseLong(imageIDs[i]));
                image.setName(imageNames[i]);
                image.setProduct(entity);
                images.add(image);
            }
            entity.setImages(images);
        }
    }

    static void saveImages(MultipartFile image, List<MultipartFile> extraImages, Product saved) throws IOException {
        String uploadDir = "product-images/" + saved.getId();
        String extraImagesDir = uploadDir + "/extras";
        if (!image.isEmpty()) {
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, saved.getMainImage(), image);
        }
        if (extraImages == null || extraImages.isEmpty()) {
            return;
        }
        for (MultipartFile file : extraImages) {
            if (file.isEmpty()) continue;
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            FileUploadUtil.saveFile(extraImagesDir, fileName, file);
        }
    }

    static void setDetails(Product entity, String[] detailIDs, String[] detailNames, String[] detailValues) {
        if (detailNames == null || detailValues == null) {
            return;
        }
        for (int i = 0; i < detailNames.length; i++) {
            String name = detailNames[i];
            String value = detailValues[i];
            long id = Long.parseLong(detailIDs[i]);

            if (id != -1) {
                entity.addDetail(id, name, value);
            } else {
                if (!name.isEmpty() && !value.isEmpty()) {
                    entity.addDetail(name, value);
                }
            }


        }
    }

    static void setExtraImages(Product entity, List<MultipartFile> extraImages) {
        if (extraImages == null || extraImages.isEmpty()) {
            return;
        }
        for (MultipartFile file : extraImages) {
            if (file.isEmpty()) continue;

            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            if (!entity.containsImageName(fileName)) {
                entity.addExtraImage(fileName);
            }
        }
    }

    static void setMainImage(Product entity, MultipartFile image) {
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            entity.setMainImage(fileName);
        }
    }
}
