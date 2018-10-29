package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.NeedToRemoveComponent;

public class EntityPoolingSystem extends IteratingSystem {

   private World world;
   private Engine engine;
   private ComponentMapper<BodyComponent> bm;
   public EntityPoolingSystem(World world, Engine engine){

      super(Family.all(NeedToRemoveComponent.class).get());
      this.world=world;
      this.engine=engine;
      bm=ComponentMapper.getFor(BodyComponent.class);
   }

   /**
    * This method is called on every entity on every update call of the EntitySystem. Override this to implement your system's
    * specific processing.
    *
    * @param entity    The current Entity being processed
    * @param deltaTime The delta time between the last and current frame
    */
   @Override
   protected void processEntity(Entity entity, float deltaTime) {
      if(!world.isLocked()){

      }
   }



}
