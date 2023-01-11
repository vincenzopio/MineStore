package it.vincenzopio.minestore.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.velocity.core.MineStoreVelocity;

import java.io.File;

@Plugin(id = "minestore", name = "MineStore", version = "1.0.0", authors = "VincenzoPio")
public class VelocityPlugin {

    private final ProxyServer proxyServer;
    private final File directory;

    private final MineStore<?, ?> mineStore;

    @Inject
    public VelocityPlugin(ProxyServer proxyServer, @DataDirectory File directory) {
        this.proxyServer = proxyServer;
        this.directory = directory;

        mineStore = new MineStoreVelocity(this, proxyServer, directory);
    }

    @Subscribe
    public void proxyInitializeEvent(ProxyInitializeEvent event) {
        mineStore.load();
    }
}
