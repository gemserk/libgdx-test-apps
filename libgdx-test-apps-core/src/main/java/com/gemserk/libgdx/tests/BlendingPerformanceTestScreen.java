package com.gemserk.libgdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BlendingPerformanceTestScreen extends TestScreen {

	TextureAtlas atlas1, atlas2;
	SpriteBatch spriteBatch;
	TextureAtlas skinAtlas;
	Skin skin;
	Stage stage;

	Sprite treesSprite;

	final float timeToHide = 5f;

	boolean blending;
	boolean depthFunc;
	boolean multipleBatches;
	boolean twoTextures;

	int renderTimes;
	float stageHideTimeout;
	private Sprite treesSprite2;

	@Override
	public void create() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glDepthFunc(GL11.GL_EQUAL);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		spriteBatch = new SpriteBatch();

		atlas1 = new TextureAtlas(Gdx.files.internal("data/images/images.atlas"));
		atlas2 = new TextureAtlas(Gdx.files.internal("data/images/images.atlas"));

		treesSprite = atlas1.createSprite("trees");
		treesSprite.setSize(width, height);
		treesSprite.setColor(1,1,1,0.2f);

		treesSprite2 = atlas2.createSprite("trees");
		treesSprite2.setSize(width, height);
		treesSprite2.setColor(1,1,1,0.2f);

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
		depthFunc = false;
		multipleBatches = false;
		twoTextures = false;
		renderTimes = 1;

		skinAtlas = new TextureAtlas(Gdx.files.internal("data/ui/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"), skinAtlas);

		stage = new Stage(width, height, false);
		Gdx.input.setInputProcessor(new InputMultiplexer(screenInputProcessor, stage));

		Table optionsContainer = new Window("Options", skin);
		optionsContainer.setTransform(false);

		optionsContainer.setSize(width * 0.8f, height * 0.8f);
		optionsContainer.setPosition(width * 0.1f, height * 0.1f);

		{
			// add intermediate container...

			// add some custom stuff

			{
				final CheckBox actor = new CheckBox("Blending: " + textForBoolean(blending), skin);
				actor.setName("Blending");
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						blending = !blending;
						actor.setText("Blending: " + textForBoolean(blending));
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f).colspan(5);
			}

			optionsContainer.row();

			{
				final CheckBox actor = new CheckBox("DepthFunc: " + textForBoolean(depthFunc), skin);
				actor.setName("DepthFunc");
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						depthFunc = !depthFunc;
						actor.setText("DepthFunc: " + textForBoolean(depthFunc));
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f).colspan(5);
			}

			optionsContainer.row();

			{
				TextButton actor = new TextButton("RenderTimes: " + renderTimes, skin);
				actor.setName("RenderTimes");
				actor.setTouchable(Touchable.disabled);
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f).colspan(5);
			}

			optionsContainer.row();
			{

				int[] renderTimesDeltas = { -10, -1, 1, 10 };
				for (int i = 0; i < renderTimesDeltas.length; i++) {
					final int renderTimeDelta = renderTimesDeltas[i];
					TextButton actor = new TextButton("" + renderTimeDelta, skin);
					actor.addListener(new ClickListener() {
						@Override
						public void clicked(InputEvent event, float x, float y) {
							super.clicked(event, x, y);
							if (renderTimes + renderTimeDelta > 0) {
								renderTimes += renderTimeDelta;
								updateRenderTimes();
							}
						}
					});
					optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f);
				}
			}
			

			optionsContainer.row();

			{
				final CheckBox actor = new CheckBox("Multiple batches: " + textForBoolean(multipleBatches), skin);
				actor.setName("MultipleBatchs");
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						multipleBatches = !multipleBatches;
						actor.setText("Multiple batches: " + textForBoolean(multipleBatches));
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f).colspan(5);
			}

			optionsContainer.row();

			{
				final CheckBox actor = new CheckBox("Two textures: " + textForBoolean(twoTextures), skin);
				actor.setName("TwoTextures");
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						twoTextures = !twoTextures;
						actor.setText("Two textures: " + textForBoolean(twoTextures));
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f).colspan(5);
			}

		}

		optionsContainer.row();

		{
			TextButton textButton = new TextButton("HIDE", skin);

			textButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					stage.getRoot().setVisible(false);
				}
			});

			optionsContainer.add(textButton).center().bottom().colspan(5).padLeft(10f).padRight(10f).expandX().fillX().padBottom(20f);
		}

		optionsContainer.row();

		{
			TextButton textButton = new TextButton("MENU", skin);

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

	private void updateRenderTimes() {
		TextButton button = (TextButton) stage.getRoot().findActor("RenderTimes");
		button.setText("RenderTimes: " + renderTimes);
	}

	@Override
	public void dispose() {
		atlas1.dispose();
		atlas2.dispose();
		spriteBatch.dispose();
		skinAtlas.dispose();
		skin.dispose();
		stage.dispose();
		atlas1 = null;
		atlas2 = null;
		spriteBatch = null;
		skinAtlas = null;
		skin = null;
		stage = null;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (blending)
			spriteBatch.enableBlending();
		else
			spriteBatch.disableBlending();

		if (!multipleBatches) {
			spriteBatch.begin();
			Gdx.gl.glDepthMask(depthFunc);
			for (int i = 0; i < renderTimes; i++) {
				treesSprite.draw(spriteBatch);
				if (twoTextures) {
					treesSprite2.draw(spriteBatch);
				}
			}
			spriteBatch.end();
		} else {
			for (int i = 0; i < renderTimes; i++) {
				spriteBatch.begin();
				Gdx.gl.glDepthMask(depthFunc);
				treesSprite.draw(spriteBatch);
				if (twoTextures) {
					treesSprite2.draw(spriteBatch);
				}
				spriteBatch.end();
			}
		}

		if (stage.getRoot().isVisible())
			stage.draw();
	}

	@Override
	public void update() {
		stageHideTimeout -= Gdx.graphics.getDeltaTime();
		if (stageHideTimeout < 0f)
			stage.getRoot().setVisible(false);
		stage.act();
	}

	private String textForBoolean(boolean b) {
		return b ? "enabled" : "disabled";
	}

}
