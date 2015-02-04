package game.tank.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.StateBasedGame;

public class MenuGameState extends GameStateAbs {

    private static final int BACKGROUND_PADDING = 0;
    private static final int BACKGROUND_IMAGE_PADDING = 50;
    private static final Color BACKGROUND_COLOR = new Color(76, 153, 0);
    private static final Color REGULAR_BUTTON_COLOR = new Color(123, 67, 31);
    private static final Color BUTTON_COLOR_WITH_MOUSE = new Color(12, 117, 78);
    private static final Color REGULAR_TEXT_COLOR = new Color(64, 75, 219);
    private static final Color TEXT_COLOR_WITH_MOUSE = new Color(34, 25, 15);

    private static final int START_BUTTON_WIDTH = 250;
    private static final int START_BUTTON_HEIGHT = 75;

    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 75;

    private static final int BUTTONS_MARGIN = 25;
    private static final float CORNER_RADIUS = 25.0f;

    private static final String START_BUTTON_LABEL = "START";
    private static final String EXIT_BUTTON_LABEL = "CLOSE";

    private Rectangle startButtonRectangle;
    private Rectangle exitButtonRectangle;
    private Rectangle backgroundRectangle;

    private int width;
    private int height;
    private Image backgroundImage;
    private Font menuFont;

    private boolean mouseIsOnStartButton = false;
    private boolean mouseIsOnExitButton = false;

    public MenuGameState(int stateID) {
        super(stateID);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        width = container.getWidth();
        height = container.getHeight();

        backgroundRectangle = new Rectangle(
                BACKGROUND_PADDING,
                BACKGROUND_PADDING,
                width - (BACKGROUND_PADDING * 2),
                height - (BACKGROUND_PADDING * 2)
        );

        startButtonRectangle = new RoundedRectangle(
                (width - START_BUTTON_WIDTH) / 2,
                (height - START_BUTTON_HEIGHT - BUTTONS_MARGIN - EXIT_BUTTON_HEIGHT) / 2,
                START_BUTTON_WIDTH,
                START_BUTTON_HEIGHT,
                CORNER_RADIUS
        );

        exitButtonRectangle = new RoundedRectangle(
                (width - EXIT_BUTTON_WIDTH) / 2,
                (height - START_BUTTON_HEIGHT - BUTTONS_MARGIN - EXIT_BUTTON_HEIGHT) / 2 +
                        START_BUTTON_HEIGHT + BUTTONS_MARGIN,
                EXIT_BUTTON_WIDTH,
                EXIT_BUTTON_HEIGHT,
                CORNER_RADIUS
        );

        backgroundImage = new Image("tank.png");
        menuFont = loadFont("OpenSans-Regular.ttf", 64.0f);
    }

    @Override
    protected void doUpdate(GameContainer container, StateBasedGame game, int delta) {
        Input input = container.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        mouseIsOnStartButton = mouseIsOnShape(startButtonRectangle, mouseX, mouseY);
        mouseIsOnExitButton = mouseIsOnShape(exitButtonRectangle, mouseX, mouseY);

        if (wasMouseReleasedOnShape(Input.MOUSE_LEFT_BUTTON, exitButtonRectangle)) {
            container.exit();
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(BACKGROUND_COLOR);
        g.fill(backgroundRectangle);

        backgroundImage.draw(
                BACKGROUND_IMAGE_PADDING,
                BACKGROUND_IMAGE_PADDING,
                width - (BACKGROUND_IMAGE_PADDING * 2),
                height - (BACKGROUND_IMAGE_PADDING * 2)
        );

        drawButton(startButtonRectangle, menuFont, START_BUTTON_LABEL, g, mouseIsOnStartButton);
        drawButton(exitButtonRectangle, menuFont, EXIT_BUTTON_LABEL, g, mouseIsOnExitButton);
    }

    private void drawButton(Rectangle rectangle, Font font, String text, Graphics g, boolean isMouseOnTheButton) {
        g.setColor(isMouseOnTheButton ? BUTTON_COLOR_WITH_MOUSE : REGULAR_BUTTON_COLOR);
        g.fill(rectangle);
        g.setColor(isMouseOnTheButton ? TEXT_COLOR_WITH_MOUSE : REGULAR_TEXT_COLOR);
        drawTextOnButton(font, rectangle, text, g);
    }

    private void drawTextOnButton(Font font, Rectangle buttonRectangle, String text, Graphics g) {
        int textHeight = font.getHeight(text);
        int textWidth = font.getWidth(text);

        float rectangleTopLeftX = buttonRectangle.getX();
        float rectangleTopLeftY = buttonRectangle.getY();

        float rectangleWidth = buttonRectangle.getWidth();
        float rectangleHeight = buttonRectangle.getHeight();

        float rectangleDownRightX = rectangleTopLeftX + rectangleWidth;
        float rectangleDownRightY = rectangleTopLeftY + rectangleHeight;

        g.setFont(font);
        g.drawString(
                text,
                (rectangleDownRightX - rectangleTopLeftX - textWidth) / 2 + rectangleTopLeftX,
                (rectangleDownRightY - rectangleTopLeftY - textHeight) / 2 + rectangleTopLeftY
        );
    }

    private boolean mouseIsOnShape(Rectangle rectangle, int mouseX, int mouseY) {
        return rectangle.contains(mouseX, mouseY);
    }

}
