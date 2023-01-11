package it.vincenzopio.minestore.spigot.core.server.command;

import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.server.command.CommandService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpigotCommandService extends CommandService implements Listener {

    private final Map<String, List<String>> playerCommands = new HashMap<>();

    private final JavaPlugin javaPlugin;


    public SpigotCommandService(JavaPlugin javaPlugin, MineStore<?, ?> mineStore) {
        super(mineStore);

        this.javaPlugin = javaPlugin;
    }

    @Override
    protected void onLoad() {
        javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);
    }

    @Override
    protected void onUnload() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void dispatchCommand(String command) {
        javaPlugin.getServer().dispatchCommand(javaPlugin.getServer().getConsoleSender(), command);
    }

    @Override
    public void dispatchOnJoin(String username, String command) {
        playerCommands.computeIfAbsent(username, u -> new ArrayList<>()).add(command);
    }

    @EventHandler
    public void playerJoinEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (!playerCommands.containsKey(player.getName())) return;

        List<String> commands = playerCommands.get(player.getName());

        commands.forEach(this::dispatchCommand);
    }
}