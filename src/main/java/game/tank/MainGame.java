package game.tank;

import game.tank.state.MenuGameState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGame extends StateBasedGame {

    public MainGame(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        this.addState(new MenuGameState(1));
//        this.addState(new GameState());
    }
}
