package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.mygdx.game.entities.Factory;

public class EnemiesSpawnSystem extends IteratingSystem {


   Array<EnemySpawnData> enemySpawnData;
   public EnemiesSpawnSystem(){
      super(Family.all().get());
      enemySpawnData=new Array<EnemySpawnData>();
      enemySpawnData.add(new EnemySpawnData(2) {
         @Override
         public void spawn() {
            Factory.getFactory().spawnEnemy(MathUtils.random(10f,110f),MathUtils.random(30f,60f));
         }
      });

   }

   @Override
   public void update(float deltaTime){
      for(EnemySpawnData data: enemySpawnData){
         data.accumulator+=deltaTime;
         if(data.accumulator>=data.spawnRate){
            data.spawn();
            data.accumulator-=data.spawnRate;
         }
      }

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

   }

   abstract class EnemySpawnData{
      public float spawnRate; //How many enemies per second.
      public float accumulator;
      public EnemySpawnData(float spawnRate){
         this.spawnRate=1/spawnRate;
         accumulator=0;
      }
      public abstract void spawn();
   }
}
