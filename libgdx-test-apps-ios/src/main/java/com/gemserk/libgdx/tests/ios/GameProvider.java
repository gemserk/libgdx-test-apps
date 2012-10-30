package com.gemserk.libgdx.tests.ios;

import com.badlogic.gdx.ApplicationListener;
import com.gemserk.libgdx.tests.TestsApplicationListener;

public class GameProvider {

	public static ApplicationListener createGame() {
		return new TestsApplicationListener();
	}

}
