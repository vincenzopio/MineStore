package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.MenuItem;

public class PackageItem implements MenuItem {

    private String name;
    private int price;
    private int discount;
    @JsonProperty("sorting")
    private int sort;
    @JsonProperty("category_url")
    private String url;
    private int featured;
    private int active;
    @JsonProperty("item_id")
    private String itemMaterial;
    @JsonProperty("item_lore")
    private String itemLore;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getURL() {
        return url;
    }


    public int getDiscount() {
        return discount;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public int getSort() {
        return sort;
    }

    public String getItemLore() {
        return itemLore;
    }

    public String getItemMaterial() {
        return itemMaterial;
    }

    public boolean isActive() {
        return active != 0;
    }

    public boolean isFeatured() {
        return featured != 0;
    }

    @Override
    public boolean hasPrice() {
        return true;
    }

    @Override
    public ItemType getType() {
        return ItemType.PACKAGE;
    }

}
