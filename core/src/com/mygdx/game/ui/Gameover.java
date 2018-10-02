package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.utilities.Utilities;


public class Gameover extends Stage {

   private String path= "GameOverScreen/PlayerScores.json";

   private Json json;
   private FileHandle handle;
   private Array<Player> players;
   private Skin skin;//initiate skin -jesse

   public Gameover (){

      skin = new Skin(Gdx.files.internal("skins/shade/skin/uiskin.json"));//-load skin - jesse
      Label label = new Label ("Game Over", skin, "title");
      label.setPosition(Utilities.MetersToPixels(Utilities.FRUSTUM_WIDTH/2),
              Utilities.MetersToPixels(Utilities.FRUSTUM_HEIGHT/2));
     label.setOrigin(label.getWidth()/2,label.getHeight()/2);
      this.addActor(label);
      json=new Json();
      handle = Gdx.files.local(path);
      players=new Array<Player>();
      if(!handle.exists()){
         handle.writeString("",false);
      }

   }




   /**
    * Write score data to Array.
    */
   private void writeScoreToFile(){
      String playerString=json.toJson(players);
      handle.writeString(playerString,false);
   }

   /**
    * Read from files and update Array
    */
   private void readScoreFromFile(){
      JsonValue jsonValue=new JsonReader().parse(handle);
      players.clear();
      for (JsonValue j :jsonValue.iterator()){
         players.add(new Player().setName(j.getString("name")).setTimestamp(j.getString("timestamp")).setScore(j.getInt("score")));
      }
   }


   /**
    * Internal data object used to represent player's score.
    */
   public class Player{
      private String name;
      private String timestamp;
      private int score;

      public String getName() {
         return name;
      }

      public Player setName(String name) {
         this.name = name;
         return this;
      }

      public String getTimestamp() {
         return timestamp;
      }

      public Player setTimestamp(String timestamp) {
         this.timestamp = timestamp;
         return this;
      }

      public int getScore() {
         return score;
      }

      public Player setScore(int score) {
         this.score = score;
         return this;
      }
      @Override
      public String toString(){
         return name+timestamp+score;
      }
   }
}
