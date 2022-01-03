package com.shopme.customer;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService service;
    private final SettingService settingService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        List<Country> countries = service.findAllCountries();
        model.addAttribute("customer", new Customer());
        model.addAttribute("countries", countries);
        return "register/register_form";
    }

    @PostMapping("/create_customer")
    public String registerCustomer(HttpServletRequest request, Customer customer, Model model)
            throws MessagingException, UnsupportedEncodingException {
        service.register(customer);
        sentVerificationEmail(request, customer);
        model.addAttribute("pageTitle", "Registration Successful");
        return "register/register_success";
    }

    private void sentVerificationEmail(HttpServletRequest request, Customer customer)
            throws MessagingException, UnsupportedEncodingException {
        EmailSettingBag settingBag = settingService.getEmailSettings();
        String siteUrl = Utility.getSiteUrl(request);

        JavaMailSender mailSender = Utility.prepareMailSender(settingBag);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(settingBag.getFromAddress(), settingBag.getSenderName());
        helper.setTo(customer.getEmail());
        helper.setSubject(settingBag.getCustomerVerifySubject());

        String content = settingBag.getCustomerVerifyContent();
        content = content.replace("[[name]]", customer.getFullName());
        content = content.replace("[[url]]", String.format("%s/verify?code=%s",
                siteUrl, customer.getVerificationCode()));
        helper.setText(content, true);

        mailSender.send(message);
        log.info("Sent verification email to {}", customer.getEmail());
    }
}
