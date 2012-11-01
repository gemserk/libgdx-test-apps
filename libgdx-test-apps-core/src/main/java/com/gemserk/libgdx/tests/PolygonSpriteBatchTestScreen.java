package com.gemserk.libgdx.tests;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PolygonSpriteBatchTestScreen extends TestScreen {

	TextureAtlas skinAtlas;
	TextureAtlas atlas;
	Skin skin;
	Stage stage;
	PolygonSpriteBatch polygonSpriteBatch;

	ArrayList<PolygonSprite> polygonSprites;

	@Override
	public void create() {
		Gdx.gl.glClearColor(0.69f, 0.86f, 0.81f, 1f);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		skinAtlas = new TextureAtlas(Gdx.files.internal("data/ui/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"), skinAtlas);

		atlas = new TextureAtlas(Gdx.files.internal("data/images/polygon.atlas"));

		PolygonRegion polygonRegion = new PolygonRegion(atlas.createSprite("worm"), Gdx.files.internal("data/polygons/worm"));

		polygonSprites = new ArrayList<PolygonSprite>();
		
		for (int i = 0; i < 100; i++) {
			PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
			polygonSprite.setPosition(MathUtils.random(0, Gdx.graphics.getWidth()) - polygonSprite.getWidth() * 0.5f, 
					MathUtils.random(0f, Gdx.graphics.getHeight()) - polygonSprite.getHeight() * 0.5f);
			polygonSprites.add(polygonSprite);
		}


		polygonSpriteBatch = new PolygonSpriteBatch();

		stage = new Stage(width, height, false);

		Table optionsContainer = new Table();
		optionsContainer.setTransform(false);

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
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		polygonSpriteBatch.begin();
		for (int i = 0; i < polygonSprites.size(); i++)
			polygonSprites.get(i).draw(polygonSpriteBatch);
		polygonSpriteBatch.end();

		stage.draw();
	}

	@Override
	public void update() {
		stage.act();
	}

}
