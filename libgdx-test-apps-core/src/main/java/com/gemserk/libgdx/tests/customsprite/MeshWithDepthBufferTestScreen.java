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
		float[] localVertices = wormInsidePolygon.getLocalVertices();
		float[] textureCoords = wormInsidePolygon.getTextureCoords();

		float[] vertices = new float[(localVertices.length / 2) * 3];

		for (int i = 0; i < localVertices.length; i += 2) {
			vertices[(i / 2) * 3] = localVertices[i];
			vertices[(i / 2) * 3 + 1] = localVertices[i + 1];
			vertices[(i / 2) * 3 + 2] = 0f;
		}

		return new MeshSprite(vertices, textureCoords, texture);
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
		// Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// if (blending)
		// meshSpriteBatch.enableBlending();
		// else
		// meshSpriteBatch.disableBlending();

		// meshWormInsideSprite.setPosition(-200f, 0f);
		// meshWormInsideSprite.setOrigin(-meshWormInsideSprite.getWidth() * 0.5f, -meshWormInsideSprite.getHeight() * 0.5f);
		// meshWormInsideSprite.setRotation(meshWormInsideSprite.getRotation() + 0.1f);
		// meshSprite.setScale(1f, 1f);
		// meshSprite.setSize(100f, 100f);
		
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

		// shapeRenderer.setProjectionMatrix(camera.combined);
		// shapeRenderer.begin(ShapeType.Line);
		// shapeRenderer.setColor(1f, 0f, 0f, 1f);
		// shapeRenderer.line(-200f, -1000f, -200f, 1000f);
		// shapeRenderer.end();

		stage.draw();
	}

	// private void checkError(String errorString) {
	// int error = Gdx.gl.glGetError();
	// if (error != GL10.GL_NO_ERROR)
	// throw new RuntimeException(errorString + " , error:" + error);
	// }

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
