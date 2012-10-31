package com.gemserk.libgdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.tablelayout.BaseTableLayout;

public class BlendingPerformanceTestScreen extends TestScreen {

	TextureAtlas atlas;
	SpriteBatch spriteBatch;
	TextureAtlas skinAtlas;
	Skin skin;
	Stage stage;

	Sprite treesSprite;

	final float timeToHide = 2f;

	boolean blending;
	int renderTimes;
	float stageHideTimeout;

	@Override
	public void create() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		spriteBatch = new SpriteBatch();

		atlas = new TextureAtlas(Gdx.files.internal("data/images/images.atlas"));
		treesSprite = atlas.createSprite("trees");

		treesSprite.setSize(width, height);

		InputAdapter screenInputProcessor = new InputAdapter() {
			@Override
			public boolean keyUp(int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					BlendingPerformanceTestScreen.this.parent.backToSelection();
				}
				return super.keyUp(keycode);
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				// if stage invisible, then show it for a while...
				stageHideTimeout = timeToHide;
				stage.getRoot().setVisible(true);
				return super.touchUp(screenX, screenY, pointer, button);
			}
		};

		blending = true;
		renderTimes = 1;

		skinAtlas = new TextureAtlas(Gdx.files.internal("data/ui/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"), skinAtlas);

		stage = new Stage(width, height, false);
		Gdx.input.setInputProcessor(new InputMultiplexer(screenInputProcessor, stage));
		
		Table optionsContainer = new Window("Options", skin);
		
		optionsContainer.setSize(width * 0.8f, height * 0.5f);
		optionsContainer.setPosition(width * 0.1f, height * 0.4f);

		{
			// add some custom stuff
		}
		
		{
			optionsContainer.row();
			TextButton textButton = new TextButton("BACK", skin);
			
			textButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					BlendingPerformanceTestScreen.this.parent.backToSelection();
				}
			});
			
			optionsContainer.add(textButton).center().bottom().colspan(5).padLeft(10f).padRight(10f).expandX().fillX();
		}
		
		stage.addActor(optionsContainer);
		
		stage.getRoot().setVisible(false);
		stageHideTimeout = 0f;
	}

	@Override
	public void dispose() {
		atlas.dispose();
		spriteBatch.dispose();
		skinAtlas.dispose();
		skin.dispose();
		stage.dispose();
		atlas = null;
		spriteBatch = null;
		skinAtlas = null;
		skin = null;
		stage = null;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		for (int i = 0; i < renderTimes; i++)
			treesSprite.draw(spriteBatch);
		spriteBatch.end();
		stage.draw();
	}

	@Override
	public void update() {
		stageHideTimeout -= Gdx.graphics.getDeltaTime();
		if (stageHideTimeout < 0f)
			stage.getRoot().setVisible(false);
		stage.act();
	}

}
