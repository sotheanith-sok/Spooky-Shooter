package com.mygdx.game.utilities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.Scripts.Behavior;
import com.mygdx.game.components.SteeringComponent;

import javax.sound.sampled.FloatControl;


public class BehaviorBuilder {
   private static BehaviorBuilder instance;

   public static BehaviorBuilder getInstance(){
      if(instance==null){
         instance=new BehaviorBuilder();
      }
      return instance;
   }
   private BehaviorBuilder(){

   }

   public Array<Behavior> load(String path){
      Array<Behavior> behaviors=new Array<Behavior>();
      FileHandle fileHandle= Gdx.files.internal(path);
      String file = fileHandle.readString();
      String [] commands=file.split("\\n");
      for (int i =0; i< commands.length;i++){
         String[] command=commands[i].split("\\s+");
         if(command[0].equals("move")){
            behaviors.add(move(Float.valueOf(command[1]),Float.valueOf(command[2])));
         }else if (command[0].equals("shot")){
            behaviors.add(shoot());
         }else if(command[0].equals("sleep")){
            behaviors.add(sleep(Float.valueOf(command[1])));
         }else if (command[0].equals("wander")){
            behaviors.add(wander(Float.valueOf(command[1])));
         }
      }
      return behaviors;
   }

   private Behavior move(float posX, float posY){
      return new Behavior(posX, posY,0) {
         @Override
         public void setBehavior(Entity entity) {
            entity.getComponent(SteeringComponent.class).steeringBehavior=
                    SteeringPresets.getArrive(entity.getComponent(SteeringComponent.class),this.posX,this.posY);
         }

         @Override
         public boolean isDone(Entity entity, float deltaTime) {
            Body body=entity.getComponent(SteeringComponent.class).body;
            return Math.abs(body.getPosition().x - this.posX) < 0.5f && Math.abs(body.getPosition().y - this.posY) < 0.5f;
         }
      };
   }
   private Behavior shoot(){
      return new Behavior() {
         @Override
         public void setBehavior(Entity entity) {

         }

         @Override
         public boolean isDone(Entity entity, float deltaTime) {
            return true;
         }
      };
   }
   private Behavior sleep(float time){
      return new Behavior(0,0,time) {
         private float accumulator =0;
         @Override
         public void setBehavior(Entity entity) {
            if(!entity.getComponent(BodyComponent.class).body.getLinearVelocity().isZero(0.01f))
               entity.getComponent(BodyComponent.class).body.setLinearVelocity(0,0);
         }

         @Override
         public boolean isDone(Entity entity, float deltaTime) {
            accumulator +=deltaTime;
            if(accumulator >=time){
               accumulator=0;
               return true;
            }
            return false;
         }
      };
   }
   private Behavior wander(float time){
      return new Behavior(0,0,time) {
         private float accumulator =0;
         private boolean applied=false;
         @Override
         public void setBehavior(Entity entity) {
            if(!applied){
               entity.getComponent(SteeringComponent.class).steeringBehavior=
                       SteeringPresets.getWander(entity.getComponent(SteeringComponent.class));
               applied=true;
            }
         }

         @Override
         public boolean isDone(Entity entity, float deltaTime) {
            accumulator +=deltaTime;
            if(accumulator >=time){
               accumulator=0;
               applied=false;
               return true;
            }
            return false;
         }
      };
   }
}
