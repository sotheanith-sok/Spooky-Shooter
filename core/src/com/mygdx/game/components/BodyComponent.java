package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

/**
 * This is a representation of physical body in our engine.
 */
public class BodyComponent implements Component {

   public Body body=null;

}
