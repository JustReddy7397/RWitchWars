package ga.justreddy.wiki.rwitchwars.game.map;

import org.bukkit.World;

public abstract class AbstractGameMao {

    public abstract boolean load();

    public abstract void unload();

    public abstract boolean isLoaded();
    
    public abstract boolean restoreFromSource();

    public abstract World getWorld();

}
