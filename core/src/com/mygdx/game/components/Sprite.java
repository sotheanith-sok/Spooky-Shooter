package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.systems.AssetManager;

public class Sprite {
    private TextureAtlas textureAtlas;
    private TextureRegion texture;

    public Sprite(String path, String name, int index) {
        textureAtlas = AssetManager.getObject().getAssetManager().get(path, TextureAtlas.class);
        texture = textureAtlas.findRegion(name, index);
    }

    public TextureRegion getTexture() {
        return texture;
    }
}
