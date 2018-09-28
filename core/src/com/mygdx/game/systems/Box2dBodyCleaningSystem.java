package com.mygdx.game.systems;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.Utilities;


public class Box2dBodyCleaningSystem extends IntervalSystem {
   private World world;
   private Array<Body>bodies;

   public Box2dBodyCleaningSystem(World world) {
      super(Utilities.MAX_STEP_TIME/2);
      this.world=world;
      bodies=new Array<Body>();
   }

   /**
    * The processing logic of the system should be placed here.
    */
   @Override
   protected void updateInterval() {
      if(!world.isLocked()){
         world.getBodies(bodies);
         for (Body body:bodies){
            if(!body.isActive()){
               world.destroyBody(body);
            }
         }
      }
   }
}
