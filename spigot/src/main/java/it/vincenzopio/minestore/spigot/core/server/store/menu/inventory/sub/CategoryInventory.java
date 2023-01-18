package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.sub;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuFormatSettings;
import it.vincenzopio.minestore.spigot.core.server.store.menu.MenuService;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.CategoryItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages.PackageItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages.SubCategoryItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.ItemBuilder;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.MessageFormatter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CategoryInventory implements InventoryProvider {

    private final MenuService menuService;
    private final MenuFormatSettings menuSettings;
    private final CategoryItem categoryItem;

    private final Material defaultMaterial;

    public CategoryInventory(MenuService menuService, CategoryItem categoryItem) {
        this.menuService = menuService;
        this.menuSettings = menuService.getMenuSettings().getFormatSettings();
        this.defaultMaterial = Objects.requireNonNullElse(Material.valueOf(menuService.getMenuSettings().getBaseValues().getMaterial()), Material.CHEST);
        this.categoryItem = categoryItem;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        List<ClickableItem> clickableItems = new ArrayList<>();

        for (SubCategoryItem subCategoryItem : categoryItem.getSubCategoryItems()) {
            ItemBuilder itemBuilder = ItemBuilder.createItem(Objects.requireNonNullElse(Material.getMaterial(subCategoryItem.getItemMaterial()), defaultMaterial))
                    .setName(MessageFormatter.formatter(menuSettings.getName())
                            .ampersand()
                            .format(subCategoryItem)
                            .toString())
                    .setLore(MessageFormatter.formatter(menuSettings.getDescription()).format(subCategoryItem).ampersand().toString());


            clickableItems.add(ClickableItem.of(itemBuilder.get(), event -> {
                List<PackageItem> packageItems = Arrays.stream(categoryItem.getPackageItems())
                        .filter(packageItem -> packageItem.getURL().equalsIgnoreCase(subCategoryItem.getURL()))
                        .toList();

                // Packages Inventory

            }));

        }

        pagination.setItemsPerPage(21);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1).blacklist(3, 8));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
