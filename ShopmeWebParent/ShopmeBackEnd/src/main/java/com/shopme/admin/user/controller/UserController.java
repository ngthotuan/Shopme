package com.shopme.admin.user.controller;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import com.shopme.admin.user.UserExporter;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.admin.user.UserService;
import com.shopme.admin.user.role.RoleService;
import com.shopme.admin.utils.Exporter;
import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping({"", "/"})
    public String listFirstPage() {
        return "redirect:/users/page/1?sortField=firstName&sortType=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable Integer pageNum,
                             @PagingAndSortingParam(module = "users", listName = "users")
                                     PagingAndSortingHelper helper) {
        userService.listByPage(pageNum, helper);
        return "users/users";
    }

    @GetMapping("/new")
    public String createUser(Model model) {
        User user = new User();
        user.setEnabled(true);
        List<Role> roles = roleService.getAll();
        model.addAttribute("pageTitle", "Create New User");
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "users/user_form";
    }

    @PostMapping("/save")
    public String saveUser(User user, @RequestParam(value = "image", required = false) MultipartFile image,
                           RedirectAttributes redirectAttributes) throws IOException {
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            user.setPhotos(fileName);
            User savedUser = userService.save(user);
            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, image);
        } else {
            if (user.getPhotos().isEmpty()) {
                user.setPhotos(null);
            }
            userService.save(user);
        }
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");

        return redirectAfterUserModified(user);
    }

    @GetMapping("/edit/{id}")
    public String updateUser(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findById(id);
            List<Role> roles = roleService.getAll();
            model.addAttribute("pageTitle", String.format("Edit User ID (%s)", user.getId()));
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return "users/user_form";

        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message", String.format("The user with ID %d has been delete successfully", id));
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/{id}/enabled/{enabled}")
    public String updateUserStatus(@PathVariable Long id,
                                   @PathVariable boolean enabled,
                                   RedirectAttributes redirectAttributes) {
        String status = enabled ? "enabled" : "disabled";
        userService.updateUserEnabledStatus(id, enabled);
        redirectAttributes.addFlashAttribute("message", String.format("The user with ID %d has been %s", id, status));
        return "redirect:/users";
    }

    @GetMapping("/export/csv")
    public void exportUserToCSV(HttpServletResponse response) throws IOException {
        List<User> list = userService.listAll();
        Exporter<User> exporter = new UserExporter(response);
        exporter.exportCSV(list);
    }

    @GetMapping("/export/excel")
    public void exportUserToExcel(HttpServletResponse response) throws IOException {
        List<User> list = userService.listAll();
        Exporter<User> exporter = new UserExporter(response);
        exporter.exportExcel(list);
    }

    @GetMapping("/export/pdf")
    public void exportUserToPDF(HttpServletResponse response) throws IOException {
        List<User> list = userService.listAll();
        Exporter<User> exporter = new UserExporter(response);
        exporter.exportPDF(list);
    }


    private String redirectAfterUserModified(User user) {
        String firstPartOfEmail = user.getEmail().split("@")[0];
        return "redirect:/users/page/1/?sortField=id&sortType=asc&keyword=" + firstPartOfEmail;
    }
}
