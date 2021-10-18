package com.shopme.admin.user;

import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Transactional
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("users")
    public String listAll(Model model) {
        return listByPage(model, 1, "id", "asc", null);
    }

    @GetMapping("users/page/{pageNum}")
    public String listByPage(Model model, @PathVariable Integer pageNum,
                             @RequestParam String sortField, @RequestParam String sortType,
                             @RequestParam(required = false) String keyword) {
        Page<User> userPage = userService.listByPage(pageNum, sortField, sortType, keyword);
        List<User> users = userPage.getContent();

        long startCount = (long) (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
        long endCount = pageNum * UserService.USERS_PER_PAGE;
        if (endCount > userPage.getTotalElements()) {
            endCount = userPage.getTotalElements();
        }
        String sortTypeReverse = sortType.equals("asc") ? "desc" : "asc";

        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("users", users);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypeReverse", sortTypeReverse);
        model.addAttribute("keyword", keyword);
        return "users";
    }

    @GetMapping("users/new")
    public String createUser(Model model) {
        User user = new User();
        user.setEnabled(true);
        List<Role> roles = roleService.getAll();
        model.addAttribute("pageTitle", "Create New User");
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user_form";
    }

    @PostMapping("users/save")
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

    @GetMapping("/users/edit/{id}")
    public String updateUser(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findById(id);
            List<Role> roles = roleService.getAll();
            model.addAttribute("pageTitle", String.format("Edit User ID (%s)", user.getId()));
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return "user_form";

        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message", String.format("The user with ID %d has been delete successfully", id));
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{enabled}")
    public String updateUserStatus(@PathVariable Long id,
                                   @PathVariable boolean enabled,
                                   RedirectAttributes redirectAttributes) {
        String status = enabled ? "enabled" : "disabled";
        userService.updateUserEnabledStatus(id, enabled);
        redirectAttributes.addFlashAttribute("message", String.format("The user with ID %d has been %s", id, status));
        return "redirect:/users";
    }

    private String redirectAfterUserModified(User user) {
        String firstPartOfEmail = user.getEmail().split("@")[0];
        return "redirect:/users/page/1/?sortField=id&sortType=asc&keyword=" + firstPartOfEmail;
    }
}
