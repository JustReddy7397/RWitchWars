package ga.justreddy.wiki.rwitchwars.game.team;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTItem;
import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.enums.TeamColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Witch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.inventory.ItemStack;

public class GameTeam {

    private final String id;
    private final List<GamePlayer> gamePlayers;
    private final Location spawnLocation;
    private Witch witch;
    private final Location witchLocation;
    private int sharpnesslevel;
    private int protectionLevel;
    private final int maximum;

    public GameTeam(String id, ConfigurationSection section, int maximum) {
        this.id = id;
        this.gamePlayers = new ArrayList<>();
        this.spawnLocation = (Location) section.get("spawnLocation");
        this.witchLocation = (Location) section.get("witchLocation");
        this.maximum = maximum;
        this.sharpnesslevel = 0;
        this.protectionLevel = 0;
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

    public void giveItems(GamePlayer gamePlayer) {
        NBTItem item = new NBTItem(XMaterial.WOODEN_SWORD.parseItem());
        item.setString("swordId", "gameSword");
        if (sharpnesslevel != 0) {
            item.getItem().addEnchantment(Enchantment.DAMAGE_ALL, sharpnesslevel);
        }
        gamePlayer.getPlayer().getInventory().addItem(item.getItem());
        

    }
}
