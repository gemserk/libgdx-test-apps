package com.gemserk.libgdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class BlendingPerformanceTestScreen extends TestScreen {
	
	@Override
	public void create() {

	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);		
	}
	
	@Override
	public void dispose() {

	}

}
