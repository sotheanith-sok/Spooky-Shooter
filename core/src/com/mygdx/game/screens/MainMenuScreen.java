package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This is the main menu. It will show all options available to players.
 */
public class MainMenuScreen extends ScreenAdapter {

   SpriteBatch batch;
   /**
    * This is the reference to the game object.
    */
   private Game myGame;

   /**
    * Constructor for this screen
    *
    * @param myGame
    */
   public MainMenuScreen(Game myGame) {
      this.myGame = myGame;
      batch = new SpriteBatch();
   }

   /**
    * This the main loop of this screen.
    *
    * @param delta time between current frame and last frame
    */
   @Override
   public void render(float delta) {
      Gdx.gl.glClearColor(255, 0, 255, 0);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
   }
}
