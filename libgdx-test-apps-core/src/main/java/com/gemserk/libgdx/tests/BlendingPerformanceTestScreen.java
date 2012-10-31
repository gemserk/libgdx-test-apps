package com.gemserk.libgdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;

public class BlendingPerformanceTestScreen extends TestScreen {
	
	@Override
	public void create() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyUp(int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					BlendingPerformanceTestScreen.this.parent.backToSelection();
				}
				return super.keyUp(keycode);
			}
		});
	}
	
	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);		
	}
	
	@Override
	public void dispose() {

	}

}
