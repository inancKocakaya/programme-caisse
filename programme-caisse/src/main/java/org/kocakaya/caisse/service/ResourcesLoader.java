package org.kocakaya.caisse.service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourcesLoader {

    private static ResourceBundle resourceBundle;
    private static ResourcesLoader instance = null;
    private static final String RESOURCE_NAME = "org/kocakaya/caisse/i18n/resources";
    private static final Locale DEFAULT_LOCALE = new Locale("fr");

    private ResourcesLoader() {
	setResourceBundleLocale(DEFAULT_LOCALE);
    }

    public static ResourcesLoader getInstance() {
	if (instance == null)
	    instance = new ResourcesLoader();
	return instance;
    }

    public void setResourceBundleLocale(Locale locale) {
	resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }

    public ResourceBundle getResourceBundle() {
	return resourceBundle;
    }

    public String getFormatedMessage(String key, Object[] arguments) {
	MessageFormat formatter = new MessageFormat("");
	formatter.applyPattern(resourceBundle.getString(key));
	return formatter.format(arguments);
    }
}