package com.gemserk.libgdx.tests.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gemserk.libgdx.tests.TestsApplicationListener;

public class Main {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Gemserk Tests";
		config.width = 800;
		config.height = 480;
		config.fullscreen = false;
		config.useGL20 = true;
		config.useCPUSynch = true;
		config.forceExit = true;
		config.vSyncEnabled = true;
		new LwjglApplication(new TestsApplicationListener(), config);
	}

}
