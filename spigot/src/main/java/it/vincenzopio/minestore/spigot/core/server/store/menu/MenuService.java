package it.vincenzopio.minestore.spigot.core.server.store.menu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import fr.minuskube.inv.InventoryManager;
import it.vincenzopio.minestore.api.service.Service;
import it.vincenzopio.minestore.api.settings.menu.MenuSettings;
import it.vincenzopio.minestore.api.settings.store.StoreSettings;
import it.vincenzopio.minestore.spigot.core.MineStoreSpigot;
import it.vincenzopio.minestore.spigot.core.server.store.menu.command.StoreCommand;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.CategoryItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.utils.MessageFormatter;
import org.bukkit.entity.Player;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuService extends Service {

    private final List<CategoryItem> categoryItems = new CopyOnWriteArrayList<>();

    private final MineStoreSpigot mineStoreSpigot;
    private final MenuSettings menuSettings;

    private InventoryManager inventoryManager;


    public MenuService(MineStoreSpigot mineStore) {
        super(mineStore);

        this.mineStoreSpigot = mineStore;
        this.menuSettings = mineStore.getSettingsService().getMenuSettings();
    }


    public void processCommand(Player player){
        if(!menuSettings.isAllowMenu()){
            player.sendMessage(MessageFormatter.formatter(menuSettings.getDisallowMessage(), true).toString());
            return;
        }


    }

    @Override
    protected void onLoad() {
        inventoryManager = new InventoryManager(mineStoreSpigot.getPluginInstance());
        inventoryManager.init();

        mineStoreSpigot.getServer()
                .getPluginCommand("store")
                .setExecutor(new StoreCommand(this));

        mineStore.getTaskScheduler().asyncTimer(() -> {
            MineStoreSpigot.LOGGER.info("Updating packages...");

            StoreSettings storeSettings = mineStoreSpigot.getSettingsService().getPluginSettings().getStoreSettings();

            try{
                URL url = new URL(storeSettings.getApiAddress() + "api/" + storeSettings.getApiKey() + "/packages_new");

                ObjectMapper objectMapper = new ObjectMapper();
                TypeReference<List<CategoryItem>> type = new TypeReference<>() {};

                List<CategoryItem> updated  = objectMapper.readValue(url, type);

                categoryItems.clear();
                categoryItems.addAll(updated);
            }catch (Exception e){
                e.printStackTrace();
            }

        }, 1000, 2);
    }

    @Override
    protected void onUnload() {

    }

    public List<CategoryItem> getCategoryItems() {
        return ImmutableList.copyOf(categoryItems);
    }
}
