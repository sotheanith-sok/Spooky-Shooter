package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.components.*;
import com.mygdx.game.systems.*;
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

   /**
    * Camera
    */
   private OrthographicCamera camera;

   /**
    * Loader for hitbox
    */

   private BodyEditorLoader bodyEditorLoader;

   /**
    * Default Constructor
    */
   private Factory() {
      assetManager = new AssetManager(); //Declare AssetManager
      loadAssets();// Load assets

      world = new World(Vector2.Zero, true);//Declare World

      spriteBatch = new SpriteBatch();//Declare SpriteBatch

      //Set up camera
      camera = new OrthographicCamera(Utilities.FRUSTUM_WIDTH, Utilities.FRUSTUM_HEIGHT);
      camera.position.set(Utilities.FRUSTUM_WIDTH / 2f, Utilities.FRUSTUM_HEIGHT / 2f, 0);

      bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("GameScreen/HitboxData.json"));//Declare hitbox loader

      engine = new PooledEngine(); //Ashely engine
      loadSystemsIntoEngine(); //Load systems into engine
      createEntities();


   }

   /**
    * Get a singleton Factory object.
    *
    * @return Factory object
    */
   public static Factory getFactory() {
      if (factory == null) {
         factory = new Factory();
      }
      return factory;

   }

   /**
    * Get access to the AssetManager
    *
    * @return AssetManager
    */
   public AssetManager getAssetManager() {
      return assetManager;
   }

   /**
    * Load predetermine assets into AssetManager.
    */
   private void loadAssets() {
      assetManager.load("GameScreen/Player.atlas", TextureAtlas.class);
      assetManager.finishLoading();
   }

   /**
    * Call this function to access World of Box2D
    *
    * @return Box2D World
    */
   public World getWorld() {
      return world;
   }

   /**
    * Call this method to access SpriteBatch
    *
    * @return SpriteBatch.
    */
   public SpriteBatch getSpriteBatch() {
      return spriteBatch;
   }

   /**
    * Call this method to create Player entity
    *
    * @return a player.
    */
   public Entity createPlayer() {
      Entity entity = engine.createEntity();
      entity.add(engine.createComponent(MovementComponent.class));
      entity.add(engine.createComponent(PlayerVelocityStatComponent.class));
      entity.add(engine.createComponent(TransformComponent.class));
      entity.add(engine.createComponent(BodyComponent.class));
      entity.add(engine.createComponent(TextureComponent.class));
      entity.add(engine.createComponent(IsPlayerComponent.class));

      entity.getComponent(TextureComponent.class).textureRegion = createTexture("GameScreen/Player.atlas", "Player_1", 0);
      entity.getComponent(BodyComponent.class).body = createBody("Player_1", 1);
      entity.getComponent(TransformComponent.class).scale.x = 1f;
      entity.getComponent(TransformComponent.class).scale.y = 1f;
      entity.add(engine.createComponent(CollisionCallbackComponent.class));
      entity.getComponent(BodyComponent.class).body.setUserData(entity);
      return entity;
   }

   /**
    * Access Orthographic Camera  for this game.
    *
    * @return Orthographic Camera
    */
   public OrthographicCamera getCamera() {
      return camera;
   }

   /**
    * Get the engine of Ashely
    *
    * @return pooled engine.
    */
   public PooledEngine getEngine() {
      return engine;
   }

   /**
    * Load systems into the Ashley engine.
    */
   private void loadSystemsIntoEngine() {
      engine.addSystem(new RenderingSystem(spriteBatch, camera));
      engine.addSystem(new PhysicsSystem(world));
      engine.addSystem(new PhysicsDebugSystem(world, camera));
      engine.addSystem(new PlayerControlSystem());
      engine.addSystem(new PlayerVelocitySystem());
      new CollisionCallbackSystem(world);
   }

   /**
    * Call this function to create TextureRegion
    *
    * @param path  to Atlas file
    * @param name  name of the texture in the atlas
    * @param index index of texture
    * @return TextureRegion
    */
   public TextureRegion createTexture(String path, String name, int index) {
      TextureAtlas ta = assetManager.get(path, TextureAtlas.class);
      return ta.findRegion(name, index);
   }

   /**
    * Call this function to create Box2D body
    *
    * @param nameOfBody of the body
    * @return Box2D body
    */
   public Body createBody(String nameOfBody, float scale) {
      BodyDef bodyDef = new BodyDef();
      bodyDef.type = BodyDef.BodyType.DynamicBody;
      bodyDef.position.set(10, 10);
      Body body = world.createBody(bodyDef);
      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.density = 1;
      bodyEditorLoader.attachFixture(body, nameOfBody, fixtureDef, scale);
      return body;
   }

   /**
    * Call this method to create entities for the start of the game.
    */
   public void createEntities() {
      engine.addEntity(createPlayer());
   }
}
