package ga.justreddy.wiki.rwitchwars.quests.quest;

import ga.justreddy.wiki.rwitchwars.quests.AbstractQuest;
import ga.justreddy.wiki.rwitchwars.quests.CooldownType;
import ga.justreddy.wiki.rwitchwars.quests.QuestType;

public class PlayGamesQuest extends AbstractQuest {

  @Override
  public String getName() {
    return "WitchWars Player";
  }

  @Override
  public String getDisplayName() {
    return "&aDaily Quest: WitchWars Player";
  }

  @Override
  public QuestType getQuestType() {
    return QuestType.WITCHWARS_PLAYER;
  }

  @Override
  public String[] getDescription() {
    return new String[]{"&7Play 2 games of WitchWars", "", "&8&oDaily Quests can be completed", "&8&oonce every day", "&8&oThe cooldown starts when the quest is completed", ""};
  }

  @Override
  public long getRequiredAmount() {
    return 2;
  }

  @Override
  public CooldownType getCooldownType() {
    return CooldownType.DAILY;
  }
}
