package com.pauson.recurrence.screens;

import com.badlogic.gdx.graphics.Color;

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

}
