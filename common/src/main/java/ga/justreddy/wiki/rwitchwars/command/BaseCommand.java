package ga.justreddy.wiki.rwitchwars.command;

import ga.justreddy.wiki.rwitchwars.command.commands.ArenaCommand;
import ga.justreddy.wiki.rwitchwars.command.commands.TestCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaseCommand implements CommandExecutor {

  private final List<ICommand> commands;

  public BaseCommand() {
    this.commands = new ArrayList<>();
    registerCommands(new ArenaCommand(), new TestCommand());

  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    if(!(sender instanceof Player)) return true;

    final Player player = (Player) sender;

    ICommand cmd = getCommand(args[0]);

    if (cmd.permission() != null && !player.hasPermission(cmd.permission())) {
      // TODO make no perm message
      return true;
    }

    cmd.onCommand(player, args);

    return true;
  }

  private void registerCommands(ICommand... commands) {
    this.commands.addAll(Arrays.asList(commands));
  }

  private ICommand getCommand(String name) {
    ICommand iCommand = null;
    for (ICommand cmd : commands) {
      if ((cmd.name().equals(name)) || (Arrays.asList(cmd.aliases()).contains(name))) iCommand = cmd;
    }
    return iCommand;
  }

  public List<ICommand> getCommands() {
    return commands;
  }
}
