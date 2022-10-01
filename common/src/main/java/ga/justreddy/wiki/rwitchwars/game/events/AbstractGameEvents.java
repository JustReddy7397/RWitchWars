package ga.justreddy.wiki.rwitchwars.game.events;

import ga.justreddy.wiki.rwitchwars.game.Game;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractGameEvents {

    protected Game game;
    protected String name;
    protected boolean enabled;
    protected int time;

    public AbstractGameEvents(Game game, boolean enabled) {
        this.game = game;
        this.enabled = enabled;
    }

    public abstract void start();

    public abstract void stop();

    public void update() {
        if (time > 0) time--;
    }

}
