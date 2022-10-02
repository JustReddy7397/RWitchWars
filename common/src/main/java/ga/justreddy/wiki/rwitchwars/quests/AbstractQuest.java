package ga.justreddy.wiki.rwitchwars.quests;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractQuest {


  public AbstractQuest() {
  }

  public abstract String getName();

  public abstract String getDisplayName();

  public abstract QuestType getQuestType();

  public abstract String[] getDescription();

  public abstract long getRequiredAmount();

  public abstract CooldownType getCooldownType();

  public void trigger(GamePlayer gamePlayer) {
    if (gamePlayer.getQuests().getCompletedQuests().containsKey(getQuestType())) return;
    if (!gamePlayer.getQuests().getQuestCounter().containsKey(this)) {
      gamePlayer.getQuests().setQuests(this, 0);
    }
    gamePlayer.getQuests().updateQuests(this, 1);
    if (getRequiredAmount() == gamePlayer.getQuests().getQuestCounter().get(this)) {
      onComplete(gamePlayer);
    }
  }

  public void onComplete(GamePlayer gamePlayer) {
    RWitchWars.getWitchWars().getQuestManager().completeQuest(gamePlayer, this);
  }

}
