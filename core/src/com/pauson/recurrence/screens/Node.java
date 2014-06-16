package com.pauson.recurrence.screens;

import java.util.ArrayList;

public class Node {
	
	float posX;
	float posY;
	ArrayList<Node> connectedNodes;
	int connectionLimit = 3;
	float timer;
	
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
	
	public void addConnection(Node node) {
		connectedNodes.add(node);
	}
}
