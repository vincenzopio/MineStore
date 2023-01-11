package it.vincenzopio.minestore.api.server.command;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.service.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandService extends Service {

    protected static final TypeReference<Map<String, List<CommandExecution>>> typeReference = new TypeReference<Map<String, List<CommandExecution>>>() {
    };
    protected static final Map<String, List<CommandExecution>> ONLINE_COMMANDS = new HashMap<>();

    protected static final ObjectMapper MAPPER = new ObjectMapper(new JsonFactory());

    protected File file;

    protected CommandService(MineStore<?, ?> mineStore) {
        super(mineStore);
    }


    @Override
    public void load() {
        file = new File(mineStore.getDataFolder(), "online_cache.json");

        try {
            if (!file.exists()) {
                MineStore.LOGGER.info(file.createNewFile() ? "Created new empty online_cache.json file!" : "Could not create online_cache.json file!");
            } else {
                Map<String, List<CommandExecution>> value = MAPPER.readValue(file, typeReference);

                for (Map.Entry<String, List<CommandExecution>> entry : value.entrySet()) {
                    ONLINE_COMMANDS.computeIfAbsent(entry.getKey(), u -> new ArrayList<>()).addAll(entry.getValue());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        super.load();
    }

    @Override
    public void unload() {
        saveCache();

        super.unload();
    }

    public abstract void dispatchCommand(String command);


    public void dispatchOnJoin(String username, CommandExecution command) {
        ONLINE_COMMANDS.computeIfAbsent(username, u -> new ArrayList<>()).add(command);

        saveCache();
    }

    protected void saveCache() {
        try {
            MAPPER.writeValue(file, ONLINE_COMMANDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getCacheFile() {
        return file;
    }
}
