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

        String clientName = oauth2User.getClientName();
        String name = oauth2User.getName();
        String email = oauth2User.getEmail();

        Customer customer = customerService.findByEmail(email);
        AuthenticationType authenticationType = getAuthenticationType(clientName);
        if (customer == null) {
            customerService.addNewCustomerUponOAuthLogin(name, email, authenticationType);
        } else {
            oauth2User.setFullName(customer.getFullName());
            customerService.updateAuthenticationType(customer, authenticationType);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private AuthenticationType getAuthenticationType(String clientName) {
        switch (clientName) {
            case "Facebook":
                return AuthenticationType.FACEBOOK;
            case "Google":
                return AuthenticationType.GOOGLE;
            default:
                return AuthenticationType.DATABASE;
        }
    }
}
