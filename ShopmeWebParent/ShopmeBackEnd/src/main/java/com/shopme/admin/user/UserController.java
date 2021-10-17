package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("users")
    public String listAll(Model model) {
        List<User> users = userService.listAll();
        model.addAttribute("users", users);
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
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "The user has been created successfully");
        return "redirect:/users";
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
    public String deleteUser(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message", String.format("The user with ID %d has been delete successfully", id));
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/users";
    }
}
