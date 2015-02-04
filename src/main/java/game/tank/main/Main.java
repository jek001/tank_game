package game.tank.main;

import game.tank.MainGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.File;

public class Main {


    public static void main(String[] args) throws SlickException {

        String path = System.getProperty("java.library.path");
        String newPath = "/home/jek/WORK/gamedev/tank_game/target/natives" + File.pathSeparator + path;
        System.setProperty("java.library.path", newPath);
        System.out.println(newPath);

        AppGameContainer appGameContainer = new AppGameContainer(new MainGame("My title !!"));
        appGameContainer.setDisplayMode(800, 600, false);
        appGameContainer.setTargetFrameRate(60);
        appGameContainer.start();
    }

}
