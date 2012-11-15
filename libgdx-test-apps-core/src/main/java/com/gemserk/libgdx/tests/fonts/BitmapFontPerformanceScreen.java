package com.gemserk.libgdx.tests.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gemserk.libgdx.tests.ShowStageInputProcessor;
import com.gemserk.libgdx.tests.TestBaseWindow;
import com.gemserk.libgdx.tests.TestScreen;

public class BitmapFontPerformanceScreen extends TestScreen {

	TextureAtlas skinAtlas;
	Skin skin;
	Stage optionsStage, stage;

	boolean blending;

	OrthographicCamera camera;
	
	int index;
	String text ="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras dignissim malesuada libero, quis euismod tellus iaculis et. Sed pharetra volutpat fringilla. Integer nulla quam, sagittis ac placerat id, tempus sit amet massa. Donec dictum molestie nisl, sit amet luctus ante fringilla eu. Donec quis est odio, vel rhoncus justo. Sed a magna lectus. Ut et placerat leo. Quisque ultricies volutpat leo sed feugiat. Aliquam aliquam dignissim lobortis. Etiam tempus, odio ac volutpat dictum, mauris tellus tincidunt erat, vel tempus nunc libero eu neque. Nulla facilisi. Vestibulum imperdiet ultrices mi sed aliquam. Suspendisse potenti. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras dignissim malesuada libero, quis euismod tellus iaculis et. Sed pharetra volutpat fringilla. Integer nulla quam, sagittis ac placerat id, tempus sit amet massa. Donec dictum molestie nisl, sit amet luctus ante fringilla eu. Donec quis est odio, vel rhoncus justo. Sed a magna lectus. Ut et placerat leo. Quisque ultricies volutpat leo sed feugiat. Aliquam aliquam dignissim lobortis. Etiam tempus, odio ac volutpat dictum, mauris tellus tincidunt erat, vel tempus nunc libero eu neque. Nulla facilisi. Vestibulum imperdiet ultrices mi sed aliquam. Suspendisse potenti. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
	private Label label;

	@Override
	public void create() {
		Gdx.gl.glClearColor(0.69f, 0.86f, 0.81f, 1f);

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		
		index  = 10;

		skinAtlas = new TextureAtlas(Gdx.files.internal("data/ui/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"), skinAtlas);

		optionsStage = new Stage(width, height, false);
		stage = new Stage(width, height, false);
		
		label = new Label(text.substring(0, index), skin);
		label.setWrap(true);
		label.setWidth(Gdx.graphics.getWidth() * 0.9f);
		label.setPosition(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.5f);
		stage.addActor(label);

		blending = true;

		Table optionsContainer = new Table();
		optionsContainer.setTransform(false);

		{
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
				TextButton actor = new TextButton("-", skin);
				actor.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						index -= 25;
						if (index < 0)
							index = 0;
						label.setText(text.substring(0, index));
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
						index += 25;
						if (index > text.length())
							index = text.length();
						label.setText(text.substring(0, index));
					}
				});
				optionsContainer.add(actor).padLeft(10f).padRight(10f).expandX().fillX().padBottom(10f);
			}
			
			optionsContainer.row();
		}

		Table baseWindowContainer = new TestBaseWindow("Options", skin, parent);

		baseWindowContainer.setTransform(false);
		baseWindowContainer.setSize(width * 0.8f, height * 0.8f);
		baseWindowContainer.setPosition(width * 0.1f, height * 0.1f);

		optionsStage.addActor(baseWindowContainer);

		Table innerContainer = (Table) baseWindowContainer.findActor(TestBaseWindow.INNER_CONTAINER_NAME);
		innerContainer.add(optionsContainer).fill().expand();

		optionsStage.getRoot().setVisible(false);

		Gdx.input.setInputProcessor(new InputMultiplexer(new ShowStageInputProcessor(optionsStage, parent), optionsStage));
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void dispose() {
		optionsStage.dispose();
		optionsStage = null;
		stage.dispose();
		stage = null;
		skin.dispose();
		skin = null;
		skinAtlas.dispose();
		skinAtlas = null;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		stage.draw();
		optionsStage.draw();
	}

	@Override
	public void update() {
		stage.act();
		optionsStage.act();
	}

}
