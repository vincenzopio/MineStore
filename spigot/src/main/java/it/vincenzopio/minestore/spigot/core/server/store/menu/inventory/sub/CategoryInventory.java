package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.sub;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuFormatSettings;
import it.vincenzopio.minestore.spigot.core.server.store.menu.MenuService;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.StoreInventory;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.CategoryItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages.PackageItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages.SubCategoryItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.ItemBuilder;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.MaterialUtils;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.MessageFormatter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryInventory implements StoreInventory {


    private final SmartInventory comingFrom;
    private final MenuService menuService;
    private final MenuFormatSettings menuSettings;
    private final CategoryItem categoryItem;

    private final Material defaultMaterial;

    public CategoryInventory(SmartInventory comingFrom, MenuService menuService, CategoryItem categoryItem) {
        this.comingFrom = comingFrom;
        this.menuService = menuService;
        this.menuSettings = menuService.getMenuSettings().getFormatSettings();
        this.defaultMaterial = Optional.of(Material.valueOf(menuService.getMenuSettings().getBaseValues().getMaterial())).orElse(Material.CHEST);
        this.categoryItem = categoryItem;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        List<ClickableItem> clickableItems = new ArrayList<>();

        for (SubCategoryItem subCategoryItem : categoryItem.getSubCategoryItems()) {
            ItemBuilder itemBuilder = ItemBuilder.createItem(MaterialUtils.getSafeMaterial(subCategoryItem.getItemMaterial(), defaultMaterial))
                    .setName(MessageFormatter.formatter(menuSettings.getName())
                            .ampersand()
                            .format(subCategoryItem)
                            .toString());

            clickableItems.add(ClickableItem.of(itemBuilder.get(), event -> {
                List<PackageItem> packageItems = Arrays.stream(categoryItem.getPackageItems())
                        .filter(packageItem -> packageItem.getURL().equalsIgnoreCase(subCategoryItem.getURL()))
                        .collect(Collectors.toList());

                new PackagesInventory(getInventory(), menuService, packageItems).getInventory().open(player);
            }));

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
                .get(), event -> {
            if (comingFrom == null) {
                player.closeInventory();
                return;
            }
            comingFrom.open(player);
        }));

    }

    @Override
    public SmartInventory getInventory() {
        return SmartInventory.builder()
                .provider(this)
                .id("store_packages")
                .manager(menuService.getInventoryManager())
                .size(3, 9)
                .title(ChatColor.DARK_GRAY + MessageFormatter.formatter(menuService.getMenuSettings().getName(), true).toString())
                .build();
    }
}
