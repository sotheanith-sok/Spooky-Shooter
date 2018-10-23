package com.mygdx.game.components.Scripts;


import com.badlogic.ashley.core.Entity;

public abstract class Behavior {
   protected float posX,posY, time;
   public Behavior(){
      posX=0;
      posY=0;
      time=0;
   }
   public Behavior(float posX, float posY, float time){
      this.posX=posX;
      this.posY=posY;
      this.time=time;
   }
   /**
    * Run this behavior every frame
    * @param entity entity that this behavior belong to
    */
    public abstract void setBehavior(Entity entity);
    public abstract boolean isDone(Entity entity, float deltaTime);
}
