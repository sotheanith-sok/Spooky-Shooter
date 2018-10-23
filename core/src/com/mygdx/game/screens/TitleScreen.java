package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.SpookyShooter;
import com.mygdx.game.utilities.Utilities;

/**
 * This is a title screen. It will be the first screen that will be display on the start of the application
 */
public class TitleScreen extends ScreenAdapter {
   SpriteBatch batch;
   Texture img;
   /**
    * This is the reference to the game object.
    */
   private Game myGame;


   /**
    * Constructor for this screen
    *
    * @param myGame
    */
   public TitleScreen(Game myGame) {
      this.myGame = myGame;
      batch = new SpriteBatch();
      img = new Texture("GameScreen/Gfx/firstscreen.jpg");
      System.out.println(Utilities.FRUSTUM_WIDTH);
      System.out.println(Utilities.FRUSTUM_HEIGHT);
   }

   /**
    * This is the main loop of this screen.
    *
    * @param delta time between current frame and last frame
    */
   @Override
   public void render(float delta) {
      Gdx.gl.glClearColor(0, 0, 0, 0);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      batch.begin();
      batch.draw(img, Gdx.graphics.getWidth() / 2 - img.getWidth() / 2, Gdx.graphics.getHeight() / 2 - img.getHeight() / 2);
      batch.end();
      if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
         System.out.println("Key press captured");
         ((SpookyShooter) myGame).changeScreen(2, 0);
      }
   }
}
