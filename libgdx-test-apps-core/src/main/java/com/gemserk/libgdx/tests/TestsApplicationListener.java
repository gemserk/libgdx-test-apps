package com.gemserk.libgdx.tests;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gemserk.libgdx.utils.gl.BasicGraphicsWrapper;
import com.gemserk.libgdx.utils.gl.GL20BasicWrapper;

public class TestsApplicationListener implements ApplicationListener {

	TestScreen currentTestScreen;
	TestScreen selectorTestScreen;

	TextureAtlas atlas;
	BitmapFont font;
	SpriteBatch spriteBatch;

	@Override
	public void create() {
		Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glDisable(GL20.GL_BLEND);
		Gdx.gl.glDepthMask(true);

		final GL20BasicWrapper gl20 = new GL20BasicWrapper(Gdx.gl20) {

			boolean depthTestEnabled = false;
			boolean blendEnabled = false;
			boolean depthMask = false;

			@Override
			public void glEnable(int cap) {
				if (cap == GL20.GL_DEPTH_TEST) {
					if (depthTestEnabled)
						return;
					depthTestEnabled = true;
				} else 
					if (cap == GL20.GL_BLEND) {
					if (blendEnabled)
						return;
					blendEnabled = true;
				}
				super.glEnable(cap);
			}

			@Override
			public void glDisable(int cap) {
				if (cap == GL20.GL_DEPTH_TEST) {
					if (!depthTestEnabled)
						return;
					depthTestEnabled = false;
				} else
					if (cap == GL20.GL_BLEND) {
					if (!blendEnabled)
						return;
					blendEnabled = false;
				}
				super.glDisable(cap);
			}

			@Override
			public void glDepthMask(boolean flag) {
				if (flag && depthMask)
					return;
				else if (!flag && !depthMask)
					return;
				depthMask = flag;
				super.glDepthMask(flag);
			}

		};
		
		Gdx.graphics = new BasicGraphicsWrapper(Gdx.graphics) {
			@Override
			public GL20 getGL20() {
				return gl20;
			}

			@Override
			public GLCommon getGLCommon() {
				return gl20;
			}
		};
		
		Gdx.gl20 = Gdx.graphics.getGL20();
		Gdx.gl = Gdx.graphics.getGLCommon();

		selectorTestScreen = new TestSelectionScreen();
		setTestScreen(selectorTestScreen);

		atlas = new TextureAtlas(Gdx.files.internal("data/ui/uiskin.atlas"));
		font = new BitmapFont(Gdx.files.internal("data/ui/default.fnt"), atlas.createSprite("default"), false);
		spriteBatch = new SpriteBatch();
	}

	@Override
	public void dispose() {
		if (currentTestScreen != null)
			currentTestScreen.dispose();
		currentTestScreen = null;
		spriteBatch.dispose();
		spriteBatch = null;
		atlas.dispose();
		font.dispose();
		atlas = null;
		font = null;
	}

	@Override
	public void resize(int width, int height) {
		if (currentTestScreen != null)
			currentTestScreen.resize(width, height);
	}

	@Override
	public void render() {
		if (currentTestScreen != null) {
			currentTestScreen.render();
			currentTestScreen.update();
		}
		spriteBatch.begin();
		font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, Gdx.graphics.getHeight() - 20);
		spriteBatch.end();
		int glError = Gdx.gl.glGetError();
		if (glError != 0) { 
			System.out.println(glError);
		}
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	public void backToSelection() {
		setTestScreen(selectorTestScreen);
	}

	public void setTestScreen(final TestScreen testScreen) {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				if (currentTestScreen != null)
					currentTestScreen.dispose();
				testScreen.setParent(TestsApplicationListener.this);
				testScreen.create();
				TestsApplicationListener.this.currentTestScreen = testScreen;
			}
		});
	}

}
