package game.tank;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.Map;

public class MenuState extends BasicGameState {

    private static final int MENU_STATE_ID = 1;

    private Image wallImage;
    private Image grassImage;
    private Image waterImage;

    private Map<Integer, Boolean> releasedKeysMap = new HashMap<>();

    @Override
    public int getID() {
        return MENU_STATE_ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        waterImage = new Image("/home/jek/WORK/gamedev/water.png");
        wallImage = new Image("/home/jek/WORK/gamedev/bricks.PNG");
        grassImage = new Image("/home/jek/WORK/gamedev/ttt.jpeg");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.clear();

        final int SIZE = 25;
        for (int i = 0; i < 16 * 2; i++) {
            for (int j = 0; j < 12 * 2; j++) {
                int rnd = (i + j) % 3;
                Image tmp = (rnd == 0) ? waterImage : (rnd == 1 ? wallImage : grassImage);
                tmp.draw(i * SIZE, j * SIZE, SIZE, SIZE);
            }
        }
        g.drawString("menu state", 100, 100);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();

        if (wasReleased(Input.KEY_ESCAPE)) {
            container.exit();
            return;
        }

        if (wasReleased(Input.KEY_ENTER)) {
            game.enterState(2);//main game
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
