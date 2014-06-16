package com.pauson.recurrence.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pauson.recurrence.Recurrence;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Recurrence";
		config.width = 1280;
		config.height = 800;
		new LwjglApplication(new Recurrence(), config);
	}
}
