package com.gemserk.libgdx.tests;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TestBaseWindow extends Window {

	public static final String INNER_CONTAINER_NAME = "innerContainer";

	private final TestsApplicationListener app;

	public TestBaseWindow(String title, Skin skin, TestsApplicationListener app) {
		super(title, skin);
		this.app = app;

		Table innerContainer = new Table(skin);
		innerContainer.setName(INNER_CONTAINER_NAME);
		innerContainer.setTransform(false);
		
		add(innerContainer).colspan(5).expand().fill();

		row();
		{
			TextButton textButton = new TextButton("HIDE", skin);

			textButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					getStage().getRoot().setVisible(false);
				}
			});
			add(textButton).center().bottom().padLeft(10f).padRight(10f).expandX().fillX().padBottom(20f);
		}
		row();
		{
			TextButton textButton = new TextButton("MENU", skin);

			textButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					TestBaseWindow.this.app.backToSelection();
				}
			});
			add(textButton).center().bottom().padLeft(10f).padRight(10f).expandX().fillX().padBottom(20f);
		}

	}

}
