package it.vincenzopio.minestore.spigot.core.server.store.menu.inventory.items;

public interface MenuItem {

    String getName();

    String getURL();

    ItemType getType();


    default boolean hasPrice() {
        return false;
    }

    default int getPrice() {
        return 0;
    }


    enum ItemType {
        CATEGORY,
        SUB_CATEGORY,
        PACKAGE;
    }
}
