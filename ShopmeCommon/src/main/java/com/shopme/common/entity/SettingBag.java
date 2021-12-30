package com.shopme.common.entity;

import java.util.List;

public class SettingBag {
    private final List<Setting> settings;

    public SettingBag(List<Setting> settings) {
        this.settings = settings;
    }

    public Setting get(String key) {
        int index = settings.indexOf(new Setting(key));
        if (index >= 0) {
            return settings.get(index);
        }

        return null;
    }

    public String getValue(String key) {
        Setting setting = get(key);
        if (setting != null) {
            return setting.getValue();
        }

        return null;
    }

    public void update(String key, String value) {
        Setting setting = get(key);
        if (setting != null && value != null) {
            setting.setValue(value);
        }
    }

    public List<Setting> list() {
        return settings;
    }

}

