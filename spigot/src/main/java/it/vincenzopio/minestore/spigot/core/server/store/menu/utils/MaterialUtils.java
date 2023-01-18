package it.vincenzopio.minestore.spigot.core.server.store.menu.utils;

import org.bukkit.Material;

import java.util.Optional;

public class MaterialUtils {

    private MaterialUtils() {
    }

    public static Material getSafeMaterial(String name, Material defaultMaterial) {
        try {
            return Optional.ofNullable(Material.matchMaterial(name)).orElse(defaultMaterial);
        } catch (Exception e) {
            return defaultMaterial;
        }
    }

    public static Material parseMaterial(String name, Material defaultMaterial) {
        try {
            return Material.valueOf(name);
        } catch (Exception e) {
            return defaultMaterial;
        }
    }
}
