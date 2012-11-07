package com.gemserk.libgdx.tests.customsprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gemserk.libgdx.tests.ShowStageInputProcessor;
import com.gemserk.libgdx.tests.TestBaseWindow;
import com.gemserk.libgdx.tests.TestScreen;

public class MeshWithDepthBufferTestScreen extends TestScreen {

	TextureAtlas skinAtlas;
	TextureAtlas atlas;
	Skin skin;
	Stage stage;

	boolean blending;

	MeshSprite meshWormInsideSprite;
	MeshSprite meshWormOutsideSprite;

	OrthographicCamera camera;
	MeshSpriteBatch meshSpriteBatch;

	ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		Gdx.gl.glClearColor(0.69f, 0.86f, 0.81f, 1f);

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		skinAtlas = new TextureAtlas(Gdx.files.internal("data/ui/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"), skinAtlas);

		atlas = new TextureAtlas(Gdx.files.internal("data/images/polygon.atlas"));

		Sprite wormSprite = atlas.createSprite("worm");

		PolygonRegion wormInsidePolygon = new PolygonRegion(wormSprite, Gdx.files.internal("data/polygons/worm-inside"));
		PolygonRegion wormOutsidePolygon = new PolygonRegion(wormSprite, Gdx.files.internal("data/polygons/worm-border"));

		meshWormInsideSprite = createMeshSprite(wormInsidePolygon, wormSprite.getTexture());
		meshWormOutsideSprite = createMeshSprite(wormOutsidePolygon, wormSprite.getTexture());

		meshSpriteBatch = new MeshSpriteBatch();

		stage = new Stage(width, height, false);

		blending = false;

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

		shapeRenderer = new ShapeRenderer();
	}

	private MeshSprite createMeshSprite(PolygonRegion wormInsidePolygon, Texture texture) {
		return new MeshSprite(wormInsidePolygon.getLocalVertices(), wormInsidePolygon.getTextureCoords(), texture);
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
		meshSpriteBatch.dispose();
		meshSpriteBatch = null;
		shapeRenderer.dispose();
		shapeRenderer = null;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		meshWormInsideSprite.setZ(0f);
		meshWormOutsideSprite.setZ(0f);

		meshSpriteBatch.setDepthTestEnabled(true);

		meshSpriteBatch.setProjectionMatrix(camera.combined);
		meshSpriteBatch.begin();
		meshSpriteBatch.disableBlending();
		meshSpriteBatch.draw(meshWormInsideSprite.getTexture(), meshWormInsideSprite.getVertices());
		meshSpriteBatch.end();

		meshSpriteBatch.begin();
		meshSpriteBatch.enableBlending();
		meshSpriteBatch.draw(meshWormOutsideSprite.getTexture(), meshWormOutsideSprite.getVertices());
		meshSpriteBatch.end();

		meshSpriteBatch.setDepthTestEnabled(false);

		stage.draw();
	}

	@Override
	public void update() {
		stage.act();
	}

	public static Mesh translate(Mesh mesh, float x, float y) {
		VertexAttribute posAttr = mesh.getVertexAttribute(Usage.Position);
		int offset = posAttr.offset / 4;
		int numVertices = mesh.getNumVertices();
		int vertexSize = mesh.getVertexSize() / 4;

		float[] vertices = new float[numVertices * vertexSize];
		mesh.getVertices(vertices);

		int idx = offset;
		for (int i = 0; i < numVertices; i++) {
			vertices[idx] += x;
			vertices[idx + 1] += y;
			idx += vertexSize;
		}

		mesh.setVertices(vertices);

		return mesh;
	}

}
