package game.tank.util;

import org.newdawn.slick.geom.Shape;

public interface InputUtilI {

    boolean wasMouseButtonPressed(int buttonCode, Shape shape);

    boolean wasMouseButtonReleased(int buttonCode, Shape shape);

}
