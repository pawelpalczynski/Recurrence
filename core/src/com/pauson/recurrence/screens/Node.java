package com.pauson.recurrence.screens;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Node {
	
	float posX;
	float posY;
	ArrayList<Node> connectedNodes;
	int connectionLimit = 3;
	float timer;
	boolean selected = false;
	
	int population = 0;
	int woodAmount = 0;
	int rockAmount = 0;
	int waterAmount = 0;
	int foodAmount = 0;
	
	public Node(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
		this.connectedNodes = new ArrayList<Node>();
		this.timer = 0;
	}
	
	public void render(ShapeRenderer shapeRenderer){
		// Node render
		shapeRenderer.begin(ShapeType.Filled);
		if (selected) {
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.rect((posX - 0.25f)*GameScreen.width, (posY - 0.25f)*GameScreen.height, GameScreen.width*1.5f, GameScreen.height*1.5f);
		}
		shapeRenderer.setColor(Color.MAROON);
		shapeRenderer.rect(posX*GameScreen.width, posY*GameScreen.height, GameScreen.width, GameScreen.height);
		shapeRenderer.end();
		
		// Connection render
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.NAVY);
		for (int j = 0; j < connectedNodes.size(); j++) {
			shapeRenderer.line(	posX*GameScreen.width + GameScreen.width/2, posY*GameScreen.height + GameScreen.height/2, 
								connectedNodes.get(j).posX*GameScreen.width + GameScreen.width/2, connectedNodes.get(j).posY*GameScreen.height + GameScreen.height/2);
		}
		shapeRenderer.end();
	}
	
	public ArrayList<Goods> update(float delta){
		ArrayList<Goods> goodsToAdd = new ArrayList<Goods>();
		timer += delta;
		if (timer > 1) {
			timer--;
			for (int j = 0; j < connectedNodes.size(); j++) {
				float distance = (float) Math.sqrt((connectedNodes.get(j).posX - posX)*(connectedNodes.get(j).posX - posX) + (connectedNodes.get(j).posY - posY)*(connectedNodes.get(j).posY - posY));
				float velX = (connectedNodes.get(j).posX - posX)*Goods.velBase/distance;
				float velY = (connectedNodes.get(j).posY - posY)*Goods.velBase/distance;
				float lifetime = distance/(Goods.velBase);
				goodsToAdd.add(new Goods(posX + 0.5f, posY + 0.5f, velX, velY, lifetime, this, connectedNodes.get(j)));
			}
		}
		return goodsToAdd;
	}
	
	public void addConnection(Node node) {
		connectedNodes.add(node);
	}
}
