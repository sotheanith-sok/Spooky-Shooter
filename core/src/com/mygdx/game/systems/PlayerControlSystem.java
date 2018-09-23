package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.utilities.Utilities;

public class PlayerControlSystem extends IntervalSystem {
   ImmutableArray<Entity> entities;
   private ComponentMapper<MovementComponent> mc;


   public PlayerControlSystem() {
      super(Utilities.MAX_STEP_TIME);
      mc = ComponentMapper.getFor(MovementComponent.class);
   }

   @Override
   public void addedToEngine(Engine engine) {
      entities = engine.getEntitiesFor(Family.all(MovementComponent.class).get());
   }

   @Override
   protected void updateInterval() {
      for (Entity entity : entities) {
         MovementComponent movementComponent = mc.get(entity);
         if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            movementComponent.moveLeft = true;
         if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            movementComponent.moveRight = true;
         if (Gdx.input.isKeyPressed(Input.Keys.UP))
            movementComponent.moveUp = true;
         if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            movementComponent.moveDown = true;
         if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) &&
             !Gdx.input.isKeyPressed(Input.Keys.RIGHT) &&
             !Gdx.input.isKeyPressed(Input.Keys.UP) &&
             !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movementComponent.moveLeft = false;
            movementComponent.moveRight = false;
            movementComponent.moveUp = false;
            movementComponent.moveDown = false;
         }
      }
   }
}
