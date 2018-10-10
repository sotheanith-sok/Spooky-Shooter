package com.mygdx.game.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SpookyShooter;
import com.mygdx.game.entities.Factory;
import com.mygdx.game.ui.IngameOverlay;

/**
 * This is the game screen. It has access to all the game play that will occur.
 */
public class GameScreen extends ScreenAdapter {
   /** Singleton for this GameScreen.*/
   private static GameScreen gameScreen;

   SpriteBatch batch;
   public IngameOverlay ui;
   long timer = 0;
   public int score0, score1, score2, score3 = 0;
   int numPlayers;
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
   public GameScreen(Game myGame, int playerCount) {
      this.myGame = myGame;
      engine = Factory.getFactory().getEngine();
      ui=new IngameOverlay(playerCount);
      Factory.getFactory().createEntities(playerCount);
      numPlayers = playerCount;
      gameScreen = this;
   }

   /**
    * Get this screen.
    *
    * @return GameScreen object
    */
   public static GameScreen getGameScreen() {
      return gameScreen;
   }

   /**
    *
    * @param delta time between current frame and last frame
    */
   @Override
   public void render(float delta) {
      engine.update(delta);
      timer += (delta * MathUtils.random(100));
      // The lower this
      // number   vv  the more often enemies spawn
      if (timer % 20 == 0)
         Factory.getFactory().spawnEnemy(MathUtils.random(10f,110f),MathUtils.random(30f,60f));
      ui.draw();
      if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
         System.out.println("Key press captured");
         ((SpookyShooter) myGame).changeScreen(4, numPlayers);
         this.dispose();
      }
   }

   /**
    * This method resets all Entities and Hitboxes
    *
    */
   @Override
   public void dispose(){
      Factory.getFactory().getEngine().removeAllEntities();
      Array<Body> array=new Array<Body>();
      Factory.getFactory().getWorld().getBodies(array);
      for(Body body:array){//kills hit boxes
         Factory.getFactory().getWorld().destroyBody(body);
      }
   }
}
