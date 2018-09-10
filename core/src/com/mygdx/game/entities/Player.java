package com.mygdx.game.entities;

import com.mygdx.game.components.Body;
import com.mygdx.game.components.Sprite;
import com.mygdx.game.systems.Collider;
import com.mygdx.game.systems.Physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;


public class Player extends GameObject implements Collider {
    float rateTimer = 0;
    float iFrameTimer = 0f;

    private float deltaTime;

    public Player(float posX, float posY) {
        super();
        super.setBody(new Body(Physics.getObject().getWorld(), posX, posY, 1f, 0.8f, "Player"));
        super.setSprite(new Sprite("gameObjects/Player.atlas", "Player", 1));
    }

}
