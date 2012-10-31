package com.gemserk.libgdx.tests;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TestsApplicationListener implements ApplicationListener {

	TestScreen currentTestScreen;
	TestScreen selectorTestScreen;

	TextureAtlas atlas;
	BitmapFont font;
	SpriteBatch spriteBatch;

	@Override
	public void create() {
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
		if (currentTestScreen != null)
			currentTestScreen.render();
		spriteBatch.begin();
		font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, Gdx.graphics.getHeight() - 20);
		spriteBatch.end();
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
