package com.mygdx.game.entities;

import com.mygdx.game.components.Body;
import com.mygdx.game.components.Sprite;

//Testingasdfdafdfs ajsdlkfjalksdfjlkasjdlkfjlkzclvkzjlkcvjlzkcjvlkjzclkv
public class GameObject {
    private Sprite sprite;
    private Body body;

    public GameObject() {
        sprite = null;
        body = null;
    }

    public GameObject(Sprite sprite, Body body) {
        this.sprite = sprite;
        this.body = body;
    }

    /**
     * Get the sprite component of this object.
     *
     * @return sprite
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Set the sprite component of this object.
     *
     * @param sprite
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Set the body component of this object.
     *
     * @return body
     */
    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

}
