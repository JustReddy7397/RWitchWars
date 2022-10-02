package ga.justreddy.wiki.rwitchwars;

import ga.justreddy.wiki.rwitchwars.achievements.AchievementManager;
import ga.justreddy.wiki.rwitchwars.hooks.PlaceholderHook;
import ga.justreddy.wiki.rwitchwars.nms.INms;
import ga.justreddy.wiki.rwitchwars.controller.GameController;
import ga.justreddy.wiki.rwitchwars.controller.MapController;
import ga.justreddy.wiki.rwitchwars.controller.MessagesController;
import ga.justreddy.wiki.rwitchwars.dependency.DLoader;
import ga.justreddy.wiki.rwitchwars.dependency.base.Dependency;
import ga.justreddy.wiki.rwitchwars.listeners.GameListener;
import ga.justreddy.wiki.rwitchwars.listeners.PluginListener;
import ga.justreddy.wiki.rwitchwars.storage.DataStorage;
import ga.justreddy.wiki.rwitchwars.storage.SQLite;
import ga.justreddy.wiki.rwitchwars.tasks.GameTask;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class RWitchWars extends JavaPlugin {

  @Getter
  private static RWitchWars witchWars;

  @Getter
  private INms nms;

  @Getter
  DataStorage dataStorage;

  @Getter
  private AchievementManager achievementManager;

  private static final String VERSION = getVersion(Bukkit.getServer());

  @Override
  public void onLoad() {
    try{
      DLoader.getInstance().onLoad();
      DLoader.getInstance().load(new Dependency("SQLite", "3.34.0", "org.xerial", "sqlite-jdbc"));
      DLoader.getInstance().load(new Dependency("MongoDB-Driver", "3.12.11", "org.mongodb", "mongodb-driver"));
      DLoader.getInstance().load(new Dependency("MongoDB-Core", "3.12.11", "org.mongodb", "mongodb-driver-core"));
      DLoader.getInstance().load(new Dependency("MongoDB-Bson", "3.12.11", "org.mongodb", "bson"));
    }catch (ClassCastException | ExceptionInInitializerError ignored) {

    }
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    witchWars = this;
    Utils.sendConsole("&7[&dRWitchWars&7] &aLoading plugin...");
    Utils.sendConsole("&7[&dRWitchWars&7] &aFinding nms version...");
    try {
      nms = (INms) Class.forName("ga.justreddy.wiki.rwitchwars." + VERSION + "." + VERSION).newInstance();
      Utils.sendConsole("&7[&dRWitchWars&7] &aNMS version found: " + VERSION);
    }catch (Exception ex) {
      Utils.sendConsole("&7[&dRWitchWars&7] &cNMS version " + VERSION + " not supported! Shutting down plugin.");
      Bukkit.getPluginManager().disablePlugin(this);
      ex.printStackTrace();
      return;
    }
    Utils.sendConsole("&7[&dRWitchWars&7] &a Checking if PlaceholderAPI is enabled...");
    if (getServer().getPluginManager().isPluginEnabled(PlaceholderAPIPlugin.getInstance())) {
      Utils.sendConsole("&7[&dRWitchWars&7] &aPlaceholderAPI found, hooking...");
      new PlaceholderHook().register();
    } else {
      Utils.sendConsole("&7[&dRWitchWars&7] &cPlaceholderAPI not found, skipping...");
    }
    Utils.sendConsole("&7[&dRWitchWars&7] &aRegistering controllers...");
    registerControllers();
    Utils.sendConsole("&7[&dRWitchWars&7] &aConnecting to a database...");
    dataStorage = new SQLite();
    dataStorage.createData();
    Utils.sendConsole("&7[&dRWitchWars&7] &aStarting tasks...");
    new GameTask().runTaskTimer(this, 0, 20L);
    this.achievementManager = new AchievementManager();
    Utils.sendConsole("&7[&dRWitchWars&7] &aRegistering events...");
    getServer().getPluginManager().registerEvents(new GameListener(), this);
    getServer().getPluginManager().registerEvents(new PluginListener(), this);
    Utils.sendConsole("&7[&dRWitchWars&7] &aPlugin successfully loaded");
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic

    dataStorage.close();
  }

  private void registerControllers() {
    MapController.getController().start();
    GameController.getController().start();
    MessagesController.getController().start();
  }

  private static String getVersion(Server server) {
    final String packageName = server.getClass().getPackage().getName();
    return packageName.substring(packageName.lastIndexOf('.') + 1);
  }

}
