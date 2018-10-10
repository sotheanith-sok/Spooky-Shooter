package com.mygdx.game.components.Scripts;


import com.badlogic.ashley.core.Entity;

public interface Behavior {
   /**
    * Run this behavior every frame
    * @param entity entity that this behavior belong to
    * @param deltaTime time since the last frame call.
    */
    void runBehavior(Entity entity, float deltaTime);
}
