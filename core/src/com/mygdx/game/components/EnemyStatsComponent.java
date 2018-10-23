package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyStatsComponent implements Component, Pool.Poolable {

   public float rof = 1f;
   public boolean shoot=false;
   public float timer=0f;
   public float speed=10f;
   /**
    * Resets the object for reuse. Object references should be nulled and fields may be set to default values.
    */
   @Override
   public void reset() {
      rof=1f;
      shoot=false;
      timer=0;
      speed=10f;
   }
}
