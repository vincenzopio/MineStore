package it.vincenzopio.minestore.spigot.core.server.store.command;

import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.server.command.CommandExecution;
import it.vincenzopio.minestore.api.server.command.CommandService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SpigotCommandService extends CommandService implements Listener {


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
        javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> javaPlugin.getServer().dispatchCommand(javaPlugin.getServer().getConsoleSender(), command));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerJoinEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (!ONLINE_COMMANDS.containsKey(player.getName())) return;

        javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> {
            if (!player.isOnline()) return;

            List<CommandExecution> commands = ONLINE_COMMANDS.get(player.getName());

            commands.forEach(commandExecution -> dispatchCommand(commandExecution.getCommand()));

            ONLINE_COMMANDS.remove(player.getName());

            saveCache();
        }, 100);
    }
}
