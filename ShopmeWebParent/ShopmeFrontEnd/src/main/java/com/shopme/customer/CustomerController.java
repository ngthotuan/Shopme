package com.shopme.customer;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.security.CustomerUserDetails;
import com.shopme.security.oauth.CustomerOAuth2User;
import com.shopme.setting.EmailSettingBag;
import com.shopme.setting.SettingService;
import com.shopme.setting.country.CountryService;
import com.shopme.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService service;
    private final SettingService settingService;
    private final CountryService countryService;

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

    @GetMapping("/verify")
    public String verify(Model model, @RequestParam("code") String code) {
        boolean success = service.verify(code);
        model.addAttribute("pageTitle", "Verification " + (success ? "Successful" : "Failed"));
        return "register/" + (success ? "verify_success" : "verify_fail");
    }

    @GetMapping("/account_details")
    public String showAccountDetails(Model model, HttpServletRequest request) {
        String email = getCurrentCustomerEmail(request);
        Customer customer = service.findByEmail(email);
        List<Country> countries = countryService.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("countries", countries);
        return "customer/account_form";
    }

    @PostMapping("/account_details")
    public String updateAccountDetails(Customer customer, RedirectAttributes ra, HttpServletRequest request) {
        service.update(customer);
        updateNameForAuthenticatedCustomer(customer, request);
        ra.addFlashAttribute("message", "Account details updated successfully");
        return "redirect:/account_details";
    }

    private void updateNameForAuthenticatedCustomer(Customer customer, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        if(principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) principal;
            CustomerOAuth2User user = (CustomerOAuth2User) oauth2Token.getPrincipal();
            user.setFullName(customer.getFirstName() + " " + customer.getLastName());
        } else if(principal instanceof UsernamePasswordAuthenticationToken) {
            CustomerUserDetails user = getCustomerUserDetails( principal);
            if(user != null) {
                Customer customerAuthenticated = user.getCustomer();
                customerAuthenticated.setFirstName(customer.getFirstName());
                customerAuthenticated.setLastName(customer.getLastName());
            }
        }
    }

    private CustomerUserDetails getCustomerUserDetails(Principal principal) {
        if(principal instanceof UsernamePasswordAuthenticationToken) {
            return (CustomerUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        } else if (principal instanceof RememberMeAuthenticationToken) {
            return (CustomerUserDetails) ((RememberMeAuthenticationToken) principal).getPrincipal();
        }
        return null;
    }

    private String getCurrentCustomerEmail(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String customerEmail = null;

        if (principal instanceof UsernamePasswordAuthenticationToken
                || principal instanceof RememberMeAuthenticationToken) {
            customerEmail = principal.getName();
        } else if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) principal;
            CustomerOAuth2User oauth2User = (CustomerOAuth2User) oauth2Token.getPrincipal();
            customerEmail = oauth2User.getEmail();
        }

        return customerEmail;
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
