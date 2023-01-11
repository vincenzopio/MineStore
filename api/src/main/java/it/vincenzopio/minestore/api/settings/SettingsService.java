package it.vincenzopio.minestore.api.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.service.Service;

import java.io.File;
import java.nio.file.Files;

public class SettingsService extends Service {

    public static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    private PluginSettings pluginSettings;

    public SettingsService(MineStore<?, ?> mineStore) {
        super(mineStore);
    }

    @Override
    protected void onLoad() {
        File dataFolder = mineStore.getDataFolder();

        if (!dataFolder.exists())
            dataFolder.mkdirs();


        File configFile = new File(dataFolder, "config.yml");

        try {
            if (!configFile.exists()) {
                MineStore.LOGGER.info("No config file found, copying default.");
                Files.copy(mineStore.getResource("config.yml"), configFile.toPath());
                MineStore.LOGGER.info("Please setup your configuration file, then restart!");
                System.exit(0);
                return;
            }

            MineStore.LOGGER.info("Loading configuration file...");

            if (configFile.length() > 0)
                pluginSettings = MAPPER.readValue(configFile, PluginSettings.class);

            MineStore.LOGGER.info("OOO: " + pluginSettings.getConnectionSettings().getConnectionMode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onUnload() {
        // Do nothing.
    }

    public PluginSettings getPluginSettings() {
        return pluginSettings;
    }
}
