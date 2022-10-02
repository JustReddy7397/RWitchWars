package ga.justreddy.wiki.rwitchwars.command.commands;

import ga.justreddy.wiki.rwitchwars.command.ICommand;
import org.bukkit.entity.Player;

public class ArenaCommand implements ICommand {

  @Override
  public String name() {
    return "arena";
  }

  @Override
  public String description() {
    return "Arena commands";
  }

  @Override
  public String usage() {
    return "/ww arena <args>";
  }

  @Override
  public String permission() {
    return "rwitchwars.command.admin";
  }

  @Override
  public String[] aliases() {
    return new String[]{};
  }

  @Override
  public void onCommand(Player player, String[] args) {

    switch (args[1]) {
      case "create": createArenaCommand(player, args[1]); break;
      default:
        sendHelpCommand(player); break;
    }

  }
  
  private void createArenaCommand(Player player, String name) {

  }

  private void sendHelpCommand(Player player) {

  }


}
