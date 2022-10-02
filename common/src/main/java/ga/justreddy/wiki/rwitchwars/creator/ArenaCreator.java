package ga.justreddy.wiki.rwitchwars.creator;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.controller.WorldController;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.enums.GeneratorType;
import ga.justreddy.wiki.rwitchwars.enums.TeamColor;
import ga.justreddy.wiki.rwitchwars.menus.menu.SelectGameTypeMenu;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

public class ArenaCreator implements Listener {

  private static ArenaCreator creator;
  private final Map<GamePlayer, String> setup;


  public ArenaCreator() {
    this.setup = new HashMap<>();
    Bukkit.getPluginManager().registerEvents(this, RWitchWars.getWitchWars());
  }

  public static ArenaCreator getCreator() {
    if (creator == null) creator = new ArenaCreator();
    return creator;
  }

  @SneakyThrows
  public void createArena(GamePlayer gamePlayer, String name) {

    if (setup.containsKey(gamePlayer)) {
      // TODO make message
      return;
    }

    File file = new File(RWitchWars.getWitchWars().getDataFolder().getAbsolutePath() + "/data/arenas/" + name + ".yml");
    if (file.exists()) {
      // TODO make message
      return;
    }

    file.createNewFile();

    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    config.set("options.minimum", 2);
    config.set("options.enabled", false);
    config.set("name", name);
    config.set("displayname", name);
    config.save(file);
    setup.put(gamePlayer, name);
    World world = WorldController.getController().createWorld(name);
    world.getSpawnLocation().getBlock().setType(Material.STONE);
    new SelectGameTypeMenu(config, world, file).open(gamePlayer.getPlayer());
  }

  @SneakyThrows
  public void addGenerator(GamePlayer gamePlayer, String type, int level) {
    if (!setup.containsKey(gamePlayer)) {
      // TODO make message
      return;
    }

    File file = new File(RWitchWars.getWitchWars().getDataFolder().getAbsolutePath() + "/data/arenas/" + setup.get(gamePlayer) + ".yml");
    if (!file.exists()) {
      // TODO make message
      return;
    }

    GeneratorType generatorType = GeneratorType.getByRawName(type);
    if (generatorType == null) {
      // TODO make message
      Utils.sendMessage(gamePlayer.getPlayer(), "&cInvalid type! Valid types are: blood, dust and crystal");
      return;
    }

    if (level < 0) {
      // TODO make message
      return;
    }

    if (level > generatorType.getMaxLevel()) {
      // TODO
      return;
    }

    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    ConfigurationSection section = config.getConfigurationSection("generators");
    int size = section.getKeys(false).size();
    config.set("generators." + (size + 1) + ".location", gamePlayer.getLocation().getBlock().getLocation());
    config.set("generators." + (size + 1) + ".type", generatorType.name());
    config.set("generators." + (size + 1) + ".level", level);
    config.save(file);
    Utils.sendMessage(gamePlayer.getPlayer(), "&aSuccessfully added a generator with the type: " + type + " and level:" + level);
  }

  @SuppressWarnings("ConstantConditions")
  @SneakyThrows
  public void setSpawnLocation(GamePlayer gamePlayer, String team) {
    if (!setup.containsKey(gamePlayer)) {
      // TODO make message
      return;
    }

    File file = new File(RWitchWars.getWitchWars().getDataFolder().getAbsolutePath() + "/data/arenas/" + setup.get(gamePlayer) + ".yml");
    if (!file.exists()) {
      // TODO make message
      return;
    }

    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    if (config.getString("gametype").equals("SOLO") || config.getString("gametype").equals("DUO")) {

      TeamColor teamColor = TeamColor.getById(team);

      if (teamColor == null) {
        // TODO send message
        return;
      }

      config.set("teams." + teamColor.getIdentifier() + ".spawnLocation", gamePlayer.getLocation());
      config.save(file);
      // TODO edit message
      Utils.sendMessage(gamePlayer.getPlayer(), "&aSuccessfully set the spawnpoint for the team: " + teamColor.getDisplayName());
    } else if (config.getString("gametype").equals("TRIO") || config.getString("gametype").equals("SQUAD")) {
      // TODO
      TeamColor teamColor = TeamColor.getById(team);
      if (teamColor == null) {
        // TODO send message
        return;
      }
      String identifier = teamColor.getIdentifier();
      if ((!identifier.equalsIgnoreCase("yellow")) || (!identifier.equalsIgnoreCase("blue"))
          || (!identifier.equalsIgnoreCase("red")) || (!identifier.equalsIgnoreCase("green"))) {
        // TODO send message.
        return;
      };



    }

  }



}
