package ga.justreddy.wiki.rwitchwars.entity;

import com.cryptomorin.xseries.messages.Titles;
import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.controller.MessagesController;
import ga.justreddy.wiki.rwitchwars.cosmetics.Messages;
import ga.justreddy.wiki.rwitchwars.game.Game;
import ga.justreddy.wiki.rwitchwars.game.GameTeam;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.cryptomorin.xseries.XSound;

import java.util.UUID;

public class GamePlayer {

    private final Player player;
    private final UUID uniqueId;
    private final String uniqueIdString;
    private final String name;
    private GameTeam gameTeam;
    private boolean dead;
    private Game game = null;
    private PlayerCosmetics cosmetics;
    private PlayerStats stats;
    private PlayerAchievements achievements;

    public GamePlayer(Player player) {
        this.player = player;
        this.uniqueId = player.getUniqueId();
        this.uniqueIdString = player.getUniqueId().toString();
        this.name = player.getName();
        this.cosmetics = new PlayerCosmetics();
        this.stats = new PlayerStats();
        this.achievements = new PlayerAchievements();
        RWitchWars.getWitchWars().getDataStorage().loadPlayerData(this);
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getUniqueIdString() {
        return uniqueIdString;
    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isPlaying() {
        return game != null;
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public PlayerCosmetics getCosmetics() {
        return cosmetics;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public void setCosmetics(PlayerCosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }

    public void setStats(PlayerStats stats) {
        this.stats = stats;
    }

    public PlayerAchievements getAchievements() {
        return achievements;
    }

    public void setAchievements(PlayerAchievements achievements) {
        this.achievements = achievements;
    }

    public void setGameTeam(GameTeam gameTeam) {
        this.gameTeam = gameTeam;
    }

    public GameTeam getGameTeam() {
        return gameTeam;
    }

    public void sendMessage(String message) {
        player.sendMessage(Utils.format(message));
    }

    public void sendSound(String sound) {
        if (!XSound.matchXSound(sound).isPresent()) return;
        player.playSound(player.getLocation(), XSound.matchXSound(sound).get().parseSound(), 1, 1);
    }

    public void sendTitle(String title, String subTitle) {
        Titles.sendTitle(
                player,
                Utils.format(title),
                Utils.format(subTitle)
        );
    }

    public Messages getMessages() {
        return MessagesController.getController().getById(cosmetics.getMessagesSelect());
    }

}
