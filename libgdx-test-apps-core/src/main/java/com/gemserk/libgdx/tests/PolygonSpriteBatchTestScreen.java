package com.gemserk.libgdx.tests;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PolygonSpriteBatchTestScreen extends TestScreen {

	TextureAtlas skinAtlas;
	TextureAtlas atlas;
	Skin skin;
	Stage stage;

	PolygonSpriteBatch polygonSpriteBatch;
	SpriteBatch spriteBatch;

	ArrayList<PolygonSprite> polygonSprites;
	ArrayList<Sprite> sprites;

	boolean blending;
	boolean usePolygonSpriteBatch;

	@Override
	public void create() {
		Gdx.gl.glClearColor(0.69f, 0.86f, 0.81f, 1f);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		skinAtlas = new TextureAtlas(Gdx.files.internal("data/ui/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"), skinAtlas);

		atlas = new TextureAtlas(Gdx.files.internal("data/images/polygon.atlas"));

		final Sprite wormSprite = atlas.createSprite("worm");
		final PolygonRegion polygonRegion = new PolygonRegion(wormSprite, Gdx.files.internal("data/polygons/worm"));

		polygonSprites = new ArrayList<PolygonSprite>();
		sprites = new ArrayList<Sprite>();

		polygonSpriteBatch = new PolygonSpriteBatch();
		spriteBatch = new SpriteBatch();

		stage = new Stage(width, height, false);

		blending = true;
		usePolygonSpriteBatch = true;

		generatePolygonSprites(wormSprite, polygonRegion, 100);

		Table optionsContainer = new Table();
		optionsContainer.setTransform(false);

		{
			{
				final CheckBox actor = new CheckBox("Use polygons: " + usePolygonSpriteBatch, skin);
				actor.setName("Blending");
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						usePolygonSpriteBatch = !usePolygonSpriteBatch;
						actor.setText("Use polygons: " + usePolygonSpriteBatch);
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f).colspan(5);
			}

			optionsContainer.row();

			{
				final CheckBox actor = new CheckBox("Blending: " + blending, skin);
				actor.setName("Blending");
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						blending = !blending;
						actor.setText("Blending: " + blending);
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f).colspan(5);
			}

			optionsContainer.row();

			{
				TextButton actor = new TextButton("Sprites: " + polygonSprites.size(), skin);
				actor.setName("SpritesCount");
				actor.setTouchable(Touchable.disabled);
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f).colspan(5);
			}

			optionsContainer.row();

			{
				TextButton actor = new TextButton("-", skin);
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						removePolygonSprites(50);
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f);
			}

			{
				TextButton actor = new TextButton("+", skin);
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						generatePolygonSprites(wormSprite, polygonRegion, 50);
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f);
			}

		}

		Table baseWindowContainer = new TestBaseWindow("Options", skin, parent);

		baseWindowContainer.setTransform(false);
		baseWindowContainer.setSize(width * 0.8f, height * 0.8f);
		baseWindowContainer.setPosition(width * 0.1f, height * 0.1f);

		stage.addActor(baseWindowContainer);

		Table innerContainer = (Table) baseWindowContainer.findActor(TestBaseWindow.INNER_CONTAINER_NAME);
		innerContainer.add(optionsContainer).fill().expand();

		stage.getRoot().setVisible(false);

		Gdx.input.setInputProcessor(new InputMultiplexer(new ShowStageInputProcessor(stage, parent), stage));
		Gdx.input.setCatchBackKey(true);
	}

	private void generatePolygonSprites(Sprite sprite, PolygonRegion polygonRegion, int count) {

		for (int i = 0; i < count; i++) {
			PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
			polygonSprite.setPosition(MathUtils.random(0, Gdx.graphics.getWidth()) - polygonSprite.getWidth() * 0.5f, MathUtils.random(0f, Gdx.graphics.getHeight()) - polygonSprite.getHeight() * 0.5f);
			polygonSprites.add(polygonSprite);
		}

		for (int i = 0; i < count; i++) {
			Sprite wormSprite = new Sprite(sprite);
			wormSprite.setPosition(MathUtils.random(0, Gdx.graphics.getWidth()) - wormSprite.getWidth() * 0.5f, MathUtils.random(0f, Gdx.graphics.getHeight()) - wormSprite.getHeight() * 0.5f);
			sprites.add(wormSprite);
		}

		TextButton button = (TextButton) stage.getRoot().findActor("SpritesCount");
		if (button != null)
			button.setText("Sprites: " + polygonSprites.size());
	}

	private void removePolygonSprites(int count) {

		while (!polygonSprites.isEmpty() && count > 0) {
			polygonSprites.remove(0);
			count--;
		}

		while (!sprites.isEmpty() && count > 0) {
			sprites.remove(0);
			count--;
		}

		TextButton button = (TextButton) stage.getRoot().findActor("SpritesCount");
		if (button != null)
			button.setText("Sprites: " + polygonSprites.size());
	}

	@Override
	public void dispose() {
		stage.dispose();
		stage = null;
		skin.dispose();
		skin = null;
		skinAtlas.dispose();
		skinAtlas = null;
		atlas.dispose();
		atlas = null;
		polygonSpriteBatch.dispose();
		polygonSpriteBatch = null;
		polygonSprites.clear();
		polygonSprites = null;
		spriteBatch.dispose();
		spriteBatch = null;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (usePolygonSpriteBatch) {
			if (blending)
				polygonSpriteBatch.enableBlending();
			else
				polygonSpriteBatch.disableBlending();

			polygonSpriteBatch.begin();
			for (int i = 0; i < polygonSprites.size(); i++)
				polygonSprites.get(i).draw(polygonSpriteBatch);
			polygonSpriteBatch.end();
		} else {
			if (blending)
				spriteBatch.enableBlending();
			else
				spriteBatch.disableBlending();

			spriteBatch.begin();
			for (int i = 0; i < sprites.size(); i++)
				sprites.get(i).draw(spriteBatch);
			spriteBatch.end();
		}

		stage.draw();
	}

	@Override
	public void update() {
		stage.act();
	}

}
