package ga.justreddy.wiki.rwitchwars.tasks;

import ga.justreddy.wiki.rwitchwars.controller.GameController;
import ga.justreddy.wiki.rwitchwars.game.Game;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    @Override
    public void run() {
        GameController.getController().getGames().values()
                .forEach(Game::onCountDown);
    }
}
