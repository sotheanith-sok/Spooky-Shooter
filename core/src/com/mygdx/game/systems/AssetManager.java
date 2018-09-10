package com.mygdx.game.systems;

public class AssetManager {
    private static AssetManager manager;
    private com.badlogic.gdx.assets.AssetManager assetManager;

    private AssetManager() {
        assetManager = new com.badlogic.gdx.assets.AssetManager();
    }

    public static AssetManager getObject() {
        if (manager == null) {
            manager = new AssetManager();

        }
        return manager;
    }

    public void loadAssets() {

    }

    public com.badlogic.gdx.assets.AssetManager getAssetManager() {
        return assetManager;
    }
}
