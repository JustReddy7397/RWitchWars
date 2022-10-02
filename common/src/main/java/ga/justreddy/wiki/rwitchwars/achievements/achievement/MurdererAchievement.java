package ga.justreddy.wiki.rwitchwars.achievements.achievement;

import ga.justreddy.wiki.rwitchwars.achievements.AbstractAchievement;
import ga.justreddy.wiki.rwitchwars.achievements.AchievementDifficulty;
import ga.justreddy.wiki.rwitchwars.achievements.AchievementType;

public class MurdererAchievement extends AbstractAchievement {

  @Override
  public String getName() {
    return "Murderer";
  }

  @Override
  public AchievementType getId() {
    return AchievementType.MURDERER;
  }

  @Override
  public AchievementDifficulty getDifficulty() {
    return AchievementDifficulty.EASY;
  }

  @Override
  public String[] getDescription() {
    return new String[]{"Well well well... It looks like", "you've become a MURDERER!!"};
  }

  @Override
  public long getRequiredAmount() {
    return 1;
  }
}
