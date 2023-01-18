package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.sub;

import com.google.common.collect.Lists;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import it.vincenzopio.minestore.api.settings.menu.sub.MenuFormatSettings;
import it.vincenzopio.minestore.spigot.core.server.store.menu.MenuService;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.StoreInventory;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages.PackageItem;
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

public class PackagesInventory implements StoreInventory {

    private final SmartInventory comingFrom;
    private final Material defaultMaterial;
    private final MenuService menuService;
    private final MenuFormatSettings formatSettings;
    private final List<PackageItem> packages;

    public PackagesInventory(SmartInventory comingFrom, MenuService menuService, List<PackageItem> packages) {
        this.comingFrom = comingFrom;
        this.defaultMaterial = Optional.of(Material.valueOf(menuService.getMenuSettings().getBaseValues().getMaterial())).orElse(Material.CHEST);
        this.menuService = menuService;
        this.formatSettings = menuService.getMenuSettings().getFormatSettings();
        this.packages = Lists.newArrayList(packages);
    }


    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        List<ClickableItem> clickableItems = new ArrayList<>();

        packages.sort(Comparator.comparingInt(PackageItem::getSort));

        for (PackageItem packageItem : packages) {
            if (!packageItem.isActive()) continue;

            ItemBuilder itemBuilder = ItemBuilder.createItem(MaterialUtils.getSafeMaterial(packageItem.getItemMaterial(), defaultMaterial))
                    .setName(MessageFormatter.formatter(packageItem.getName())
                            .ampersand()
                            .format(packageItem).toString())
                    .setLore(MessageFormatter.formatter(packageItem.getItemLore()).ampersand().description(packageItem).format(packageItem).toString(),
                            "",
                            MessageFormatter.formatter(formatSettings.getPrice()).ampersand().format(packageItem).toString());


            clickableItems.add(ClickableItem.of(itemBuilder.get(), event -> player.sendMessage(MessageFormatter.formatter(menuService.getMenuSettings().getBuyMessage())
                    .ampersand()
                    .format(packageItem)
                    .url(packageItem, menuService.getMenuSettings().getBaseURL() + "category/")
                    .toString())));
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
