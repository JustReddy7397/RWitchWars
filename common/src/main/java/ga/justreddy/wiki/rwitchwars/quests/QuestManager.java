package ga.justreddy.wiki.rwitchwars.quests;

import com.cryptomorin.xseries.XSound;
import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.achievements.AbstractAchievement;
import ga.justreddy.wiki.rwitchwars.achievements.AchievementType;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.quests.quest.PlayGamesQuest;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestManager {

  private final List<AbstractQuest> quests;

  public QuestManager() {
    this.quests = new ArrayList<>();
    registerQuests(new PlayGamesQuest());
  }

  private void registerQuests(AbstractQuest... quests) {
    this.quests.addAll(Arrays.asList(quests));
  }

  public void completeQuest(GamePlayer gamePlayer, AbstractQuest quest){
    gamePlayer.getQuests().completeQuest(quest);
    Utils.sendMessage(gamePlayer.getPlayer(), "&7Quest completed: &6" + quest.getName());
  }

  public void removeQuestsFromPlayer(GamePlayer gamePlayer, AbstractQuest quest) {
    gamePlayer.getQuests().removeQuest(quest);
  }

  public void removeQuestsCounter(GamePlayer gamePlayer, AbstractAchievement achievement) {
    achievement.getCounter().remove(gamePlayer);
  }

  public AbstractQuest getById(QuestType questType) {
    return quests.stream().filter(quest -> quest.getQuestType() == questType).findFirst().orElse(null);
  }

  public List<AbstractQuest> getQuests() {
    return quests;
  }
}
