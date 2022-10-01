package ga.justreddy.wiki.rwitchwars.controller;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.cosmetics.Messages;
import ga.justreddy.wiki.rwitchwars.cosmetics.messages.DefaultMessages;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;

public class MessagesController {

  private static MessagesController controller;
  private final Map<String, Messages> data;
  private final File folder;

  public static MessagesController getController() {
    if (controller == null) controller = new MessagesController();
    return controller;
  }

  public MessagesController() {
    this.folder = new File(RWitchWars.getWitchWars().getDataFolder().getAbsolutePath() + "/messages");
    if (!this.folder.exists()) this.folder.mkdir();
    this.data = new HashMap<>();
  }


  public void start() {
    data.put("default", new DefaultMessages());
    File[] dances = folder.listFiles();
    if (dances == null) return;
    for (File file : dances) {
      if (!file.getName().endsWith(".jar")) continue;
      String name = file.getName().replace(".jar", "");
      if (data.containsKey(name)) continue;
      register(name, file);
    }
  }

  @SneakyThrows
  public void register(String name, File file) {
      Messages messages = Utils.findClass(file, Messages.class).getConstructor().newInstance();
      data.put(name, messages);
  }

  public void reload() {
    data.clear();
    start();
  }

  public Messages getById(int id) {
    return data.values().stream().filter(messages -> messages.getId() == id).findFirst().orElse(null);
  }

}
