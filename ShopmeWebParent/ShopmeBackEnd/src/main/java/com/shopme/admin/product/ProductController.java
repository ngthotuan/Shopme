package com.shopme.admin.product;

import com.shopme.admin.brand.BrandService;
import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.PageInfo;
import com.shopme.common.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
                       @RequestParam(value = "detailNames") List<String> detailNames,
                       @RequestParam(value = "detailValues") List<String> detailValues)
            throws IOException {
        // set main image
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            entity.setMainImage(fileName);
        }
        // set extra images
        if (extraImages != null && !extraImages.isEmpty()) {
            for (MultipartFile file : extraImages) {
                if (!file.isEmpty()) {
                    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                    entity.addExtrasImages(fileName);
                }
            }
        }

        // set details
        if (detailNames != null && !detailNames.isEmpty() && detailValues != null && !detailValues.isEmpty()) {
            for (int i = 0; i < detailNames.size(); i++) {
                entity.addDetails(detailNames.get(i), detailValues.get(i));
            }
        }

        Product saved = service.save(entity);
        // save image
        String uploadDir = "product-images/" + saved.getId();
        String extraImagesDir = uploadDir + "/extras";
        FileUploadUtil.saveFile(uploadDir, entity.getMainImage(), image);
        if (extraImages != null && !extraImages.isEmpty()) {
            for (MultipartFile file : extraImages) {
                if (!file.isEmpty()) {
                    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                    FileUploadUtil.saveFile(extraImagesDir, fileName, file);
                }
            }
        }

        redirectAttributes.addFlashAttribute("message", "The product has been saved successfully");
        return "redirect:/products";
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

}
