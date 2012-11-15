package com.gemserk.libgdx.tests;

import java.util.LinkedHashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gemserk.libgdx.tests.customsprite.MeshWithDepthBufferTestScreen;
import com.gemserk.libgdx.tests.customsprite.SpriteToMeshSpritePerformanceTestScreen;
import com.gemserk.libgdx.tests.fonts.BitmapFontPerformanceScreen;

public class TestSelectionScreen extends TestScreen {

	private Skin skin;
	private TextureAtlas atlas;
	private Stage stage;

	Map<String, TestScreen> screens;

	public TestSelectionScreen() {
		screens = new LinkedHashMap<String, TestScreen>();
		screens.put("BlendingPerformanceTest", new BlendingPerformanceTestScreen());
		screens.put("Polygon batches with/without blending", new PolygonSpriteBatchTestScreen());
		screens.put("Mesh Sprite implementation", new MeshWithDepthBufferTestScreen());
		screens.put("Sprite to MeshSprite performance", new SpriteToMeshSpritePerformanceTestScreen());
		screens.put("Font render performance", new BitmapFontPerformanceScreen());
	}

	@Override
	public void create() {
		super.create();
		atlas = new TextureAtlas(Gdx.files.internal("data/ui/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"), atlas);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		stage = new Stage(width, height, false);
		Gdx.input.setInputProcessor(stage);

		Table container = new Window("Select a test", skin);
		container.setTransform(false);

		container.setSize(width * 0.95f, height * 0.95f);
		container.setPosition(width * 0.025f, height * 0.025f);

		String[] keys = new String[screens.keySet().size()];
		keys = screens.keySet().toArray(keys);

		final List list = new List(keys, skin);
		list.setTouchable(Touchable.enabled);

		ScrollPane scrollPane = new ScrollPane(list);
		scrollPane.setTouchable(Touchable.childrenOnly);

		container.add(scrollPane).pad(10f).expand().fill();
		container.row();

		TextButton startButton = new TextButton("START", skin);
		startButton.setTransform(false);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				parent.setTestScreen(screens.get(list.getSelection()));
			}
		});

		container.add(startButton).center().expandX().fillX();

		stage.addActor(container);

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);

		Gdx.input.setCatchBackKey(false);
	}

	@Override
	public void dispose() {
		super.dispose();
		skin.dispose();
		atlas.dispose();
		stage.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void update() {
		stage.act();
	}

}
