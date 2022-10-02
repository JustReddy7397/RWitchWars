package ga.justreddy.wiki.rwitchwars.entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerController {

    private static PlayerController controller;

    private final Map<UUID, GamePlayer> gamePlayerMap = new HashMap<>();

    public static PlayerController getController() {
        if (controller == null) {
            controller = new PlayerController();
        }
        return controller;
    }

    public void add(Player player) {
        gamePlayerMap.putIfAbsent(player.getUniqueId(), new GamePlayer(player));
    }

    public void add(UUID uuid) {
        add(Bukkit.getPlayer(uuid));
    }

    public GamePlayer get(Player player) {
        return gamePlayerMap.getOrDefault(player.getUniqueId(), null);
    }

    public GamePlayer get(UUID uuid) {
        return get(Bukkit.getPlayer(uuid));
    }

    public void remove(Player player) {
        gamePlayerMap.remove(player.getUniqueId());
    }

    public void remove(UUID uuid) {
        remove(Bukkit.getPlayer(uuid));
    }

    public Map<UUID, GamePlayer> getGamePlayerMap() {
        return gamePlayerMap;
    }
}
