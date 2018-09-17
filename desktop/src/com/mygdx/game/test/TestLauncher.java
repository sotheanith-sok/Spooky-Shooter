package com.mygdx.game.test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.TestShooter;

public class TestLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new TestShooter(), config);

        config.title = "This test is very spooky!!!";
        config.useGL30 = true;
        config.width = 1920;
        config.height = 1080;
        config.allowSoftwareMode = true;
        config.x = -1;
        config.y = -1;
        config.foregroundFPS = 60;
    }
}
