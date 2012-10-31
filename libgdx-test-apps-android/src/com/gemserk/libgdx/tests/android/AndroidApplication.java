package com.gemserk.libgdx.tests.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gemserk.libgdx.tests.TestsApplicationListener;

public class AndroidApplication extends com.badlogic.gdx.backends.android.AndroidApplication {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useGL20 = true;
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = false;

		initialize(new TestsApplicationListener(), config);
	}

}
