package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.mygdx.game.components.*;
import com.mygdx.game.components.Scripts.EnemyCollisionCallback;
import com.mygdx.game.components.Scripts.InvisibleWallCollisionCallback;
import com.mygdx.game.components.Scripts.PlayerCollisionCallback;
import com.mygdx.game.systems.*;
import com.mygdx.game.systems.CollisionCallbackSystem;
import com.mygdx.game.systems.PhysicsDebugSystem;
import com.mygdx.game.systems.PhysicsSystem;
import com.mygdx.game.systems.RenderingSystem;
import com.mygdx.game.utilities.BehaviorBuilder;
import com.mygdx.game.utilities.ParticleEffectManager;
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

   private ParticleEffectManager particleEffectManager;

   public Entity player;
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
       //Load systems into engine
      loadSystemsIntoEngine();
      loadParticleEffects();
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
      //Loading normal texture
      assetManager.load("GameScreen/Player.atlas", TextureAtlas.class);
      assetManager.load("GameScreen/Laser.atlas",TextureAtlas.class);
      assetManager.load("GameScreen/Enemies.atlas",TextureAtlas.class);
      assetManager.load("GameScreen/Bullet.atlas",TextureAtlas.class);

      //Loading assets
      ParticleEffectLoader.ParticleEffectParameter particleEffectParameter= new ParticleEffectLoader.ParticleEffectParameter();
      particleEffectParameter.atlasFile="GameScreen/Effects.atlas";
      assetManager.load("GameScreen/Effects/CandyCornExplode.p",ParticleEffect.class,particleEffectParameter);
      assetManager.load("GameScreen/Effects/SmokeTrail.p",ParticleEffect.class,particleEffectParameter);
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
   public Entity createPlayer(String player, float posx, float posy, int playerNum) {
      Entity entity = engine.createEntity();
      entity.add(engine.createComponent(MovementComponent.class));
      entity.add(engine.createComponent(PlayerVelocityStatComponent.class));
      entity.add(engine.createComponent(TransformComponent.class));
      entity.add(engine.createComponent(BodyComponent.class));
      entity.add(engine.createComponent(TextureComponent.class));
      entity.add(engine.createComponent(IsPlayerComponent.class));
      entity.add(engine.createComponent(SteeringComponent.class));
      entity.add(engine.createComponent(CollisionCallbackComponent.class));
      entity.getComponent(CollisionCallbackComponent.class).beginContactCallback=Pools.get(PlayerCollisionCallback.class).obtain();
      entity.add(engine.createComponent(IsLaserComponent.class));
      entity.getComponent(IsPlayerComponent.class).playerNum = playerNum;
      entity.add(engine.createComponent(BulletVelocityStatComponent.class));
      entity.getComponent(TextureComponent.class).textureRegionAnimation = createTexture("GameScreen/Player.atlas", player, 5f);
      entity.getComponent(BodyComponent.class).body = createBody(player, posx, posy, 0.7f);
      entity.getComponent(TransformComponent.class).scale.x = 1f;
      entity.getComponent(TransformComponent.class).scale.y = 1f;
      entity.getComponent(BodyComponent.class).body.setUserData(entity);
      applyCollisionFilter(entity.getComponent(BodyComponent.class).body, Utilities.CATEGORY_PLAYER, Utilities.MASK_PLAYER,false);
      entity.getComponent(SteeringComponent.class).body=entity.getComponent(BodyComponent.class).body;
      this.player=entity;

      return entity;
   }


   /**
    * Call this method to create Bullet entity
    *
    * @return a bullet.
    */
   public Entity shoot(float x, float y, int playerNum) {
      Entity entity = engine.createEntity();
      entity.add(engine.createComponent(MovementComponent.class));
      entity.add(engine.createComponent(BulletVelocityStatComponent.class));
      entity.add(engine.createComponent(TransformComponent.class));
      entity.add(engine.createComponent(BodyComponent.class));
      entity.add(engine.createComponent(TextureComponent.class));
      entity.add(engine.createComponent(IsBulletComponent.class));
      entity.getComponent(IsBulletComponent.class).playerNum = playerNum;
      entity.getComponent(TextureComponent.class).textureRegionAnimation = createTexture("GameScreen/Player.atlas", "Player_1", 1);
      entity.getComponent(BodyComponent.class).body = createBody("Player_1", x, y, 0.35f);
      entity.getComponent(TransformComponent.class).scale.x = 0.5f;
      entity.getComponent(TransformComponent.class).scale.y = 0.5f;
      entity.getComponent(BodyComponent.class).body.setUserData(entity);
       applyCollisionFilter(entity.getComponent(BodyComponent.class).body, Utilities.CATEGORY_PLAYER_PROJECTILE, Utilities.MASK_PLAYER_PROJECTILE,true);
       engine.addEntity(entity);

       //Add particle to bullet
      entity.add(engine.createComponent(ParticleEffectComponent.class));
      Entity particle=createParticleEffect(ParticleEffectManager.SMOKETRIAL,entity.getComponent(BodyComponent.class));
      particle.getComponent(ParticleEffectDataComponent.class).isLooped=true;
      entity.getComponent(ParticleEffectComponent.class).effect= particle;
       return entity;
   }

   public Entity laser(float x, float y, int playerNum) {
      Entity entity = engine.createEntity();
      entity.add(engine.createComponent(MovementComponent.class));
      entity.add(engine.createComponent(BulletVelocityStatComponent.class));
      entity.add(engine.createComponent(TransformComponent.class));
      entity.add(engine.createComponent(BodyComponent.class));
      entity.add(engine.createComponent(TextureComponent.class));
      entity.add(engine.createComponent(IsLaserComponent.class));


      entity.getComponent(IsLaserComponent.class).playerNum=playerNum;
      entity.getComponent(TextureComponent.class).textureRegionAnimation = createTexture("GameScreen/Laser.atlas", "Laser_0", 15);

      entity.getComponent(BodyComponent.class).body = createBody("Laser_0", x, y, 70);
      entity.getComponent(TransformComponent.class).scale.x = 0.2f;
      entity.getComponent(TransformComponent.class).scale.y = 105f;
      entity.getComponent(TransformComponent.class).position.z=-1;
      entity.add(engine.createComponent(CollisionCallbackComponent.class));

      entity.getComponent(BodyComponent.class).body.setUserData(entity);
      applyCollisionFilter(entity.getComponent(BodyComponent.class).body, Utilities.CATEGORY_PLAYER_SPECIAL_PROJECTILE,
       Utilities.MASK_PLAYER_SPECIAL_PROJECTILE,true);
      engine.addEntity(entity);
      return entity;
   }

   /**
    * Call this method to create an Enemy entity
    *
    * @return an enemy.
    */
   public Entity spawnEnemy1(float x, float y, String behavior) {
      Entity entity = engine.createEntity();
      entity.add(engine.createComponent(EnemyStatsComponent.class));
      entity.add(engine.createComponent(TransformComponent.class));
      entity.add(engine.createComponent(BodyComponent.class));
      entity.add(engine.createComponent(TextureComponent.class));
      entity.add(engine.createComponent(IsEnemyComponent.class));
      entity.add(engine.createComponent(CollisionCallbackComponent.class));

      entity.getComponent(CollisionCallbackComponent.class).beginContactCallback =
       Pools.get(EnemyCollisionCallback.class).obtain();
      entity.getComponent(TextureComponent.class).textureRegionAnimation = createTexture("GameScreen/Enemies.atlas", "Enemies_0", 5);
      entity.getComponent(BodyComponent.class).body = createBody("Enemies_0", x, y, 2);
      entity.getComponent(BodyComponent.class).body.setType(BodyDef.BodyType.DynamicBody);
      entity.getComponent(TransformComponent.class).scale.x = 1f;
      entity.getComponent(TransformComponent.class).scale.y = 1f;
      entity.getComponent(BodyComponent.class).body.setUserData(entity);
      applyCollisionFilter(entity.getComponent(BodyComponent.class).body,
       Utilities.CATEGORY_ENEMY, Utilities.MASK_ENEMY,true);

      entity.add(engine.createComponent(SteeringComponent.class));
      entity.getComponent(SteeringComponent.class).body=entity.getComponent(BodyComponent.class).body;
      entity.getComponent(EnemyStatsComponent.class).health = 900;
      entity.add(engine.createComponent(BehaviorComponent.class));
      entity.getComponent(BehaviorComponent.class).behaviors= BehaviorBuilder.getInstance().load(behavior);
      engine.addEntity(entity);

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
      engine.addEntityListener(new RenderingSystem(spriteBatch, camera));
      engine.addSystem(new PhysicsSystem(world));
      engine.addSystem(new RenderingSystem(spriteBatch, camera));
      engine.addSystem(new PhysicsDebugSystem(world, camera));
      engine.addSystem(new PlayerControlSystem());
      engine.addSystem(new PlayerVelocitySystem());
      engine.addSystem(new EntityRemovingSystem(world,engine));
      engine.addSystem(new BulletVelocitySystem());
      engine.addSystem(new SteeringSystem());
      engine.addSystem(new EnemiesSpawnSystem());
      engine.addSystem(new BehaviorSystem());
      engine.addSystem(new LaserSystem());
      new CollisionCallbackSystem(world);
      engine.addSystem(new DetectEndGameSystem());
      engine.addSystem(new EnemyFireSystem());
      engine.addSystem(new ParticleEffectSystem(spriteBatch,camera));
   }

   /**
    * Call this function to create TextureRegion
    *
    * @param path  to Atlas file
    * @param name  name of the texture in the atlas
    * @param fps how many frame should be playing
    * @return TextureRegion
    */
   public Animation<TextureRegion> createTexture(String path, String name, float fps) {
      TextureAtlas atlas = assetManager.get(path, TextureAtlas.class);

      return new Animation<TextureRegion>(1f/fps,atlas.findRegions(name), Animation.PlayMode.LOOP);
   }

   /**
    * Call this function to create Box2D body
    *
    * @param nameOfBody of the body
    * @return Box2D body
    */
   public Body createBody(String nameOfBody, float posX, float posY,  float scale) {
      BodyDef bodyDef = new BodyDef();
      bodyDef.type = BodyDef.BodyType.DynamicBody;
      bodyDef.position.set(posX, posY);
      Body body = world.createBody(bodyDef);
      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.density = 1;
      fixtureDef.isSensor=true;
      bodyEditorLoader.attachFixture(body, nameOfBody, fixtureDef, scale);
      body.setFixedRotation(true);
      return body;
   }

   /**
    * Call this method to create entities for the start of the game.
    */
   public void createEntities(int playerCount) {
      for(int i = 0; i < playerCount; i++){
         engine.addEntity(createPlayer("Player_2", 10 + (i * 10), 10, i));
      }

      createInvisibleWall(Utilities.FRUSTUM_WIDTH*0/5,0-0.5f,Utilities.FRUSTUM_WIDTH*5/5,Utilities.FRUSTUM_HEIGHT+1f,1);


   }

   /**
    * Apply collision filter to this body
    * @param body which body to apply to
    * @param categoryBits which category is this body belong to. LOOK AT UTILITIES for more detail.
    * @param maskingBits whiich category should this body collide with. LOOK AT UTILITIES for more detail.
    */
   public void applyCollisionFilter(Body body, short categoryBits, short maskingBits, boolean isSensor) {
      Array<Fixture> fixtures = body.getFixtureList();
      for (Fixture fixture : fixtures) {
         Filter filter = fixture.getFilterData();
         filter.categoryBits = categoryBits;
         filter.maskBits = maskingBits;
         fixture.setSensor(isSensor);
         fixture.setFilterData(filter);
      }
   }

   private Entity createAnInvisibleWall(float x, float y, float scale){
       Entity entity = engine.createEntity();
       entity.add(engine.createComponent(BodyComponent.class));
       entity.add(engine.createComponent(CollisionCallbackComponent.class));
       entity.getComponent(CollisionCallbackComponent.class).beginContactCallback=Pools.get(InvisibleWallCollisionCallback.class).obtain();
       entity.getComponent(BodyComponent.class).body = createBody("Bullet_1",x, y, scale);
       entity.getComponent(BodyComponent.class).body.setUserData(entity);
       entity.getComponent(BodyComponent.class).body.setType(BodyDef.BodyType.StaticBody);
       applyCollisionFilter(entity.getComponent(BodyComponent.class).body,
               Utilities.CATEGORY_ENVIRONMENT, Utilities.MASK_ENVIRONMENT,false);
        return  entity;
   }

    /**
     *Call this method to create boundary that delete bullet and prevent player from passing through it.
     * @param x x-coordinate of the boundary wall in meter
     * @param y y-coordinate of the boundary wall in meter
     * @param width width of the boundary wall in meter
     * @param height height of the boundary wall in meter
     * @param scale how big should each block of the wall be. Default value in 1 meter
     */
   private void createInvisibleWall(float x, float y, float width, float height, float scale){
       //Top and Buttom
       for (float i=x; i<=x+width;i=i+scale){
           createAnInvisibleWall(i,y,scale);
           createAnInvisibleWall(i,y+height,scale);
       }
       //Left and right wall
       for (float i = y+scale; i<=y+height;i=i+scale){
           createAnInvisibleWall(x,i,scale);
           createAnInvisibleWall(x+width,i,scale);
       }
   }

   public Entity createEnemyBullet (float x, float y){
      Entity entity = engine.createEntity();
      entity.add(engine.createComponent(TransformComponent.class));
      entity.add(engine.createComponent(BodyComponent.class));
      entity.add(engine.createComponent(TextureComponent.class));
      entity.add(engine.createComponent(IsEnemyBulletComponent.class));
      entity.getComponent(TextureComponent.class).textureRegionAnimation = createTexture("GameScreen/Bullet.atlas", "Bullet_0", 8);
      entity.getComponent(BodyComponent.class).body = createBody("Player_1", x, y, 1);
      entity.getComponent(TransformComponent.class).scale.x = 1f;
      entity.getComponent(TransformComponent.class).scale.y = 1f;
      entity.getComponent(BodyComponent.class).body.setUserData(entity);
      applyCollisionFilter(entity.getComponent(BodyComponent.class).body, Utilities.CATEGORY_ENEMY_PROJECTILE, Utilities.MASK_ENEMY_PROJECTILE, true);
      engine.addEntity(entity);
      return entity;
   }

   public void loadParticleEffects(){
      particleEffectManager=new ParticleEffectManager();
      particleEffectManager.addParticleEffect(
              ParticleEffectManager.CANDYCORNEXPLOSION,assetManager.get("GameScreen/Effects/CandyCornExplode.p",
                      ParticleEffect.class),
              1/5f);
      particleEffectManager.addParticleEffect(
              ParticleEffectManager.SMOKETRIAL,assetManager.get("GameScreen/Effects/SmokeTrail.p",
                      ParticleEffect.class),
              1/5f);
   }

   public Entity createParticleEffect(int type, float x, float y){
      Entity entity=engine.createEntity();
      ParticleEffectDataComponent particleEffectComponent=engine.createComponent(ParticleEffectDataComponent.class);
      particleEffectComponent.particleEffect=particleEffectManager.getPooledParticleEffect(type);
      particleEffectComponent.particleEffect.setPosition(x,y);
      entity.add(particleEffectComponent);
      engine.addEntity(entity);
      return entity;
   }

   public Entity createParticleEffect(int type, BodyComponent bodyComponent){
      return createParticleEffect(type,bodyComponent,0f,0f);
   }

   public Entity createParticleEffect(int type, BodyComponent bodyComponent, float xOffSet, float yOffset) {
      Entity entity = engine.createEntity();
      ParticleEffectDataComponent particleEffectComponent = engine.createComponent(ParticleEffectDataComponent.class);
      particleEffectComponent.particleEffect = particleEffectManager.getPooledParticleEffect(type);
      particleEffectComponent.particleEffect.setPosition(bodyComponent.body.getPosition().x, bodyComponent.body.getPosition().y);
      particleEffectComponent.particleEffect.getEmitters().first().setAttached(true); //manually attach for testing
      particleEffectComponent.xOffset = xOffSet;
      particleEffectComponent.yOffset = yOffset;
      particleEffectComponent.isAttached = true;
      particleEffectComponent.attachedBody = bodyComponent.body;
      entity.add(particleEffectComponent);
      engine.addEntity(entity);
      return entity;
   }

}
