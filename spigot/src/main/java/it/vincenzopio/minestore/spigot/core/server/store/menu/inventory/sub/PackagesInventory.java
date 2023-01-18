package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.sub;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuFormatSettings;
import it.vincenzopio.minestore.spigot.core.server.store.menu.MenuService;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages.PackageItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.ItemBuilder;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.MessageFormatter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PackagesInventory implements InventoryProvider {

    private final MenuService menuService;
    private final MenuFormatSettings menuSettings;

    private final List<PackageItem> packages;

    public PackagesInventory(MenuService menuService, MenuFormatSettings menuSettings, List<PackageItem> packages) {
        this.menuService = menuService;
        this.menuSettings = menuSettings;
        this.packages = packages;
    }


    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        List<ClickableItem> clickableItems = new ArrayList<>();

        for (PackageItem packageItem : packages) {
            ItemBuilder itemBuilder = ItemBuilder.createItem(Material.getMaterial(packageItem.getItemMaterial()))
                    .setName(MessageFormatter.formatter(packageItem.getName())
                            .ampersand()
                            .format(packageItem).toString())
                    .setLore(MessageFormatter.formatter(packageItem.getItemLore()).ampersand().format(packageItem).toString(),
                            "",
                            MessageFormatter.formatter(menuSettings.getPrice()).ampersand().format(packageItem).toString());


            clickableItems.add(packageItem.getSort(), ClickableItem.of(itemBuilder.get(), event -> player.sendMessage(MessageFormatter.formatter(menuSettings.getDescription())
                    .ampersand()
                    .format(packageItem)
                    .url(packageItem, menuService.getMenuSettings().getBaseURL())
                    .toString())));
        }

        pagination.setItems(clickableItems.toArray(new ClickableItem[]{}));
        pagination.setItemsPerPage(21);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1).blacklist(3, 8));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
