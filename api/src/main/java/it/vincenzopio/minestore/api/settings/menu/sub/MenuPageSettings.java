package it.vincenzopio.minestore.api.settings.menu.sub;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuPageSettings {

    @JsonProperty("back-item")
    private String backMessage;
    @JsonProperty("next-page-item")
    private String nextPageMessage;
    @JsonProperty("previous-page-item")
    private String previousPageMessage;

    public String getBackMessage() {
        return backMessage;
    }

    public String getNextPageMessage() {
        return nextPageMessage;
    }

    public String getPreviousPageMessage() {
        return previousPageMessage;
    }
}
