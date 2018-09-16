package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.SpookyShooter;

/**
 * This is the game screen. It has access to all the game play that will occur.
 */
public class GameScreen extends ScreenAdapter {

   /**
    *This is the reference to the game object.
    */
   private Game myGame;
   SpriteBatch batch;
   /**
    *Constructor for this screen
    * @param myGame
    */
   public GameScreen(Game myGame) {
      this.myGame = myGame;
      batch = new SpriteBatch();
   }

   /**
    *This the main loop of this screen.
    * @param delta time between current frame and last frame
    */
   @Override
   public void render(float delta) {
      Gdx.gl.glClearColor(255, 0, 0, 0);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
   }

}
