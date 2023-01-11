package it.vincenzopio.minestore.spigot.core.server.user;

import it.vincenzopio.minestore.api.server.user.PlayerResolver;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlayerResolver implements PlayerResolver {

    private final BukkitAudiences audience;
    private final Server server;

    public SpigotPlayerResolver(JavaPlugin javaPlugin) {
        this.audience = BukkitAudiences.builder(javaPlugin).build();
        this.server = javaPlugin.getServer();
    }


    @Override
    public boolean isOnline(String username) {
        return server.getPlayerExact(username) != null;
    }

    @Override
    public void sendChatMessage(String username, Component component) {
        Player player = server.getPlayerExact(username);

        if (player == null) return;

        audience.player(player).sendMessage(component);
    }
}
