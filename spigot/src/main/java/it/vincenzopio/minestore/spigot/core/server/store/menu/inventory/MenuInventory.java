package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory;

import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import it.vincenzopio.minestore.spigot.core.server.store.menu.MenuService;
import org.bukkit.entity.Player;

public class MenuInventory implements InventoryProvider {

    private final MenuService menuService;

    public MenuInventory(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void init(Player player, InventoryContents contents) {

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
