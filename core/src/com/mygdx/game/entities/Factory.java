package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PlayerVelocityStatComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.systems.CollisionCallbackSystem;
import com.mygdx.game.systems.PhysicsDebugSystem;
import com.mygdx.game.systems.PhysicsSystem;
import com.mygdx.game.systems.RenderingSystem;
import com.mygdx.game.utilities.Utilities;

public class Factory {


    /**
     * A singleton to this object.
     */
    private static Factory factory;

    /**
     * Manager of all assets.
     */
    private AssetManager assetManager;

    /**
     * Pooled Engine of Ashely. It used to keep tracks of entities, components, and systems.
     */
    private PooledEngine engine;

    /**
     * World of Box2D. It keeps track of all box2d bodies
     */
    private World world;

    /**
     * Use to draw on the screen.
     */
    private SpriteBatch spriteBatch;

    private OrthographicCamera camera;

    /**
     * Get a singleton Factory object.
     * @return Factory object
     */
    public static Factory getFactory(){
       if(factory==null){
            factory=new Factory();
        }
        return factory;

    }

    /**
     * Default Constructor
     */
    private Factory(){
       assetManager=new AssetManager(); //Declare AssetManager
       loadAssets();// Load assets

        world=new World(Vector2.Zero,true);//Declare World

        spriteBatch=new SpriteBatch();//Declare SpriteBatch

        camera=new OrthographicCamera(Utilities.FRUSTUM_WIDTH,Utilities.FRUSTUM_HEIGHT);
        camera.position.set(Utilities.FRUSTUM_WIDTH/2f,Utilities.FRUSTUM_HEIGHT/2f,0);

        engine=new PooledEngine(); //Ashely engine
        loadSystemsIntoEngine(); //Load systems into engine
    }

    /**
     * Get access to the AssetManager
     * @return AssetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Load predetermine assets into AssetManager.
     */
    private void loadAssets(){

    }

    /**
     * Call this function to access World of Box2D
     * @return Box2D World
     */
    public World getWorld() {
        return world;
    }

    /**
     * Call this method to access SpriteBatch
     * @return SpriteBatch.
     */
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    /**
     * Call this method to create Player entity
     * @return a player.
     */
   public Entity createPlayer() {
      Entity entity=engine.createEntity();
      entity.add(engine.createComponent(MovementComponent.class));
      entity.add(engine.createComponent(PlayerVelocityStatComponent.class));
      entity.add(engine.createComponent(TransformComponent.class));
      entity.add(engine.createComponent(BodyComponent.class));
      BodyDef bodyDef = new BodyDef();
      bodyDef.type = BodyDef.BodyType.KinematicBody;
      bodyDef.position.set(0, 0);
      entity.getComponent(BodyComponent.class).body = world.createBody(bodyDef);
      entity.getComponent(BodyComponent.class).body.setUserData(entity);
      return entity;
   }

    /**
     * Access Orthographic Camera  for this game.
     * @return Orthographic Camera
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Get the engine of Ashely
     * @return pooled engine.
     */
    public PooledEngine getEngine() {
        return engine;
    }

    /**
     * Load systems into the Ashley engine.
     */
    private void loadSystemsIntoEngine(){
        engine.addSystem(new RenderingSystem(spriteBatch,camera));
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world,camera));
        new CollisionCallbackSystem(world);
    }
}
