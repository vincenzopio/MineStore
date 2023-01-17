package it.vincenzopio.minestore.api.server.platform;

public enum Platform {

    SPIGOT(true),
    VELOCITY(false);

    private final boolean supportMenu;

    Platform(boolean supportMenu) {
        this.supportMenu = supportMenu;
    }

    public boolean hasMenuSupport() {
        return supportMenu;
    }
}
