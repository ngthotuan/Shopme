package com.shopme.admin.setting;

import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Currency;
import com.shopme.common.entity.Setting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingController {
    private final SettingService service;
    private final CurrencyService currencyService;

    @GetMapping("")
    public String listAll(Model model) {
        List<Setting> settings = service.listAllSetting();
        settings.forEach(setting -> {
            model.addAttribute(setting.getKey(), setting.getValue());
        });
        model.addAttribute("currencies", currencyService.listAllByName());
        return "settings/settings";
    }

    @PostMapping("save_general")
    public String saveGeneralSettings(@RequestParam(value = "fileImage", required = false) MultipartFile fileImage,
                                      HttpServletRequest request, RedirectAttributes ra) throws IOException {
        GeneralSettingBag generalSettingBag = service.getGeneralSettings();
        updateSiteImage(generalSettingBag, fileImage);
        updateSiteCurrencies(generalSettingBag, request);
        saveSettingsInForm(request, generalSettingBag.list());
        ra.addFlashAttribute("message", "General settings saved successfully");
        return "redirect:/settings";
    }

    @PostMapping("save_mail_server")
    public String saveMailSettings(HttpServletRequest request, RedirectAttributes ra) {
        saveSettingsInForm(request, service.getMailServerSettings());
        ra.addFlashAttribute("message", "Mail settings saved successfully");
        return "redirect:/settings";
    }

    @PostMapping("save_mail_templates")
    public String saveMailTemplates(HttpServletRequest request, RedirectAttributes ra) {
        saveSettingsInForm(request, service.getMailTemplatesSettings());
        ra.addFlashAttribute("message", "Mail templates saved successfully");
        return "redirect:/settings";
    }

    private void saveSettingsInForm(HttpServletRequest request, List<Setting> settings) {
        settings.forEach(setting -> {
            String value = request.getParameter(setting.getKey());
            if (value != null) {
                setting.setValue(value);
            }
        });
        service.saveAll(settings);
    }

    private void updateSiteCurrencies(GeneralSettingBag generalSettingBag, HttpServletRequest request) {
        try {
            long currencyId = Long.parseLong(request.getParameter("CURRENCY_ID"));
            Currency currency = currencyService.findById(currencyId);
            generalSettingBag.updateCurrencySymbol(currency.getSymbol());
        } catch (NumberFormatException | NullPointerException ignored) {
        }
    }

    private void updateSiteImage(GeneralSettingBag generalSettingBag, MultipartFile fileImage) throws IOException {
        if (fileImage.getSize() > 0) {
            String fileName = StringUtils.cleanPath(fileImage.getOriginalFilename());
            String value = "/site-logo/" + fileName;
            generalSettingBag.updateSiteLogo(value);

            String uploadDir = "site-logo";
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, fileImage);
        }
    }
}