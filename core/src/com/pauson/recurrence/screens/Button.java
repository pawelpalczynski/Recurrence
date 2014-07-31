package com.pauson.recurrence.screens;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Button {
	
	float x, y, width, height;
	String string;
	boolean active;
	
	public Button(float x, float y, float width, float height, String string){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.string = string;
		this.active = false;
	}
	
	public void render(float delta, ShapeRenderer shapeRenderer, SpriteBatch batch, BitmapFont font) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.rect(x, y, width, height);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.end();
		
		batch.begin();
		if (active) {
			font.setColor(Color.GREEN);
		} else {
			font.setColor(Color.WHITE);
		}
		
		font.draw(batch, string, x + width/2 - font.getBounds(string).width/2, y + height/2 + font.getCapHeight()/2);
		batch.end();
		font.setColor(Color.BLACK);
	}
	
	public boolean isClicked(float mouseX, float mouseY, ArrayList<Button> buttons) {
		if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
			for (int i = 0; i<buttons.size(); i++){
				buttons.get(i).active = false;
			}
			active = true;
			return true;
		}
		return false;
	}
	
}