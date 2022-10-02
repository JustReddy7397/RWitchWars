package ga.justreddy.wiki.rwitchwars.achievements.achievement;

import ga.justreddy.wiki.rwitchwars.achievements.AbstractAchievement;
import ga.justreddy.wiki.rwitchwars.achievements.AchievementDifficulty;
import ga.justreddy.wiki.rwitchwars.achievements.AchievementType;

public class TraitorAchievement extends AbstractAchievement {

  @Override
  public String getName() {
    return "Traitor";
  }

  @Override
  public AchievementType getId() {
    return AchievementType.TRAITOR;
  }

  @Override
  public AchievementDifficulty getDifficulty() {
    return AchievementDifficulty.EASY;
  }

  @Override
  public String[] getDescription() {
    return new String[]{"Wait what? Did you just", "try to kill your own witch?"};
  }

  @Override
  public long getRequiredAmount() {
    return 1;
  }

}
