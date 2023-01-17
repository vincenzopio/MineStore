package it.vincenzopio.minestore.spigot.core.server.store.menu.command;

import it.vincenzopio.minestore.spigot.core.server.store.menu.MenuService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StoreCommand implements CommandExecutor {

    private final MenuService menuService;

    public StoreCommand(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "This command can be only executed from the game!");
            return false;
        }

        menuService.processCommand((Player) sender);
        return true;
    }
}
