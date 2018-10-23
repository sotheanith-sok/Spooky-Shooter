package com.mygdx.game.components.Scripts;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.IsEnemyBulletComponent;
import com.mygdx.game.components.IsEnemyComponent;
import com.mygdx.game.components.NeedToRemoveComponent;
import com.mygdx.game.entities.Factory;

public class PlayerCollisionCallback implements CollisionCallback {

   @Override
   public void run(Entity thisObject, Entity otherObject) {
      if(otherObject.getComponent(IsEnemyComponent.class)!=null){
         thisObject.add(Factory.getFactory().getEngine().createComponent(NeedToRemoveComponent.class));
      }
      if(otherObject.getComponent(IsEnemyBulletComponent.class)!=null){
         thisObject.add(Factory.getFactory().getEngine().createComponent(NeedToRemoveComponent.class));
      }
   }
}
