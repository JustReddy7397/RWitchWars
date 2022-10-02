package ga.justreddy.wiki.rwitchwars.command.commands;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.command.ICommand;
import ga.justreddy.wiki.rwitchwars.entity.PlayerController;
import ga.justreddy.wiki.rwitchwars.menus.menu.game.AchievementsMenu;
import ga.justreddy.wiki.rwitchwars.menus.menu.game.QuestMenu;
import ga.justreddy.wiki.rwitchwars.quests.QuestType;
import org.bukkit.entity.Player;

public class TestCommand implements ICommand {

  @Override
  public String name() {
    return "test";
  }

  @Override
  public String description() {
    return null;
  }

  @Override
  public String usage() {
    return null;
  }

  @Override
  public String permission() {
    return null;
  }

  @Override
  public String[] aliases() {
    return new String[0];
  }

  @Override
  public void onCommand(Player player, String[] args) {
    if (args[1].equals("complete")) {
      RWitchWars.getWitchWars().getQuestManager().completeQuest(PlayerController.getController()
          .get(player), RWitchWars.getWitchWars().getQuestManager().getById(QuestType.WITCHWARS_PLAYER));
    } else if(args[1].equals("menu")) {
      new QuestMenu().open(player);
    } else if (args[1].equals("achievement")) {
      new AchievementsMenu().open(player);
    }
  }
}
