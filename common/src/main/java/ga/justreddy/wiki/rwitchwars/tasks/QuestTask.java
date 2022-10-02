package ga.justreddy.wiki.rwitchwars.tasks;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.entity.PlayerController;
import ga.justreddy.wiki.rwitchwars.quests.AbstractQuest;
import ga.justreddy.wiki.rwitchwars.quests.QuestType;
import java.util.Date;
import java.util.Map;
import org.bukkit.scheduler.BukkitRunnable;

public class QuestTask extends BukkitRunnable {

  @Override
  public void run() {

    for (GamePlayer gamePlayer : PlayerController.getController().getGamePlayerMap().values()) {
      for (Map.Entry<QuestType, Long> entry : gamePlayer.getQuests().getCompletedQuests().entrySet()) {
        if (new Date().getTime() < entry.getValue()) continue;
        gamePlayer.getQuests().removeQuestFromCooldown(RWitchWars.getWitchWars().getQuestManager().getById(
            entry.getKey()));
      }
    }

  }
}
