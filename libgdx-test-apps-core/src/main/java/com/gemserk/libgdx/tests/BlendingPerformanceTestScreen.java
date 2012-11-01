package com.gemserk.libgdx.tests;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BlendingPerformanceTestScreen extends TestScreen {

	TextureAtlas atlas1, atlas2;
	SpriteBatch spriteBatch;
	TextureAtlas skinAtlas;
	Skin skin;
	Stage stage;

	Sprite treesSprite;
	Sprite treesSprite2;

	final float timeToHide = 5f;

	boolean blending;
	boolean depthFunc;
	boolean multipleBatches;
	boolean twoTextures;

	boolean smallTexture1;
	boolean smallTexture2;

	int renderTimes;

	@Override
	public void create() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glDepthFunc(GL11.GL_EQUAL);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		spriteBatch = new SpriteBatch();

		atlas1 = reloadTextureAtlas(false, atlas1);
		treesSprite = atlas1.createSprite("trees");
		treesSprite.setSize(width, height);
		treesSprite.setColor(1, 1, 1, 0.2f);

		atlas2 = reloadTextureAtlas(false, atlas2);
		treesSprite2 = atlas2.createSprite("trees");
		treesSprite2.setSize(width, height);
		treesSprite2.setColor(1, 1, 1, 0.2f);

		blending = true;
		depthFunc = false;
		multipleBatches = false;
		twoTextures = false;
		smallTexture1 = false;
		smallTexture2 = false;
		renderTimes = 1;

		skinAtlas = new TextureAtlas(Gdx.files.internal("data/ui/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"), skinAtlas);

		stage = new Stage(width, height, false);
	
		Table optionsContainer = new Table();
		optionsContainer.setTransform(false);

		{
			{
				final CheckBox actor = new CheckBox("Texture1: " + textForBoolean(smallTexture1), skin);
				actor.setName("Texture1");
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						smallTexture1 = !smallTexture1;
						atlas1 = reloadTextureAtlas(smallTexture1, atlas1);
						treesSprite = atlas1.createSprite("trees");
						treesSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
						treesSprite.setColor(1, 1, 1, 0.2f);
						actor.setText("Texture1: " + textForBoolean(smallTexture1));
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f);
			}

			{
				final CheckBox actor = new CheckBox("Texture2: " + textForBoolean(smallTexture2), skin);
				actor.setName("Texture2");
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						smallTexture2 = !smallTexture2;
						atlas2 = reloadTextureAtlas(smallTexture2, atlas2);
						treesSprite2 = atlas2.createSprite("trees");
						treesSprite2.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
						treesSprite2.setColor(1, 1, 1, 0.2f);
						actor.setText("Texture2: " + textForBoolean(smallTexture2));
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f);
			}

			optionsContainer.row();

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

		Table baseWindowContainer = new TestBaseWindow("Options", skin, parent);
		
		baseWindowContainer.setTransform(false);
		baseWindowContainer.setSize(width * 0.8f, height * 0.8f);
		baseWindowContainer.setPosition(width * 0.1f, height * 0.1f);
		
		stage.addActor(baseWindowContainer);
		
		Table innerContainer = (Table) baseWindowContainer.findActor(TestBaseWindow.INNER_CONTAINER_NAME);
		innerContainer.add(optionsContainer).fill().expand().padTop(10f);
		
		stage.getRoot().setVisible(false);
		
		Gdx.input.setInputProcessor(new InputMultiplexer(new ShowStageInputProcessor(stage, parent), stage));
		
		Gdx.input.setCatchBackKey(true);
	}

	private TextureAtlas reloadTextureAtlas(boolean smallTexture, TextureAtlas atlas) {
		if (atlas != null)
			atlas.dispose();

		if (!smallTexture)
			return new TextureAtlas(Gdx.files.internal("data/images/images.atlas"));
		else
			return new TextureAtlas(Gdx.files.internal("data/images/small.atlas"));
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

		int rt = !twoTextures ? renderTimes : Math.round(((float) renderTimes) / 2f);

		if (!multipleBatches) {
			spriteBatch.begin();
			Gdx.gl.glDepthMask(depthFunc);
			for (int i = 0; i < rt; i++) {
				treesSprite.draw(spriteBatch);
				if (twoTextures) {
					treesSprite2.draw(spriteBatch);
				}
			}
			spriteBatch.end();
		} else {
			for (int i = 0; i < rt; i++) {
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
		stage.act();
	}

	private String textForBoolean(boolean b) {
		return b ? "enabled" : "disabled";
	}

}
