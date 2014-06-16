package com.pauson.recurrence.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.pauson.recurrence.Recurrence;
import com.pauson.recurrence.screens.GameScreen;

public class InputHandler implements InputProcessor {
	
	Recurrence game;
	GameScreen gameScreen;
	
	public InputHandler(Recurrence game) {
		this.game = game;
		this.gameScreen = (GameScreen) game.getScreen();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.ESCAPE: Gdx.app.exit();
			case Keys.ENDCALL: Gdx.app.exit();
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
