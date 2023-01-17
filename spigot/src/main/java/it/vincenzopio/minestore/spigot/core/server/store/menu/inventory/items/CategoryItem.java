package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items;

import com.google.gson.annotations.SerializedName;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages.PackageItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages.SubCategoryItem;

public class CategoryItem implements MenuItem {

    private int id;

    private String name;

    private String url;

    @SerializedName("gui_item_id")
    private String itemMaterial;

    @SerializedName("subcategories")
    private SubCategoryItem[] subCategoryItems;

    @SerializedName("packages")
    private PackageItem[] packageItems;


    public int getId() {
        return id;
    }

    public String getItemMaterial() {
        return itemMaterial;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getURL() {
        return url;
    }


    public SubCategoryItem[] getSubCategoryItems() {
        return subCategoryItems;
    }

    public PackageItem[] getPackageItems() {
        return packageItems;
    }

    @Override
    public ItemType getType() {
        return ItemType.CATEGORY;
    }
}

