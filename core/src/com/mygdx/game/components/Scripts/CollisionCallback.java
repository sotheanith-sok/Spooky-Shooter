package com.mygdx.game.components.Scripts;


import com.badlogic.ashley.core.Entity;

public interface CollisionCallback {
   void run(Entity thisObject, Entity otherObject);
}
