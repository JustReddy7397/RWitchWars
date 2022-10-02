package ga.justreddy.wiki.rwitchwars.listeners;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.achievements.AchievementType;
import ga.justreddy.wiki.rwitchwars.cosmetics.Messages;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.entity.PlayerController;
import ga.justreddy.wiki.rwitchwars.enums.GameType;
import ga.justreddy.wiki.rwitchwars.game.Game;
import ga.justreddy.wiki.rwitchwars.game.team.GameTeam;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GameListener implements Listener {

  @EventHandler
  public void onEntityDamage(EntityDamageByEntityEvent e) {
    if (e.getEntity() instanceof Witch && e.getDamager() instanceof Player) {
      Witch witch = (Witch) e.getEntity();
      Player damager = (Player) e.getDamager();
      GamePlayer gamePlayer = PlayerController.getController().get(damager);
      RWitchWars.getWitchWars().getAchievementManager().getById(AchievementType.TRAITOR).trigger(gamePlayer);
      if (!gamePlayer.isPlaying()) {
        return;
      }
      Game game = gamePlayer.getGame();
      GameTeam gameTeam = game.getGameTeamByWitch(witch);
      if (gameTeam == null) {
        return;
      }
      if (gameTeam.getWitch() == witch) {
        e.setCancelled(true);
        gamePlayer.sendMessage("&cYou can't attack your own witch.");
        return;
      }
      if (witch.getHealth() <= 0.0 || e.getDamage() >= witch.getHealth()) {
        gameTeam.getAlivePlayers()
            .forEach(gamePlayer1 -> {
              gamePlayer1.sendTitle("&cYour witch died.", "&6You will not respawn after u die.");
            });
        Messages messages = gamePlayer.getMessages();
        gamePlayer.getGame()
            .getPlayers()
            .forEach(gamePlayer1 -> {
              gamePlayer1
                  .sendMessage(messages.witchKill()
                      .replaceAll("<team>", gameTeam.getTeamColor().getDisplayName().toUpperCase())
                      .replaceAll("<player>", gamePlayer.getName()));
            });

        gameTeam.setWitch(null);
      }


    } else if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
      Player damager = (Player) e.getDamager();
      Player target = (Player) e.getEntity();
      GamePlayer gamePlayer = PlayerController.getController().get(damager);
      GamePlayer targetPlayer = PlayerController.getController().get(target);
      if (!gamePlayer.isPlaying() || !targetPlayer.isPlaying()) {
        return;
      }
      if (gamePlayer.getGameTeam() == null || targetPlayer.getGameTeam() == null) {
        return;
      }
      if (gamePlayer.getGameTeam().getGamePlayers().contains(targetPlayer)) {
        e.setCancelled(true);
        return;
      }

      if (e.getDamage() >= target.getHealth() && targetPlayer.getGameTeam().isWitchAlive()) {
        // TODO death system
        RWitchWars.getWitchWars().getAchievementManager().getById(AchievementType.MURDERER).trigger(gamePlayer);
      } else if (e.getDamage() >= target.getHealth() && !targetPlayer.getGameTeam().isWitchAlive()) {
        // TODO death system
        RWitchWars.getWitchWars().getAchievementManager().getById(AchievementType.SERIAL_KILLER).trigger(gamePlayer);
        RWitchWars.getWitchWars().getAchievementManager().getById(AchievementType.MURDERER).trigger(gamePlayer);
        RWitchWars.getWitchWars().getAchievementManager().removeAchievementCounter(targetPlayer, RWitchWars.getWitchWars().getAchievementManager().getById(AchievementType.SERIAL_KILLER));
      }

    }
  }

  @EventHandler
  public void onGameChat(AsyncPlayerChatEvent e) {

    Player player = e.getPlayer();
    GamePlayer gamePlayer = PlayerController.getController().get(player);

    if (!gamePlayer.isPlaying()) {
      return;
    }

    Game game = gamePlayer.getGame();
    GameTeam gameTeam = gamePlayer.getGameTeam();
    if (game == null || gameTeam == null) {
      return;
    }
    if (!gamePlayer.isDead()) {
      if (game.getGameType() == GameType.SOLO) {
        game.getPlayers()
            .forEach(gamePlayer1 -> {
              gamePlayer1.sendMessage(
                  "%team% %player%&f: %message%"
                      .replaceAll("%team%", gameTeam.getTeamColor().getPrefix())
                      .replaceAll("%player%", player.getName())
                      .replaceAll("%message%", e.getMessage())
              );
            });
        e.setCancelled(true);
      }

      if (game.getGameType() == GameType.DUO || game.getGameType() == GameType.TRIO
          || game.getGameType() == GameType.SQUAD) {

        if (!e.getMessage().startsWith("!")) {
          gameTeam.getGamePlayers()
              .forEach(teamPlayer -> {
                teamPlayer.sendMessage(
                    "%team% %player%&f: %message%"
                        .replaceAll("%team%", gameTeam.getTeamColor().getPrefix())
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%message%", e.getMessage())
                );
              });
        } else {
          String message = e.getMessage().replace("!", "");
          game.getPlayers()
              .forEach(gamePlayer1 -> {
                gamePlayer1.sendMessage(
                    "&8[GLOBAL] %team% %player%&f: %message%"
                        .replaceAll("%team%", gameTeam.getTeamColor().getPrefix())
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%message%", message)
                );
              });
        }
        e.setCancelled(true);
      }
    } else {
      game.getSpectators()
          .forEach(teamPlayer -> {
            teamPlayer.sendMessage(
                "&7[SPECTATOR] %player%&f: %message%"
                    .replaceAll("%player%", player.getName())
                    .replaceAll("%message%", e.getMessage())
            );
          });
      e.setCancelled(true);

    }


  }


}
