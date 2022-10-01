package ga.justreddy.wiki.rwitchwars.storage;

import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import java.util.List;

public interface DataStorage {

  void createData();

  boolean createPlayerData(GamePlayer gamePlayer);

  void loadPlayerData(GamePlayer gamePlayer);

  void removePlayerData(GamePlayer gamePlayer);

  void savePlayerData(GamePlayer gamePlayer);

  void close();

}
