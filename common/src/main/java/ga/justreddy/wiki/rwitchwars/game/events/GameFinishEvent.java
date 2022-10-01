package ga.justreddy.wiki.rwitchwars.game.events;

import ga.justreddy.wiki.rwitchwars.game.Game;

public class GameFinishEvent extends AbstractGameEvents {

  public GameFinishEvent(Game game, boolean enabled) {
    super(game, enabled);
    setName("Ending");
  }

  @Override
  public void start() {
    game.onGameEnd(null);
    setEnabled(false);
  }

  @Override
  public void stop() {

  }
}
