package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;

public interface StoreInventory extends InventoryProvider {

    @Override
    default void update(Player player, InventoryContents contents) {
    }

    default void addBackItem(InventoryContents contents, ClickableItem clickableItem) {
        contents.set(contents.inventory().getRows() - 1, 4, clickableItem);
    }

    SmartInventory getInventory();
}
