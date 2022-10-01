package ga.justreddy.wiki.rwitchwars.achievements;

import com.cryptomorin.xseries.XSound;
import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.achievements.achievement.MurdererAchievement;
import ga.justreddy.wiki.rwitchwars.achievements.achievement.SerialKillerAchievement;
import ga.justreddy.wiki.rwitchwars.achievements.achievement.TraitorAchievement;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AchievementManager {

  private final List<AbstractAchievement> achievementList;

  public AchievementManager() {
    this.achievementList = new ArrayList<>();
    registerAchievements(
        new TraitorAchievement(),
        new MurdererAchievement(),
        new SerialKillerAchievement()
        );
  }

  private void registerAchievements(AbstractAchievement... achievements) {
    achievementList.addAll(Arrays.asList(achievements));
  }

  public void addAchievementToPlayer(GamePlayer gamePlayer, AbstractAchievement achievement) {
    gamePlayer.getAchievements().addAchievement(achievement);
    StringBuilder builder = new StringBuilder();
    for (String line : achievement.getDescription()) {
      builder.append(line).append("\n");
    }
    RWitchWars.getWitchWars().getNms().sendJsonMessage(gamePlayer.getPlayer(),
        Utils.format(
            "[\"\",{\"text\":\">> \",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\"Achievement unlocked:\",\"color\":\"green\"},"
                + "{\"text\":\" \"},{\"text\":\""+achievement.getName()+"\",\"color\":\"gold\","
                + "\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"&a"+achievement.getName()+""
                + "\n"+builder+"\"}}]\n"
        ));
    gamePlayer.sendSound(XSound.ENTITY_PLAYER_LEVELUP.name());
  }

  public void removeAchievementFromPlayer(GamePlayer gamePlayer, AbstractAchievement achievement) {
    gamePlayer.getAchievements().removeAchievement(achievement);
  }

  public void removeAchievementCounter(GamePlayer gamePlayer, AbstractAchievement achievement) {
    achievement.getCounter().remove(gamePlayer);
  }

  public AbstractAchievement getById(AchievementType type) {
    return achievementList.stream().filter(achievement -> achievement.getId() == type).findFirst().orElse(null);
  }

}
