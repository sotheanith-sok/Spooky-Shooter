package com.mygdx.game.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.entities.Factory;
import com.mygdx.game.ui.IngameOverlay;

/**
 * This is the game screen. It has access to all the game play that will occur.
 */
public class GameScreen extends ScreenAdapter {

   SpriteBatch batch;
   IngameOverlay ui;
   float count=0;
   /**
    * This is the reference to the game object.
    */
   private Game myGame;
   private PooledEngine engine;

   /**
    * Constructor for this screen
    *
    * @param myGame
    */
   public GameScreen(Game myGame) {
      this.myGame = myGame;
      engine = Factory.getFactory().getEngine();
      ui=new IngameOverlay(4);
   }

   /**
    * This the main loop of this screen.
    *
    * @param delta time between current frame and last frame
    */
   @Override
   public void render(float delta) {
      engine.update(delta);
      count+=delta*MathUtils.random(1,100);
      ui.updateScore(MathUtils.random(0,3),  count);
      ui.draw();
   }

}
