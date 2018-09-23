package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PlayerVelocityStatComponent;
import com.mygdx.game.utilities.Utilities;

public class PlayerVelocitySystem extends IntervalSystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<MovementComponent> mm;
    private ComponentMapper<BodyComponent> bm;
    private ComponentMapper<PlayerVelocityStatComponent> pm;

    public PlayerVelocitySystem() {
        super(Utilities.MAX_STEP_TIME);
        mm = ComponentMapper.getFor(MovementComponent.class);
        bm = ComponentMapper.getFor(BodyComponent.class);
        pm = ComponentMapper.getFor(PlayerVelocityStatComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine){
        entities=engine.getEntitiesFor(Family.all(BodyComponent.class,MovementComponent.class).get());
    }

    /**
     * The processing logic of the system should be placed here.
     */
    @Override
    protected void updateInterval() {
        for(Entity entity: entities){
            BodyComponent bC= bm.get(entity);
            MovementComponent mC = mm.get(entity);
            PlayerVelocityStatComponent pVC = pm.get(entity);
            if(mC.moveLeft)
                bC.body.setLinearVelocity(-pVC.movingSpeed, bC.body.getLinearVelocity().y);
            if(mC.moveRight)
                bC.body.setLinearVelocity( pVC.movingSpeed, bC.body.getLinearVelocity().y);
            if(mC.moveUp)
                bC.body.setLinearVelocity(bC.body.getLinearVelocity().x,  pVC.movingSpeed);
            if(mC.moveDown)
                bC.body.setLinearVelocity(bC.body.getLinearVelocity().x, -pVC.movingSpeed);
            if (!mC.moveLeft && !mC.moveRight && !mC.moveUp && !mC.moveDown)
               bC.body.setLinearVelocity(0, 0);
        }
    }
}
