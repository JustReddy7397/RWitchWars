package ga.justreddy.wiki.rwitchwars.controller;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.game.Game;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GameController {

    @Getter private final File arenaFolder = new File(RWitchWars.getWitchWars().getDataFolder().getAbsolutePath() + "/data/arenas");

    @Getter private Map<String, Game> games;

    private static GameController controller;

    public static GameController getController() {
        if (controller == null) controller = new GameController();
        return controller;
    }

    public GameController() {
        if (!arenaFolder.exists()) arenaFolder.mkdir();
        games = new HashMap<>();
    }

    public void start() {

        File[] files = arenaFolder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (!file.getName().endsWith(".yml")) continue;
            String name = file.getName().replace(".yml", "");
            if (games.containsKey(name)) continue;
            register(name, YamlConfiguration.loadConfiguration(file));
        }
    }

    public void register(String name, YamlConfiguration configuration) {
        games.put(name, new Game(name, configuration));
    }

    public void reload() {
        games.clear();
        start();
    }

    public Game getGameByName(String name) {
        return games.getOrDefault(name, null);
    }

}
