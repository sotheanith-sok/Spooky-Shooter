package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

/**
 *This is the game over screen. It will be shown at the end of a game session.
 */
public class GameOverScreen extends ScreenAdapter {

   /**
    *This is the reference to the game object.
    */
   private Game myGame;

   /**
    *Constructor for this screen
    * @param myGame
    */
   public GameOverScreen(Game myGame) {
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
