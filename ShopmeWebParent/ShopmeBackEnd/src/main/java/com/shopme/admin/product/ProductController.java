package com.shopme.admin.product;

import com.shopme.admin.brand.BrandService;
import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.PageInfo;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.shopme.common.utils.Common.setModelListPage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    private final BrandService brandService;

    @GetMapping({"", "/"})
    public String listFirstPage(Model model) {
        return listByPage(model, 1, "name", "asc", null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(Model model, @PathVariable(name = "pageNum") Integer pageNum,
                             @RequestParam(name = "sortField", required = false, defaultValue = "name") String sortField,
                             @RequestParam(name = "sortType", required = false, defaultValue = "asc") String sortType,
                             @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {

        PageInfo pageInfo = new PageInfo();
        List<Product> list = service.listByPage(pageInfo, pageNum, sortField, sortType, keyword);

        model.addAttribute("products", list);
        setModelListPage(model, "products", sortField, sortType, keyword, pageInfo);
        return "product/products";
    }

    @GetMapping("/new")
    public String create(Model model) {
        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);
        List<Brand> brands = brandService.listAll();

        model.addAttribute("pageTitle", "Create New Product");
        model.addAttribute("product", product);
        model.addAttribute("brands", brands);
        return "product/product_form";
    }

    @PostMapping("/save")
    public String save(Product entity, RedirectAttributes redirectAttributes,
                       @RequestParam(value = "fileImage", required = false) MultipartFile image,
                       @RequestParam(value = "extraImage", required = false) List<MultipartFile> extraImages,
                       @RequestParam(value = "detailIDs") String[] detailIDs,
                       @RequestParam(value = "detailNames") String[] detailNames,
                       @RequestParam(value = "detailValues") String[] detailValues,
                       @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
                       @RequestParam(name = "imageNames", required = false) String[] imageNames
    ) throws IOException {
        setMainImage(entity, image);
        setExistingExtraImageNames(entity, imageIDs, imageNames);
        setExtraImages(entity, extraImages);
        setDetails(entity, detailIDs, detailNames, detailValues);

        Product saved = service.save(entity);
        saveImages(image, extraImages, saved);
        removeUnusedImages(saved);

        redirectAttributes.addFlashAttribute("message", "The product has been saved successfully");
        return "redirect:/products";
    }

    private void removeUnusedImages(Product saved) {
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

    private void setExistingExtraImageNames(Product entity, String[] imageIDs, String[] imageNames) {
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

    private void saveImages(MultipartFile image, List<MultipartFile> extraImages, Product saved) throws IOException {
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

    private void setDetails(Product entity, String[] detailIDs, String[] detailNames, String[] detailValues) {
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

    private void setExtraImages(Product entity, List<MultipartFile> extraImages) {
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

    private void setMainImage(Product entity, MultipartFile image) {
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            entity.setMainImage(fileName);
        }
    }

    @GetMapping("/{id}/enabled/{enabled}")
    @Transactional
    public String updateStatus(@PathVariable Long id,
                               @PathVariable boolean enabled,
                               RedirectAttributes redirectAttributes) {
        String status = enabled ? "enabled" : "disabled";
        service.updateEnabledStatus(id, enabled);
        redirectAttributes.addFlashAttribute("message",
                String.format("The product with ID %d has been %s", id, status));
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message",
                    String.format("The product with ID %d has been deleted successfully", id));
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        try {
            Product product = service.get(id);
            List<Brand> brands = brandService.listAll();

            model.addAttribute("pageTitle", "Edit Product");
            model.addAttribute("product", product);
            model.addAttribute("brands", brands);
            return "product/product_form";
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/products";
        }
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        try {
            Product product = service.get(id);
            model.addAttribute("pageTitle", "Detail Product");
            model.addAttribute("product", product);
            return "product/product_detail_dialog";
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/products";
        }
    }

}
