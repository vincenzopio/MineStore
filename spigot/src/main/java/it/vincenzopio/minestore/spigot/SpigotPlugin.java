package it.vincenzopio.minestore.spigot;

import it.vincenzopio.minestore.spigot.core.MineStoreSpigot;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlugin extends JavaPlugin {

    private final MineStoreSpigot mineStoreSpigot;

    public SpigotPlugin() {
        this.mineStoreSpigot = new MineStoreSpigot(this);
    }

    @Override
    public void onEnable() {
        mineStoreSpigot.load();
    }

    @Override
    public void onDisable() {
        mineStoreSpigot.disable();
    }
}
