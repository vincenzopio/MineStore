package it.vincenzopio.minestore.spigot.core.server.store.menu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import fr.minuskube.inv.InventoryManager;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.service.Service;
import it.vincenzopio.minestore.api.settings.menu.MenuSettings;
import it.vincenzopio.minestore.api.settings.store.StoreSettings;
import it.vincenzopio.minestore.spigot.core.MineStoreSpigot;
import it.vincenzopio.minestore.spigot.core.server.store.menu.command.StoreCommand;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.MenuInventory;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.CategoryItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.MessageFormatter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

public class MenuService extends Service {

    private final List<CategoryItem> categoryItems = new CopyOnWriteArrayList<>();

    private final MineStoreSpigot mineStoreSpigot;
    private final MenuSettings menuSettings;
    private InventoryManager inventoryManager;

    private boolean updating = false;


    public MenuService(MineStoreSpigot mineStore) {
        super(mineStore);

        this.mineStoreSpigot = mineStore;
        this.menuSettings = mineStore.getSettingsService().getMenuSettings();
    }


    public void processCommand(Player player) {
        if (!menuSettings.isAllowMenu()) {
            player.sendMessage(MessageFormatter.formatter(menuSettings.getDisallowMessage(), true).toString());
            return;
        }

        if (updating) {
            player.sendMessage(ChatColor.RED + "We are updating the packages, wait a few seconds..");
            return;
        }

        if (categoryItems.isEmpty()) {
            player.sendMessage(ChatColor.RED + "No packages to show!");
            return;
        }

        new MenuInventory(this).getInventory().open(player);
    }

    @Override
    protected void onLoad() {
        mineStoreSpigot.getServer()
                .getPluginCommand("store")
                .setExecutor(new StoreCommand(this));

        if (!menuSettings.isAllowMenu()) {
            return;
        }

        inventoryManager = new InventoryManager(mineStoreSpigot.getPluginInstance());
        inventoryManager.init();


        mineStore.getTaskScheduler().asyncTimer(() -> {
            MineStore.LOGGER.info("Updating packages...");

            updating = true;

            StoreSettings storeSettings = mineStoreSpigot.getSettingsService().getPluginSettings().getStoreSettings();

            try {
                URL url = new URL(mineStore.getSettingsService().getPluginSettings().getStoreSettings().getApiAddress() + (menuSettings.isAuthRequired() ? storeSettings.getApiKey() + "/" : "") + "gui/packages_new");

                MineStore.LOGGER.info("Fetching packages from Store: " + url.getPath());

                ObjectMapper objectMapper = new ObjectMapper();
                TypeReference<List<CategoryItem>> type = new TypeReference<List<CategoryItem>>() {
                };

                List<CategoryItem> updated = objectMapper.readValue(url, type);

                categoryItems.clear();
                categoryItems.addAll(updated);
            } catch (Exception e) {
                MineStore.LOGGER.log(Level.SEVERE, "An error occurred while loading packages from the store: ", e);
            }

            updating = false;
        }, 1000, 1);
    }

    @Override
    protected void onUnload() {
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public MenuSettings getMenuSettings() {
        return menuSettings;
    }

    public List<CategoryItem> getCategoryItems() {
        return ImmutableList.copyOf(categoryItems);
    }
}
