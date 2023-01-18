package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory;

import com.google.common.collect.Lists;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuFormatSettings;
import it.vincenzopio.minestore.spigot.core.server.store.menu.MenuService;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.CategoryItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.sub.CategoryInventory;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.sub.PackagesInventory;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.ItemBuilder;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.MaterialUtils;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.MessageFormatter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MenuInventory implements StoreInventory {

    private final Material defaultMaterial;
    private final MenuService menuService;
    private final MenuFormatSettings menuSettings;
    private final List<CategoryItem> categoryItems;


    public MenuInventory(MenuService menuService) {
        this.menuService = menuService;
        this.defaultMaterial = Optional.of(Material.valueOf(menuService.getMenuSettings().getBaseValues().getMaterial())).orElse(Material.CHEST);
        this.menuSettings = menuService.getMenuSettings().getFormatSettings();
        this.categoryItems = Lists.newArrayList(menuService.getCategoryItems());
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        List<ClickableItem> clickableItems = new ArrayList<>();

        categoryItems.sort(Comparator.comparingInt(CategoryItem::getId));

        for (CategoryItem categoryItem : categoryItems) {

            ItemBuilder itemBuilder = ItemBuilder.createItem(MaterialUtils.getSafeMaterial(categoryItem.getItemMaterial(), defaultMaterial))
                    .setName(MessageFormatter.formatter(menuSettings.getName())
                            .ampersand()
                            .format(categoryItem)
                            .toString());

            clickableItems.add(ClickableItem.of(itemBuilder.get(),
                    event -> {
                        if (categoryItem.getSubCategoryItems().length > 0) {
                            new CategoryInventory(getInventory(), menuService, categoryItem).getInventory().open(player);
                            return;
                        }

                        new PackagesInventory(getInventory(), menuService, Lists.newArrayList(categoryItem.getPackageItems())).getInventory().open(player);
                    }
            ));

        }

        pagination.setItems(clickableItems.toArray(new ClickableItem[0]))
                .setItemsPerPage(18)
                .addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));

        //Go previous page
        if (!pagination.isFirst()) {
            contents.set(5, 0, ClickableItem.of(new ItemBuilder(Material.ARROW)
                    .setName(MessageFormatter.formatter(menuService.getMenuSettings().getPaginationSettings().getPreviousPageMessage(), true).pagination(pagination).toString())
                    .get(), event -> getInventory().open(player, pagination.getPage() - 1)));
        }


        //Go next page
        if (!pagination.isLast())
            contents.set(5, 8, ClickableItem.of(new ItemBuilder(Material.ARROW)
                    .setName(MessageFormatter.formatter(menuService.getMenuSettings().getPaginationSettings().getNextPageMessage(), true).pagination(pagination).toString())
                    .get(), event -> getInventory().open(player, pagination.getPage() + 1)));

        addBackItem(contents, ClickableItem.of(new ItemBuilder(Material.ARROW)
                .setName(MessageFormatter.formatter(menuService.getMenuSettings().getPaginationSettings().getBackMessage(), true).pagination(pagination).toString())
                .get(), event -> player.closeInventory()));

    }

    @Override
    public SmartInventory getInventory() {
        return SmartInventory.builder()
                .provider(this)
                .id("store_menu")
                .manager(menuService.getInventoryManager())
                .size(3, 9)
                .title(ChatColor.DARK_GRAY + MessageFormatter.formatter(menuService.getMenuSettings().getName(), true).toString())
                .build();
    }
}
