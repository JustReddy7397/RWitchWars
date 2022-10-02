package ga.justreddy.wiki.rwitchwars.entity;

import ga.justreddy.wiki.rwitchwars.quests.AbstractQuest;
import ga.justreddy.wiki.rwitchwars.quests.QuestType;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerQuests {

  private final Map<QuestType, Long> completedQuests;
  private final List<String> startedQuests;
  private final Map<AbstractQuest, Integer> questCounter;

  public PlayerQuests() {
    this.completedQuests = new HashMap<>();
    this.startedQuests = new ArrayList<>();
    this.questCounter = new HashMap<>();
  }

  public void startQuest(AbstractQuest quest) {
    this.startedQuests.add(quest.getQuestType().getId());
  }

  public void completeQuest(AbstractQuest quest) {
    this.completedQuests.put(quest.getQuestType(), Utils.getDurationMS(quest.getCooldownType().getCooldown()));
    this.startedQuests.remove(quest.getQuestType().getId());
    questCounter.remove(quest);
    long ms = Utils.getDurationMS(quest.getCooldownType().getCooldown());
    System.out.println(ms + " - " + Utils.getDurationString(ms));
  }

  public void removeQuest(AbstractQuest quest) {
    completedQuests.remove(quest.getQuestType());
    questCounter.remove(quest);
  }

  public void removeQuestFromCooldown(AbstractQuest quest) {
    completedQuests.remove(quest.getQuestType());
    questCounter.remove(quest);
  }

  public void setQuests(AbstractQuest quest, int counter) {
    questCounter.put(quest, counter);
  }

  public void updateQuests(AbstractQuest quest, int addCounter) {
    questCounter.replace(quest, questCounter.get(quest), (questCounter.get(quest) + addCounter));
  }

  public boolean isQuestCompleted(QuestType questType) {
    return completedQuests.containsKey(questType);
  }

  public List<String> getStartedQuests() {
    return startedQuests;
  }

  public Map<AbstractQuest, Integer> getQuestCounter() {
    return questCounter;
  }

  public Map<QuestType, Long> getCompletedQuests() {
    return completedQuests;
  }
}
