package it.vincenzopio.minestore.api.settings;

import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.service.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

public class SettingsService extends Service {

    private static final Yaml YAML = new Yaml();

    private PluginSettings pluginSettings;

    public SettingsService(MineStore<?, ?> mineStore) {
        super(mineStore);
    }

    @Override
    protected void onLoad() {
        File dataFolder = mineStore.getDataFolder();

        File configFile = new File(dataFolder, "config.yml");

        try {
            if (!configFile.exists()) {
                MineStore.LOGGER.info("No config file found, copying default.");
                Files.copy(mineStore.getResource("config.yml"), dataFolder.toPath());
                MineStore.LOGGER.info("Please setup your configuration file, then restart!");
                mineStore.forceShutdown();

                return;
            }

            pluginSettings = YAML.load(new FileInputStream(configFile));
        } catch (Exception e) {

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
