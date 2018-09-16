package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.TitleScreen;

import java.util.Scanner;

/**
 * This is the entry point to the game.
 * @author Agile Whisperers
 * @version 0.0.1
 */
public class TestShooter extends Game {

    /**
     * Called when the {@link Application} is first created.
     */
    @Override
    public void create() {
        System.out.println("Press a key to select a game screen to launch:" +
                "\n1. Title" +
                "\n2. Main Menu" +
                "\n3. Game" +
                "\n4. Game Ogre Screen");
        changeScreen(new Scanner(System.in).nextInt());
    }

    /**
     * This function allows screen to control which screen it will be translate to.
     * @param choice
     */
    public void changeScreen(int choice) {
        switch (choice) {
            case 1:
                setScreen(new TitleScreen(this));
                break;
            case 2:
                setScreen(new MainMenuScreen(this));
                break;
            case 3:
                setScreen(new GameScreen(this));
                break;
            case 4:
                setScreen(new GameOverScreen(this));
                break;
        }
    }
}
