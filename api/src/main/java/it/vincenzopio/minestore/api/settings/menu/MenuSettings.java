package it.vincenzopio.minestore.api.settings.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuDefaultSettings;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuFormatSettings;

public class MenuSettings {

    @JsonProperty("allow-menu")
    private boolean allowMenu;

    @JsonProperty("disallow-message")
    private String disallowMessage;

    private String name;

    private String url;

    private String message;

    @JsonProperty("default")
    private MenuDefaultSettings baseValues;

    @JsonProperty("format")
    private MenuFormatSettings formatSettings;


    public boolean isAllowMenu() {
        return allowMenu;
    }

    public String getDisallowMessage() {
        return disallowMessage;
    }

    public String getName() {
        return name;
    }

    public String getBaseURL() {
        return url;
    }

    public MenuDefaultSettings getBaseValues() {
        return baseValues;
    }

    public MenuFormatSettings getFormatSettings() {
        return formatSettings;
    }
}
