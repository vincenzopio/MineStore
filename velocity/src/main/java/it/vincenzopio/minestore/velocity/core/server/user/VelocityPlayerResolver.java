package it.vincenzopio.minestore.velocity.core.server.user;

import com.velocitypowered.api.proxy.ProxyServer;
import it.vincenzopio.minestore.api.server.user.PlayerResolver;
import net.kyori.adventure.text.Component;

public class VelocityPlayerResolver implements PlayerResolver {

    private final ProxyServer proxyServer;

    public VelocityPlayerResolver(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public boolean isOnline(String username) {
        return proxyServer.getPlayer(username).isPresent();
    }

    @Override
    public void sendChatMessage(String username, Component component) {
        proxyServer.getPlayer(username).ifPresent(player -> player.sendMessage(component));
    }
}
