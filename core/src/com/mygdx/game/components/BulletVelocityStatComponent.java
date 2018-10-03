package com.mygdx.game.components;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class BulletVelocityStatComponent implements Component, Pool.Poolable {
   public float movingSpeed = 20f;
   public float rof = 0.1f;
   public float timer = 0;

    /**
     * Resets the object for reuse. Object references should be nulled and fields may be set to default values.
     */
    @Override
    public void reset() {
        movingSpeed = 20f;
       rof = 0.1f;
        timer = 0;
    }
}
