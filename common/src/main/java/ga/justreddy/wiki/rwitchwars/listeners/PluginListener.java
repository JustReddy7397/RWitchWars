package ga.justreddy.wiki.rwitchwars.listeners;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.PlayerController;
import ga.justreddy.wiki.rwitchwars.entity.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PluginListener implements Listener {

  @EventHandler
  public void onPLayerJoin(PlayerJoinEvent e) {
    PlayerController.getController().add(e.getPlayer());
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
    RWitchWars.getWitchWars().getDataStorage().savePlayerData(PlayerController.getController().get(e.getPlayer()));
    PlayerController.getController().remove(e.getPlayer());
  }

}
