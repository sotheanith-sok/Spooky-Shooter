package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.Bag;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.components.IsBulletComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.entities.Factory;
import com.mygdx.game.utilities.Utilities;

public class BulletControlSystem extends IntervalSystem {
   ImmutableArray<Entity> entities;
   private ComponentMapper<MovementComponent> mm;
   private ComponentMapper<IsBulletComponent> im;

   public BulletControlSystem() {
      super(Utilities.MAX_STEP_TIME);
      mm = ComponentMapper.getFor(MovementComponent.class);
      im = ComponentMapper.getFor(IsBulletComponent.class);
   }

   @Override
   public void addedToEngine(Engine engine) {
      entities = engine.getEntitiesFor(Family.all(IsBulletComponent.class).get());
   }

   @Override
   protected void updateInterval() {
      // HELP HELP HELP HELP HELP
      for (Entity entity : entities) {
         MovementComponent mc = mm.get(entity);
//         if( Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ||
//                   Gdx.input.isKeyJustPressed(Input.Keys.BUTTON_R1)) {
//            Factory.getFactory().shoot();
//            mc.shot = true;
//         }
      }
      // HELP HELP HELP HELP HELP
   }
}