package it.vincenzopio.minestore.api.connection.handler.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.connection.handler.ConnectionHandler;
import it.vincenzopio.minestore.api.connection.handler.listener.message.ListenerMessage;
import it.vincenzopio.minestore.api.server.command.CommandExecution;
import it.vincenzopio.minestore.api.server.command.CommandService;
import it.vincenzopio.minestore.api.settings.connection.mode.listener.ListenerSettings;
import it.vincenzopio.minestore.api.settings.store.StoreSettings;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;

public class ConnectionListenerHandler implements ConnectionHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final MineStore<?, ?> mineStore;

    private final StoreSettings storeSettings;
    private final ListenerSettings listenerSettings;
    private final CommandService commandService;

    public ConnectionListenerHandler(MineStore<?, ?> mineStore) {
        this.mineStore = mineStore;

        this.storeSettings = mineStore.getSettingsService().getPluginSettings().getStoreSettings();
        this.listenerSettings = mineStore.getSettingsService().getPluginSettings().getConnectionSettings().getListenerConfig();
        this.commandService = mineStore.getCommandService();
    }

    @Override
    public void connect() {
        String storeUrl = storeSettings.getApiAddress();
        String secretKey = listenerSettings.getSecretKey();

        mineStore.getTaskScheduler().asyncTimer(() -> {
            MineStore.LOGGER.info("Fetching from store...");
            try {
                URL url = new URL(storeUrl + "servers/" + secretKey + "/commands/queue");

                ListenerMessage message = OBJECT_MAPPER.readValue(url, ListenerMessage.class);

                if (message == null) {
                    return;
                }

                if (message.getCommand() == null) {
                    return;
                }

                String command = message.getCommand().replaceFirst("/", "").replaceFirst(" {3}", " ");
                String username = message.getUsername();


                int id = message.getId();

                MineStore.LOGGER.info("Found " + username + " command: " + command);

                writeExecute(id);

                if (message.isRequiredOnline()) {
                    if (mineStore.getPlayerResolver().isOnline(username)) {
                        commandService.dispatchCommand(command);
                        return;
                    }

                    commandService.dispatchOnJoin(username, new CommandExecution(username, command));
                    return;
                }

                commandService.dispatchCommand(command);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 5, listenerSettings.getUpdateDelay());
    }

    public void writeExecute(int id) {
        String storeUrl = storeSettings.getApiAddress();
        String secretKey = listenerSettings.getSecretKey();

        try {
            URL url = new URL(storeUrl + "servers/" + secretKey + "/commands/executed/" + id);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConnection.setDoOutput(true);

            try (OutputStream os = urlConnection.getOutputStream()) {
                os.write(id);
            }

            urlConnection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {

    }
}
