package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Utilities {
    //Use in RendeingSystem
    public static final float PPM=16.0f;
    public static final float FRUSTUM_WIDTH= Gdx.graphics.getWidth()/PPM;
    public static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight()/PPM;

    public static final float PIXELS_TO_METRES = 1.0f / PPM;

    public static Vector2 getScreenSizeInMeters(){
        Vector2 meterDimension= new Vector2(Gdx.graphics.getWidth()*PIXELS_TO_METRES,
                Gdx.graphics.getHeight()*PIXELS_TO_METRES);
        return meterDimension;
    }

    public static Vector2 getScreenSizeInPixel(){
        Vector2 pixelDimension= new Vector2(Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        return pixelDimension;
    }
    public static float PixelsToMeters(float pixelValue){
        return pixelValue*PIXELS_TO_METRES;
    }
    public static float MetersToPixels(float meterValue){
        return meterValue*PIXELS_TO_METRES;
    }

    //Use in
    public static final float MAX_STEP_TIME=1/60f;
}
