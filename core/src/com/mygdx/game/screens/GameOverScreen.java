package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.ui.Gameover;

/**
 * This is the game over screen. It will be shown at the end of a game session.
 */
public class GameOverScreen extends ScreenAdapter {

   /**
    * This is the reference to the game object.
    */
   private Game myGame;
   private Gameover gameOver;

   /**
    * Constructor for this screen
    *
    * @param myGame
    */
   public GameOverScreen(Game myGame) {
      this.myGame = myGame;
     gameOver= new Gameover();
   }

   /**
    * This the main loop of this screen.
    *
    * @param delta time between current frame and last frame
    */
   @Override
   public void render(float delta) {
      gameOver.draw();
   }
}
