package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

/**
 * This is the game screen. It has access to all the game play that will occur.
 */
public class GameScreen extends ScreenAdapter {

   /**
    *This is the reference to the game object.
    */
   private Game myGame;

   /**
    *Constructor for this screen
    * @param myGame
    */
   public GameScreen(Game myGame) {
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
