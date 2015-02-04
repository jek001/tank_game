package game.tank.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public interface GameObject {

    void update(GameContainer container, StateBasedGame game, int delta);

    void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException;

}
