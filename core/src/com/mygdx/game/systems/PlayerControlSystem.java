package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.TransformComponent;

public class PlayerControlSystem extends IntervalSystem {
   private ComponentMapper<MovementComponent> mc;
   ImmutableArray<Entity> entities;


   public PlayerControlSystem(float interval) {
      super(interval);
      mc=ComponentMapper.getFor(MovementComponent.class);
   }

   @Override
   public void addedToEngine(Engine engine){
      entities=engine.getEntitiesFor(Family.all(MovementComponent.class).get());
   }

   @Override
   protected void updateInterval() {
      for (Entity entity : entities) {
         MovementComponent movementComponent = mc.get(entity);
         if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movementComponent.moveLeft = true;
         } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movementComponent.moveRight = true;
         } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movementComponent.moveUp = true;
         } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movementComponent.moveDown = true;
         } else {
            movementComponent.moveLeft = false;
            movementComponent.moveRight = false;
            movementComponent.moveUp = false;
            movementComponent.moveDown = false;
         }
      }
   }
}
