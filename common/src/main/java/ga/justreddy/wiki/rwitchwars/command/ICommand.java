package ga.justreddy.wiki.rwitchwars.command;

import org.bukkit.entity.Player;

public interface ICommand {

  String name();

  String description();

  String usage();

  String permission();

  String[] aliases();

  void onCommand(Player player, String[] args);

}
