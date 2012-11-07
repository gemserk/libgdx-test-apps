package com.gemserk.libgdx.tests.customsprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
	ShaderProgram shaderProgram;
	Mesh mesh;

	boolean blending;
	private Sprite wormSprite;

	OrthographicCamera camera;

	@Override
	public void create() {
		Gdx.gl.glClearColor(0.69f, 0.86f, 0.81f, 1f);

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		skinAtlas = new TextureAtlas(Gdx.files.internal("data/ui/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"), skinAtlas);

		atlas = new TextureAtlas(Gdx.files.internal("data/images/polygon.atlas"));

		wormSprite = atlas.createSprite("worm");

		PolygonRegion polygonRegion = new PolygonRegion(wormSprite, Gdx.files.internal("data/polygons/worm"));

		shaderProgram = SpriteBatch.createDefaultShader();
		Gdx2dMeshBuilder meshBuilder = new Gdx2dMeshBuilder(false, true, 2);

		float[] localVertices = polygonRegion.getLocalVertices();
		float[] textureCoords = polygonRegion.getTextureCoords();

		for (int i = 0; i < localVertices.length; i += 2) {
			meshBuilder.color(1f, 1f, 1f, 1f);
			meshBuilder.texCoord(textureCoords[i], textureCoords[i + 1]);
			meshBuilder.vertex(localVertices[i], localVertices[i + 1], -1f);
		}

		// for (int i = 0; i < localVertices.length; i += 2) {
		// meshBuilder.color(1f, 1f, 1f, 1f);
		// meshBuilder.texCoord(textureCoords[i], textureCoords[i + 1]);
		// meshBuilder.vertex(localVertices[i] - 20f, localVertices[i + 1] - 20f, -2f);
		// }

		// meshBuilder.color(1f, 0f, 0f, 1f).vertex(50f, 50f);
		// meshBuilder.color(1f, 0f, 0f, 1f).vertex(100f, 50f);
		// meshBuilder.color(1f, 0f, 0f, 1f).vertex(100f, 100f);

		mesh = meshBuilder.build();

		// translate(mesh, 50, 50);

		stage = new Stage(width, height, false);

		blending = false;

		Table optionsContainer = new Table();
		optionsContainer.setTransform(false);

		{

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
		shaderProgram.dispose();
		shaderProgram = null;
		mesh.dispose();
		mesh = null;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		if (blending)
			Gdx.gl.glEnable(GL10.GL_BLEND);
		else
			Gdx.gl.glDisable(GL10.GL_BLEND);

		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glDepthMask(true);

		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);
		wormSprite.getTexture().bind();

		shaderProgram.begin();
		shaderProgram.setUniformMatrix("u_projectionViewMatrix", camera.combined);
		checkError("error when binding shader");
		mesh.render(shaderProgram, GL10.GL_TRIANGLES);
		checkError("error when drawing triangles");
		shaderProgram.end();
		checkError("error when ending shader");

		Gdx.gl.glDisable(GL10.GL_DEPTH_TEST);

		stage.draw();
	}

	private void checkError(String errorString) {
		int error = Gdx.gl.glGetError();
		if (error != GL10.GL_NO_ERROR)
			throw new RuntimeException(errorString + " , error:" + error);
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
