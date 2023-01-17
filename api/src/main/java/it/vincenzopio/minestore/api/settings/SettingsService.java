package it.vincenzopio.minestore.api.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.service.Service;
import it.vincenzopio.minestore.api.settings.menu.MenuSettings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public class SettingsService extends Service {

    public static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    private PluginSettings pluginSettings;
    private MenuSettings menuSettings;

    public SettingsService(MineStore<?, ?> mineStore) {
        super(mineStore);
    }

    @Override
    protected void onLoad() {
        File dataFolder = mineStore.getDataFolder();

        if (!dataFolder.exists())
            dataFolder.mkdirs();

        try {
            pluginSettings = readFromFile(dataFolder, "config", PluginSettings.class, true);

            if(mineStore.getPlatform().hasMenuSupport())
                menuSettings = readFromFile(dataFolder, "config", MenuSettings.class, true);

        }catch (Exception e){
            MineStore.LOGGER.log(Level.SEVERE, "Could not load configuration file, shutting down.", e);
            mineStore.forceShutdown();
        }
    }

    protected <T> T readFromFile(File dataFolder, String configName, Class<T> objectClass, boolean shutdown) throws IOException {
        File configFile = new File(dataFolder, configName + ".yml");

        if (!configFile.exists()) {
            MineStore.LOGGER.info("No config file found, copying default.");
            Files.copy(mineStore.getResource(configName + ".yml"), configFile.toPath());

            if (shutdown) {
                MineStore.LOGGER.info("Please setup your configuration file, then restart!");
                System.exit(0);
            }

            return null;
        }

        MineStore.LOGGER.info("Loading configuration file...");

        return MAPPER.readValue(configFile, objectClass);
    }

    @Override
    protected void onUnload() {
        // Do nothing.
    }

    public PluginSettings getPluginSettings() {
        return pluginSettings;
    }

    public MenuSettings getMenuSettings() {
        return menuSettings;
    }
}
