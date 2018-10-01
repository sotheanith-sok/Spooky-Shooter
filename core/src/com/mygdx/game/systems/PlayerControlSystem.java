package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.components.IsPlayerComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.utilities.Utilities;

public class PlayerControlSystem extends IntervalSystem {
   ImmutableArray<Entity> entities;
   private ComponentMapper<MovementComponent> mm;
   private ComponentMapper<IsPlayerComponent> im;


   public PlayerControlSystem() {
      super(Utilities.MAX_STEP_TIME);
      mm = ComponentMapper.getFor(MovementComponent.class);
      im = ComponentMapper.getFor(IsPlayerComponent.class);
   }

   @Override
   public void addedToEngine(Engine engine) {
      entities = engine.getEntitiesFor(Family.all(IsPlayerComponent.class).get());
   }

   @Override
   protected void updateInterval() {
      for (Entity entity : entities) {
         MovementComponent mc = mm.get(entity);
         IsPlayerComponent ic = im.get(entity);
         if(ic.isPlayer0) {
            mc.moveLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT);
            mc.moveRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
            mc.moveUp = Gdx.input.isKeyPressed(Input.Keys.UP);
            mc.moveDown = Gdx.input.isKeyPressed(Input.Keys.DOWN);
            mc.shot = Gdx.input.isKeyPressed(Input.Keys.SPACE) ||
                    Gdx.input.isKeyPressed(Input.Keys.BUTTON_R1);
         }
         else if(ic.isPlayer1) {
            mc.moveLeft = Gdx.input.isKeyPressed(Input.Keys.A);
            mc.moveRight = Gdx.input.isKeyPressed(Input.Keys.D);
            mc.moveUp = Gdx.input.isKeyPressed(Input.Keys.W);
            mc.moveDown = Gdx.input.isKeyPressed(Input.Keys.S);
            mc.shot = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ||
                    Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT);
         }
      }
   }
}
