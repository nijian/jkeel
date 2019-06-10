package com.github.nijian.jkeel.i18n;

import com.github.nijian.jkeel.commons.feature.FeatureParam;

import java.util.Locale;

public class I18nFeatureParam implements FeatureParam {

    private String key;

    private String code;

    private Locale locale;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
