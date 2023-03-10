package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.MenuItem;

public class SubCategoryItem implements MenuItem {

    private String name;
    private String url;
    @JsonProperty("gui_item_id")
    private String itemMaterial;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getURL() {
        return url;
    }

    public String getItemMaterial() {
        return itemMaterial;
    }

    @Override
    public ItemType getType() {
        return ItemType.SUB_CATEGORY;
    }
}
