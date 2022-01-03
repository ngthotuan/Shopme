package com.shopme.utils;

import com.shopme.setting.EmailSettingBag;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

public class Utility {
    public static String getSiteUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }

    public static JavaMailSenderImpl prepareMailSender(EmailSettingBag settingBag) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(settingBag.getHost());
        mailSender.setPort(settingBag.getPort());
        mailSender.setUsername(settingBag.getUsername());
        mailSender.setPassword(settingBag.getPassword());
        mailSender.setDefaultEncoding("UTF-8");

        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", settingBag.getSmtpAuth());
        mailProperties.setProperty("mail.smtp.starttls.enable", settingBag.getSmtpSecured());
        mailSender.setJavaMailProperties(mailProperties);

        return mailSender;
    }
}
