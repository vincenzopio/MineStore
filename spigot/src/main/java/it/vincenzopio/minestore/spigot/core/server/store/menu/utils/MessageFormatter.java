package it.vincenzopio.minestore.spigot.core.server.store.menu.utils;

import it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items.MenuItem;
import org.bukkit.ChatColor;

import java.io.Serializable;

public class MessageFormatter implements Serializable {

    private String message;


    private MessageFormatter(String message) {
        this.message = message;
    }

    public static MessageFormatter formatter(String message) {
        return new MessageFormatter(message);
    }

    public static MessageFormatter formatter(String message, boolean ampersand) {
        MessageFormatter messageFormatter = new MessageFormatter(message);

        return ampersand ? messageFormatter.ampersand() : messageFormatter;
    }

    public MessageFormatter ampersand() {
        message = ChatColor.translateAlternateColorCodes('&', message);
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
