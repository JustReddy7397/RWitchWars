package ga.justreddy.wiki.rwitchwars.achievements;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAchievement {

  private final Map<GamePlayer, Integer> counter;

  public AbstractAchievement() {
    this.counter = new HashMap<>();
  }

  public abstract String getName();

  public abstract AchievementType getId();

  public abstract String[] getDescription();

  public abstract long getRequiredAmount();

  public void trigger(GamePlayer gamePlayer) {
    if (gamePlayer.getAchievements().getAchievements().contains(getId().getId())) return;
    if (!counter.containsKey(gamePlayer)) {
      counter.put(gamePlayer, 0);
    }
    counter.replace(gamePlayer, counter.get(gamePlayer), (counter.get(gamePlayer) + 1));
    if (getRequiredAmount() == counter.get(gamePlayer)) {
      onComplete(gamePlayer);
    }
  }

  public void onComplete(GamePlayer gamePlayer) {
    RWitchWars.getWitchWars().getAchievementManager().addAchievementToPlayer(gamePlayer, this);
  }

  public Map<GamePlayer, Integer> getCounter() {
    return counter;
  }

}
