package com.shopme.security.oauth;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Lazy
    private final CustomerService customerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomerOAuth2User oauth2User = (CustomerOAuth2User) authentication.getPrincipal();

        String name = oauth2User.getName();
        String email = oauth2User.getEmail();
        String countryCode = request.getLocale().getCountry();

        Customer customer = customerService.findByEmail(email);
        if (customer == null) {
            customerService.addNewCustomerUponOAuthLogin(name, email);
        } else {
            customerService.updateAuthenticationType(customer, AuthenticationType.GOOGLE);
        }

        System.out.println("OAuth2LoginSuccessHandler: " + name + " | " + email + " | " + countryCode);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
