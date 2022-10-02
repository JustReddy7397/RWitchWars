package ga.justreddy.wiki.rwitchwars.quests;

import ga.justreddy.wiki.rwitchwars.achievements.AchievementType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestType {

  WITCHWARS_PLAYER("wwplayer");

  private final String id;

  public static QuestType getById(String id) {
    for(QuestType e : values()) {
      if(e.id.equals(id)) return e;
    }
    return null;
  }


}
