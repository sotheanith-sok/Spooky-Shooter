package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.components.Scripts.CollisionCallback;

public class CollisionCallbackComponent implements Component , Pool.Poolable {
   public CollisionCallback beginContactCallback=null;
   public CollisionCallback endContactCallback=null;

    /**
     * Resets the object for reuse. Object references should be nulled and fields may be set to default values.
     */
    @Override
    public void reset() {
        beginContactCallback=null;
        endContactCallback=null;
    }
}
