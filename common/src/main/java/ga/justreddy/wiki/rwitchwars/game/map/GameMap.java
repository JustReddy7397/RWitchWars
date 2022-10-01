package ga.justreddy.wiki.rwitchwars.game.map;

import ga.justreddy.wiki.rwitchwars.controller.MapController;
import ga.justreddy.wiki.rwitchwars.controller.WorldController;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;

public class GameMap extends AbstractGameMao {

    private final File sourceWorldFolder;
    private File activeWorldFolder;

    private World bukkitWorld;

    public GameMap(File worldFolder, String worldName, boolean loadOnInit) {
        this.sourceWorldFolder = new File(worldFolder, worldName);

        if (loadOnInit) load();
    }


    @SneakyThrows
    @Override
    public boolean load() {
        if (isLoaded()) return true;
        activeWorldFolder = new File(MapController.getController().getWorldFolder(), sourceWorldFolder.getName());
        FileUtil.copy(sourceWorldFolder, activeWorldFolder);
        bukkitWorld = WorldController.getController().createWorld(activeWorldFolder.getName());
        return isLoaded();
    }

    @Override
    public void unload() {
        if (bukkitWorld == null) return;
        Bukkit.unloadWorld(bukkitWorld, false);
        if (activeWorldFolder == null) return;
        FileUtil.delete(activeWorldFolder);
        bukkitWorld = null;
        activeWorldFolder = null;
    }

    @Override
    public boolean isLoaded() {
        return bukkitWorld != null;
    }

    @Override
    public boolean restoreFromSource() {
        unload();
        return load();
    }

    @Override
    public World getWorld() {
        return bukkitWorld;
    }
}
