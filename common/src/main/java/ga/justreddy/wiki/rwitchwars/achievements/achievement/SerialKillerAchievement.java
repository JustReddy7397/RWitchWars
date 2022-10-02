package ga.justreddy.wiki.rwitchwars.achievements.achievement;

import ga.justreddy.wiki.rwitchwars.achievements.AbstractAchievement;
import ga.justreddy.wiki.rwitchwars.achievements.AchievementDifficulty;
import ga.justreddy.wiki.rwitchwars.achievements.AchievementType;

public class SerialKillerAchievement extends AbstractAchievement {

  @Override
  public String getName() {
    return "Serial Killer";
  }

  @Override
  public AchievementType getId() {
    return AchievementType.SERIAL_KILLER;
  }

  @Override
  public AchievementDifficulty getDifficulty() {
    return AchievementDifficulty.HARD;
  }

  @Override
  public String[] getDescription() {
    return new String[]{"Eliminate 5 players without dying."};
  }

  @Override
  public long getRequiredAmount() {
    return 5;
  }
}
