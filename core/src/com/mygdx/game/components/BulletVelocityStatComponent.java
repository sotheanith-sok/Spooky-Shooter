package com.mygdx.game.components;
import com.badlogic.ashley.core.Component;

public class BulletVelocityStatComponent implements Component {
   public float movingSpeed = 20f;
   public float rof = 0.1f;
   public float timer = 0;
}
