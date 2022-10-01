package ga.justreddy.wiki.rwitchwars.controller;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.game.map.GameMap;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MapController {

    @Getter private final File worldFolder;

    private static MapController controller;

    public static MapController getController() {
        if (controller == null) controller = new MapController();
        return controller;
    }

    private final Map<String, GameMap> gameMaps;

    public MapController() {
        this.worldFolder = new File(RWitchWars.getWitchWars().getDataFolder().getAbsolutePath() + "/data/worlds");
        if (!worldFolder.exists()) worldFolder.mkdirs();

        this.gameMaps = new HashMap<>();

    }

    public void start() {
        if (worldFolder == null || worldFolder.listFiles() == null) return;
        for (File file : worldFolder.listFiles()) {
            String name = file.getName();
            if (name.isEmpty() || gameMaps.containsKey(name)) continue;
            register(name);
        }
    }


    public void register(String name) {
        gameMaps.put(name, new GameMap(worldFolder, name, true));
    }

    public void reload() {

    }

    public GameMap getByName(String name) {
        return gameMaps.getOrDefault(name, null);
    }

    public Map<String, GameMap> getGameMaps() {
        return gameMaps;
    }
}
