package it.vincenzopio.minestore.spigot.core.server.store.menu.utils;

import fr.minuskube.inv.content.Pagination;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.MenuItem;
import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.packages.PackageItem;
import org.bukkit.ChatColor;

import java.io.Serializable;

public class MessageFormatter implements Serializable {

    private String message;


    private MessageFormatter(String message) {
        this.message = message == null ? "" : message;
    }

    public static MessageFormatter formatter(String message) {
        return new MessageFormatter(message);
    }

    public static MessageFormatter formatter(String message, boolean ampersand) {
        MessageFormatter messageFormatter = formatter(message);

        return ampersand ? messageFormatter.ampersand() : messageFormatter;
    }

    public MessageFormatter ampersand() {
        message = ChatColor.translateAlternateColorCodes('&', message);
        return this;
    }

    public MessageFormatter description(PackageItem packageItem) {
        message = message.replaceAll("%description%", packageItem.getItemLore());
        return this;
    }

    public MessageFormatter pagination(Pagination pagination) {
        message = message.replaceAll("%prev_page%", pagination.previous().getPage() + "")
                .replaceAll("%next_page%", pagination.previous().getPage() + "");

        return this;
    }

    public MessageFormatter format(MenuItem menuItem) {
        message = message.replaceAll("%name%", menuItem.getName())
                .replaceAll("%price%", menuItem.getPrice() + "");

        return this;
    }

    public MessageFormatter url(MenuItem menuItem, String baseUrl) {
        message = message.replaceAll("%url%", baseUrl + menuItem.getURL());
        return this;
    }

    @Override
    public String toString() {
        return message;
    }
}
