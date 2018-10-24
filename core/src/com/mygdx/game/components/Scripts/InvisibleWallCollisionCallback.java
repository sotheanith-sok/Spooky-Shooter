package com.mygdx.game.components.Scripts;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.components.IsBulletComponent;
import com.mygdx.game.components.IsEnemyBulletComponent;
import com.mygdx.game.components.NeedToRemoveComponent;
import com.mygdx.game.entities.Factory;

public class InvisibleWallCollisionCallback implements CollisionCallback, Pool.Poolable {
    private ComponentMapper<IsBulletComponent> isBulletComponentComponentMapper= ComponentMapper.getFor(IsBulletComponent.class);
    private ComponentMapper<IsEnemyBulletComponent>isEnemyBulletComponentComponentMapper=ComponentMapper.getFor(IsEnemyBulletComponent.class);
    @Override
    public void run(Entity thisObject, Entity otherObject) {
        if(isBulletComponentComponentMapper.get(otherObject)!=null){
            otherObject.add(Factory.getFactory().getEngine().createComponent(NeedToRemoveComponent.class));

        }
        if(isEnemyBulletComponentComponentMapper.get(otherObject)!=null){
           otherObject.add(Factory.getFactory().getEngine().createComponent(NeedToRemoveComponent.class));
        }
    }

   /**
    * Resets the object for reuse. Object references should be nulled and fields may be set to default values.
    */
   @Override
   public void reset() {

   }
}
