package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuFormatSettings;
import it.vincenzopio.minestore.spigot.core.server.store.menu.MenuService;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.CategoryItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.ItemBuilder;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.MessageFormatter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MenuInventory implements StoreInventory {

    private final MenuService menuService;
    private final MenuFormatSettings menuSettings;
    private final List<CategoryItem> categoryItems;


    public MenuInventory(MenuService menuService) {
        this.menuService = menuService;
        this.menuSettings = menuService.getMenuSettings().getFormatSettings();
        this.categoryItems = menuService.getCategoryItems();
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        Pagination pagination = contents.pagination();

        List<ClickableItem> clickableItems = new ArrayList<>();
        for (CategoryItem categoryItem : categoryItems) {
            List<String> lore = new ArrayList<>();

            lore.add(MessageFormatter.formatter(menuSettings.getDescription())
                    .format(categoryItem)
                    .ampersand()
                    .toString());

            if (categoryItem.hasPrice()) {
                lore.add("");
                lore.add(MessageFormatter.formatter(menuSettings.getPrice())
                        .format(categoryItem)
                        .ampersand()
                        .toString());
            }


            ItemBuilder itemBuilder = ItemBuilder.createItem(Material.valueOf(categoryItem.getItemMaterial()))
                    .setName(MessageFormatter.formatter(menuSettings.getName())
                            .ampersand()
                            .format(categoryItem)
                            .toString())
                    .setLore(lore);


            clickableItems.add(categoryItem.getId(), ClickableItem.of(
                    itemBuilder.get(),
                    event -> {

                        if (categoryItem.getSubCategoryItems().length > 0) {
                            // Category Inventory
                            return;
                        }

                        // Packages Inventory
                    }
            ));

        }

        pagination.setItemsPerPage(21);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1)
                .blacklist(3, 8));

    }

    @Override
    public SmartInventory getInventory() {
        return SmartInventory.builder()
                .id("menu_store")
                .manager(menuService.getInventoryManager())
                .size(3, 9)
                .title(MessageFormatter.formatter(menuSettings.getName(), true).toString())
                .build();
    }
}
