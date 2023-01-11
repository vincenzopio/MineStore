package it.vincenzopio.minestore.api.connection.handler.listener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.connection.handler.ConnectionHandler;
import it.vincenzopio.minestore.api.connection.handler.listener.message.ListenerMessage;
import it.vincenzopio.minestore.api.server.command.CommandService;
import it.vincenzopio.minestore.api.settings.connection.mode.listener.ListenerSettings;
import it.vincenzopio.minestore.api.settings.store.StoreSettings;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class ConnectionListenerHandler implements ConnectionHandler {

    private static final Gson GSON = new GsonBuilder().create();


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
        String storeUrl = storeSettings.getAddress();
        String secretKey = listenerSettings.getSecretKey();

        mineStore.getTaskScheduler().asyncTimer(() -> {
            try {
                URL url = new URL(storeUrl + "servers/" + secretKey + "/commands/queue");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream stream = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                ListenerMessage message = null;

                String line;
                while ((line = reader.readLine()) != null) {
                    message = GSON.fromJson(line, ListenerMessage.class);
                }

                if (message == null) {
                    return;
                }

                if (message.getCommand() == null) {
                    return;
                }

                String command = message.getCommand().replaceFirst("/", "").replaceFirst(" {3}", " ");

                String username = message.getUsername();

                writeExecute(message.getId());

                if (message.isRequiredOnline()) {
                    if (mineStore.getPlayerResolver().isOnline(username)) {
                        commandService.dispatchCommand(command);
                        return;
                    }

                    commandService.dispatchOnJoin(username, command);
                    return;
                }

                commandService.dispatchCommand(command);

                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 0, 10);

    }

    public void writeExecute(int id) {
        String storeUrl = storeSettings.getAddress();
        String secretKey = listenerSettings.getSecretKey();

        try {
            URL url = new URL(storeUrl + "servers/" + secretKey + "/commands/executed" + id);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConnection.setDoOutput(true);

            try (final OutputStream os = urlConnection.getOutputStream()) {
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
