package ga.justreddy.wiki.rwitchwars.quests;

import ga.justreddy.wiki.rwitchwars.utils.Utils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CooldownType {

  DAILY("10s"),
  WEEKLY("1w");

  private final String cooldown;

}
