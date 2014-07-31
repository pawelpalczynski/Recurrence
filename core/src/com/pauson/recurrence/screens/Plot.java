package com.pauson.recurrence.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Plot {
	
	int posX = 0;
	int posY = 0;
	int height = 0;
	Type type = Type.GRASS;
	Color color = Color.GREEN;
	
	public Plot(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public Plot(int posX, int posY, int height) {
		this.posX = posX;
		this.posY = posY;
		this.height = height;
	}
	
	public Plot(int posX, int posY, int height, Type type, Color color) {
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.type = type;
		this.color = color;
	}
	
	public void render(ShapeRenderer shapeRenderer) {
		if (type.equals(Type.WATER)) {
			shapeRenderer.setColor(color);
		} else if (type.equals(Type.GRASS)) {
			shapeRenderer.setColor(color);
		} else if (type.equals(Type.FOREST)) {
			shapeRenderer.setColor(color);
		} else if (type.equals(Type.ROCK)) {
			shapeRenderer.setColor(color);
		} 
		shapeRenderer.rect(posX*GameScreen.width, posY*GameScreen.height, GameScreen.width, GameScreen.height);
	}

}
