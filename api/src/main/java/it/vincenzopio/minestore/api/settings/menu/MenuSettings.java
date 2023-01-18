package it.vincenzopio.minestore.api.settings.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuDefaultSettings;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuFormatSettings;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuPageSettings;

public class MenuSettings {

    @JsonProperty("allow-menu")
    private boolean allowMenu;

    @JsonProperty("disallow-message")
    private String disallowMessage;

    @JsonProperty("message")
    private String buyMessage;

    @JsonProperty("api-auth")
    private boolean authRequired;

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    @JsonProperty("default")
    private MenuDefaultSettings baseValues;

    @JsonProperty("format")
    private MenuFormatSettings formatSettings;

    @JsonProperty("pagination")
    private MenuPageSettings paginationSettings;


    public String getBuyMessage() {
        return buyMessage;
    }

    public boolean isAllowMenu() {
        return allowMenu;
    }

    public boolean isAuthRequired() {
        return authRequired;
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

    public MenuPageSettings getPaginationSettings() {
        return paginationSettings;
    }

    public MenuDefaultSettings getBaseValues() {
        return baseValues;
    }

    public MenuFormatSettings getFormatSettings() {
        return formatSettings;
    }
}
