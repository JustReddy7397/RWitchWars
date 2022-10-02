package ga.justreddy.wiki.rwitchwars.game.events;

import ga.justreddy.wiki.rwitchwars.game.Game;
import ga.justreddy.wiki.rwitchwars.game.team.GameTeam;
import org.bukkit.entity.Witch;

public class WitchBreakEvent extends AbstractGameEvents {

  public WitchBreakEvent(Game game, boolean enabled) {
    super(game, enabled);
    setName("Witch Kill");
  }

  @Override
  public void start() {
    game.getTeams()
        .stream().filter(GameTeam::isWitchAlive)
        .forEach(gameTeam -> {
          Witch witch = gameTeam.getWitch();
          witch.setHealth(0.0);
          if (witch != null) witch.remove();
          gameTeam.setWitch(null);
        });
    setEnabled(false);
  }

  @Override
  public void stop() {

  }
}
