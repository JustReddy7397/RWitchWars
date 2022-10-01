package ga.justreddy.wiki.rwitchwars.game;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.enums.GeneratorType;
import ga.justreddy.wiki.rwitchwars.enums.TeamColor;
import ga.justreddy.wiki.rwitchwars.generators.Generator;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Witch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameTeam {

    private final String id;
    private final List<GamePlayer> gamePlayers;
    private final Location spawnLocation;
    private Witch witch;
    private final Location witchLocation;
    private final int maximum;

    public GameTeam(String id, ConfigurationSection section, int maximum) {
        this.id = id;
        this.gamePlayers = new ArrayList<>();
        this.spawnLocation = (Location) section.get("spawnLocation");
        this.witchLocation = (Location) section.get("witchLocation");
        this.maximum = maximum;
    }

    public TeamColor getTeamColor() {
        TeamColor teamColor = null;
        for (TeamColor teamColor1 : TeamColor.values()) {
            if (teamColor1.getIdentifier().equalsIgnoreCase(id)) teamColor = teamColor1;
        }
        return teamColor;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return gamePlayers.size();
    }

    public int getMaximum() {
        return maximum;
    }

    public boolean isFull() {
        return getSize() >= getMaximum();
    }

    public List<GamePlayer> getAlivePlayers() {
        return gamePlayers.stream().filter(gamePlayer -> !gamePlayer.isDead()).collect(Collectors.toList());
    }

    public List<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public List<GamePlayer> getSpectators() {
        return gamePlayers.stream().filter(GamePlayer::isDead).collect(Collectors.toList());
    }

    public boolean hasPlayer(GamePlayer gamePlayer) {
        return gamePlayers.contains(gamePlayer);
    }

    public void addPlayer(GamePlayer gamePlayer) {
        if (hasPlayer(gamePlayer)) return;
        gamePlayers.add(gamePlayer);
        gamePlayer.setGameTeam(this);
    }

    public void removePlayer(GamePlayer gamePlayer) {
        if(!hasPlayer(gamePlayer)) return;
        gamePlayers.remove(gamePlayer);
        gamePlayer.setGameTeam(null);
    }

    public int getAliveCount() {
        return (int) gamePlayers.stream().filter(gamePlayer -> !gamePlayer.isDead()).count();
    }

    public void spawnWitch() {
        witch = witchLocation.getWorld().spawn(witchLocation, Witch.class);
        witch.setHealth(500.0D);
        NBTEntity entity = new NBTEntity(witch);
        entity.setString("gameWitch", id);
        RWitchWars.getWitchWars().getNms().removeAI(witch);
    }

    public void setWitch(Witch witch) {
        this.witch = witch;
    }

    public boolean isWitchAlive() {
        return !witch.isDead() && witch != null;
    }

    // Displays the text after the team name
    // Witch alive? show a check mark
    // Witch dead and team players alive? show team player count
    // Witch dead and all team players dead? show a cross
    public String getDisplay() {
        if (isWitchAlive()) {
            return "&a✅";
        } else if (!isWitchAlive() && getAliveCount() > 0) {
            return "&f" + getAliveCount();
        } else if (!isWitchAlive()) {
            return "&c❌";
        }
        return "error";
    }

    public Location getWitchLocation() {
        return witchLocation;
    }

    public Witch getWitch() {
        return witch;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void teleportToSpawn(GamePlayer gamePlayer) {
        gamePlayer.getPlayer().teleport(getSpawnLocation());
    }

    public void teleportToSpawn() {
        gamePlayers.stream().filter(gamePlayer -> !gamePlayer.isDead()).forEach(this::teleportToSpawn);
    }

}
