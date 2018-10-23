package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.EnemyStatsComponent;
import com.mygdx.game.entities.Factory;
import com.mygdx.game.utilities.Utilities;

public class EnemyFireSystem extends IntervalSystem {

   ImmutableArray<Entity> entities;

   private ComponentMapper<EnemyStatsComponent> escm;
   private ComponentMapper<BodyComponent> bcm;
   public EnemyFireSystem() {
      super(Utilities.MAX_STEP_TIME);
      escm=ComponentMapper.getFor(EnemyStatsComponent.class);
      bcm=ComponentMapper.getFor(BodyComponent.class);
   }

   @Override
   public void addedToEngine(Engine engine) {
     entities= engine.getEntitiesFor(Family.all(EnemyStatsComponent.class, BodyComponent.class).get());
   }
   /**
    * The processing logic of the system should be placed here.
    */
   @Override
   protected void updateInterval() {
      for(Entity entity: entities){
         EnemyStatsComponent es=escm.get(entity);
         BodyComponent bc=bcm.get(entity);
         es.timer+=Utilities.MAX_STEP_TIME;
         if(es.shoot==true && es.timer>=es.rof){
            Entity bullet =Factory.getFactory().createEnemyBullet(bc.body.getPosition().x, bc.body.getPosition().y);
            bullet.getComponent(BodyComponent.class).body.setLinearVelocity(0f,-es.speed);
            es.timer=0;
         }
      }
   }
}
