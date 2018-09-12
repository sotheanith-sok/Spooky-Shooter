package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

/**
 *This is a title screen. It will be the first screen that will be display on the start of the application
 */
public class TitleScreen extends ScreenAdapter {

   /**
    *This is the reference to the game object.
    */
   private Game myGame;

   /**
    *Constructor for this screen
    * @param myGame
    */
   public TitleScreen(Game myGame) {
      this.myGame = myGame;
   }

   /**
    *This the main loop of this screen.
    * @param delta time between current frame and last frame
    */
   @Override
   public void render(float delta) {

   }
}
