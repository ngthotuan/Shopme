package com.shopme.setting;

import com.shopme.common.entity.Setting;
import com.shopme.common.entity.SettingCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository repo;

    public List<Setting> getGeneralSettings() {
        return repo.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
    }

    public EmailSettingBag getEmailSettings() {
        List<Setting> settings = repo.findByTwoCategories(SettingCategory.MAIL_SERVER, SettingCategory.MAIL_TEMPLATES);

        return new EmailSettingBag(settings);
    }

}
