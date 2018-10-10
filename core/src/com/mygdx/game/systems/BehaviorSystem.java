package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.BehaviorComponent;
import com.mygdx.game.components.Scripts.Behavior;

public class BehaviorSystem extends IteratingSystem {
   private ComponentMapper<BehaviorComponent> bcm;
   public BehaviorSystem(){
      super(Family.all(BehaviorComponent.class).get());
      bcm=ComponentMapper.getFor(BehaviorComponent.class);
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
      BehaviorComponent behaviorComponent=bcm.get(entity);
      if (behaviorComponent.behavior!=null){
         behaviorComponent.behavior.runBehavior(entity,deltaTime);
      }
   }
}
