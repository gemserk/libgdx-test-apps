package com.gemserk.libgdx.tests;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ShowStageInputProcessor extends InputAdapter {
	
	private final Stage stage;
	private final TestsApplicationListener app;

	public ShowStageInputProcessor(Stage stage, TestsApplicationListener app) {
		this.stage = stage;
		this.app = app;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.BACK || keycode == Keys.ESCAPE) 
			app.backToSelection();
		return super.keyUp(keycode);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (!stage.getRoot().isVisible()) {
			stage.getRoot().setVisible(true);
			return true;
		}
		return super.touchUp(screenX, screenY, pointer, button);
	}
}