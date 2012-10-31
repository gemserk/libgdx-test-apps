package com.gemserk.libgdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class BlendingPerformanceTestScreen extends TestScreen {

	TextureAtlas atlas;
	SpriteBatch spriteBatch;

	Sprite treesSprite;

	@Override
	public void create() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		
		spriteBatch = new SpriteBatch();

		atlas = new TextureAtlas(Gdx.files.internal("data/images/images.atlas"));
		treesSprite = atlas.createSprite("trees");
		
		treesSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
	public void dispose() {
		atlas.dispose();
		spriteBatch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		treesSprite.draw(spriteBatch);
		spriteBatch.end();
	}

}
