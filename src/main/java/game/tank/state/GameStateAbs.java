package game.tank.state;

import game.tank.util.InputUtilI;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GameStateAbs extends BasicGameState {

    private final int stateID;
    private final Map<Integer, Boolean> releasedKeysMap = new HashMap<>();
    private final Map<Integer, Boolean> pressedKeysMap = new HashMap<>();

    private final Map<Integer, List<Vector2f>> releasedPoints = new HashMap<>();
    private final Map<Integer, List<Vector2f>> pressedPoints = new HashMap<>();

    private InputUtilI inputUtil = new InputUtil();

    protected GameStateAbs(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        doUpdate(container, game, delta);
        cleanKeysMaps();
        cleanMousePoints();
    }

    protected abstract void doUpdate(GameContainer container, StateBasedGame game, int delta);

    @Override
    public void keyReleased(int key, char c) {
        releasedKeysMap.put(key, Boolean.TRUE);
    }

    @Override
    public void keyPressed(int key, char c) {
        pressedKeysMap.put(key, Boolean.TRUE);
    }

    protected boolean wasKeyReleased(int keyCode) {
        Boolean wasReleased = releasedKeysMap.get(keyCode);
        return (wasReleased != null) && wasReleased;
    }

    protected boolean wasKeyPressed(int keyCode) {
        Boolean wasPressed = pressedKeysMap.get(keyCode);
        return (wasPressed != null) && wasPressed;
    }

    private void cleanKeysMaps() {
        releasedKeysMap.clear();
        pressedKeysMap.clear();
    }

    private void cleanMousePoints() {
        releasedPoints.clear();
        pressedPoints.clear();
    }

    protected Font loadFont(String fontRef, float size) throws SlickException {
        try {
            InputStream stream = ResourceLoader.getResourceAsStream(fontRef);
            java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, stream);
            return new TrueTypeFont(font.deriveFont(size), true);
        } catch (Exception e) {
            throw new SlickException(e.getMessage(), e);
        }
    }

    protected boolean wasMouseReleasedOnShape(int button, Shape shape){
        return shapeContainsAnyPoint(shape, releasedPoints.get(button));
    }

    protected boolean wasMousePressedOnShape(int button, Shape shape) {
        return shapeContainsAnyPoint(shape, pressedPoints.get(button));
    }

    private boolean shapeContainsAnyPoint(Shape shape, List<Vector2f> pointsList) {
        return pointsList != null &&
                pointsList.stream().anyMatch(point -> shape.contains(point.getX(), point.getY()));
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        releasedPoints.computeIfAbsent(button, buttonCode -> new ArrayList<>());
        List<Vector2f> pointsList = releasedPoints.get(button);
        pointsList.add(new Vector2f(x, y));
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        pressedPoints.computeIfAbsent(button, buttonCode -> new ArrayList<>());
        List<Vector2f> pointsList = pressedPoints.get(button);
        pointsList.add(new Vector2f(x, y));
    }

    protected InputUtilI getInputUtil() {
        return inputUtil;
    }

    private class InputUtil implements InputUtilI {

        @Override
        public boolean wasMouseButtonPressed(int buttonCode, Shape shape) {
            return GameStateAbs.this.wasMousePressedOnShape(buttonCode, shape);
        }

        @Override
        public boolean wasMouseButtonReleased(int buttonCode, Shape shape) {
            return GameStateAbs.this.wasMousePressedOnShape(buttonCode, shape);
        }
    }
}
