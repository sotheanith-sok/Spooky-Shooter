package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.components.Scripts.CollisionCallback;

public class CollisionCallbackComponent implements Component {
   public CollisionCallback beginContactCallback;
   public CollisionCallback endContactCallback;
}
