package com.pauson.recurrence.screens;

public class Goods {
	
	float posX = 0;
	float posY = 0;
	float velX = 0;
	float velY = 0;
	Node nodeOrigin;
	Node nodeDestination;
	
	float lifetime = 0;
	
	public static final float velBase = 2;

	public Goods(float posX, float posY, float velX, float velY, float lifetime, Node nodeOrigin, Node nodeDestination) {
		this.posX = posX;
		this.posY = posY;
		this.velX = velX;
		this.velY = velY;
		this.lifetime = lifetime;
		this.nodeOrigin = nodeOrigin;
		this.nodeDestination = nodeDestination;
	}

}
