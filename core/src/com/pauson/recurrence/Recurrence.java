package com.pauson.recurrence;

import com.badlogic.gdx.Game;
import com.pauson.recurrence.screens.GameScreen;

public class Recurrence extends Game {
	
	public static final String VERSION = "0.0.01 Pre-Alpha";
	public static final String LOG = "Incremental";
	
	@Override
	public void create() {	
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
