package com.shopme.customer;

import com.shopme.common.entity.Customer;
import com.shopme.common.exception.CustomerNotFoundException;
import com.shopme.setting.EmailSettingBag;
import com.shopme.setting.SettingService;
import com.shopme.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {

    private final CustomerService customerService;
    private final SettingService settingService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "customer/forgot_password";
    }

    @PostMapping("/forgot_password")
    public String showForgotPasswordForm(Model model, HttpServletRequest request) {
        String email = request.getParameter("email");
        try {
            Customer customer = customerService.updateResetPasswordCode(email);
            sentForgotPasswordEmail(request, customer);
            model.addAttribute("message", "Password reset link has been sent to your email");
        } catch (CustomerNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            model.addAttribute("error", "Internal server error");
        }
        return "customer/forgot_password";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@RequestParam("code") String code, Model model) {
        Customer customer = customerService.getByResetPasswordCode(code);
        if (customer == null) {
            return "customer/reset_password_fail";
        }
        model.addAttribute("code", code);
        return "customer/reset_password";
    }

    @PostMapping("/reset_password")
    public String resetPassword(@RequestParam("code") String code, @RequestParam("password") String password) {
        System.out.println(code);
        System.out.println(password);
        Customer customer = customerService.getByResetPasswordCode(code);
        if (customer == null) {
            return "customer/reset_password_fail";
        }
        customerService.updatePassword(code, password);
        return "customer/reset_password_success";
    }

    private void sentForgotPasswordEmail(HttpServletRequest request, Customer customer)
            throws MessagingException, UnsupportedEncodingException {
        EmailSettingBag settingBag = settingService.getEmailSettings();
        String siteUrl = Utility.getSiteUrl(request);

        JavaMailSender mailSender = Utility.prepareMailSender(settingBag);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(settingBag.getFromAddress(), settingBag.getSenderName());
        helper.setTo(customer.getEmail());
        helper.setSubject(settingBag.getCustomerForgotPasswordSubject());

        String content = settingBag.getCustomerForgotPasswordContent();
        content = content.replace("[[name]]", customer.getFullName());
        content = content.replace("[[url]]", String.format("%s/reset_password?code=%s",
                siteUrl, customer.getResetPasswordCode()));
        helper.setText(content, true);

        mailSender.send(message);
        log.info("Sent reset password email to {}", customer.getEmail());
    }

}
