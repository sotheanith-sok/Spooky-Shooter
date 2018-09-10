package com.mygdx.game.systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.GameObject;


public class Physics {
    private static Physics single_instance;

    private World world;

    private Physics() {
        world = new World(new Vector2(0, 0), true);
    }

    public static Physics getObject() {
        if (single_instance == null) {
            single_instance = new Physics();
        }
        return single_instance;
    }

    /**
     * Get the physic container.
     *
     * @return
     */
    public World getWorld() {
        return world;
    }

    /**
     * Add listeners to the physic engine
     *
     * @param contactListener listener
     */
    public void setContactListener(ContactListener contactListener) {
        world.setContactListener(contactListener);

    }


    public void cleanDeadBody() {
        Array<Body> bodies = new Array<Body>();
        Physics.getObject().getWorld().getBodies(bodies);
        for (com.badlogic.gdx.physics.box2d.Body body : bodies) {
            if (body.getUserData() != null) {
                Physics.getObject().getWorld().destroyBody(body);
            }

        }
    }

}
