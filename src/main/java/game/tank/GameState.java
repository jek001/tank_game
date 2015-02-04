package game.tank;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.Map;

public class GameState extends BasicGameState {

    private Map<Integer, Boolean> releasedKeysMap = new HashMap<>();

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.clear();
        g.drawString("game state", 200, 200);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (wasReleased(Input.KEY_ENTER)) {
            game.enterState(1);//main game
        }

        cleanReleaseKeyMap();
    }

    private boolean wasReleased(int keyCode) {
        Boolean wasReleased = releasedKeysMap.get(keyCode);
        if (wasReleased == null || (!wasReleased)) {
            return false;
        }
        releasedKeysMap.put(keyCode, Boolean.FALSE);
        return true;
    }

    @Override
    public void keyReleased(int key, char c) {
        releasedKeysMap.put(key, Boolean.TRUE);
    }

    private void cleanReleaseKeyMap() {
        releasedKeysMap.clear();
    }
}
