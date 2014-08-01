package com.pauson.recurrence.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.pauson.recurrence.Recurrence;

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
		if (!gameScreen.mouseOnScreen && button == 0) {
			for (int i = 0; i<gameScreen.buttons.size(); i++) {
				if(gameScreen.buttons.get(i).isClicked(Gdx.input.getX(), GameScreen.screenY - Gdx.input.getY(), gameScreen.buttons)) { 
					gameScreen.activeButton = i + 1;
					gameScreen.nodeIsSelected = false;
					gameScreen.nodeSelected.selected = false;
					gameScreen.nodeSelected = new Node(0, 0);
				}
			}
		} else if (button == 1 && !gameScreen.mouseOnScreen){
			for (int i = 0; i<gameScreen.buttons.size(); i++) {
				gameScreen.buttons.get(i).active = false;
			}
			gameScreen.activeButton = 0;
		}
		
		// UI - abilities
		if (gameScreen.mouseOnScreen && button == 0) {
			switch (gameScreen.activeButton){
			case 1: boolean isEmpty = true;
					for (int i = 0; i < gameScreen.nodes.size(); i++){
						if (gameScreen.nodes.get(i).posX == gameScreen.mousePosX && gameScreen.nodes.get(i).posY == gameScreen.mousePosY){
							isEmpty = false;
						}
					}
					if (isEmpty && gameScreen.nodesCount < gameScreen.nodesLimit){
						gameScreen.nodes.add(new Node(gameScreen.mousePosX, gameScreen.mousePosY));
					}
					break;
			case 2: for (int i = 0; i<gameScreen.nodes.size(); i++){
						if(gameScreen.nodes.get(i).posX == gameScreen.mousePosX && gameScreen.nodes.get(i).posY == gameScreen.mousePosY){
							gameScreen.nodesToRemove.add(gameScreen.nodes.get(i));
						}
					}
					gameScreen.nodes.removeAll(gameScreen.nodesToRemove);
					gameScreen.nodesToRemove.clear();
					break;
			case 3: for (int i = 0; i < gameScreen.nodes.size(); i++)	{
						if(gameScreen.nodes.get(i).posX == gameScreen.mousePosX && gameScreen.nodes.get(i).posY == gameScreen.mousePosY){
							if(!gameScreen.nodeIsSelected){
								gameScreen.nodeIsSelected = true;
								gameScreen.nodeSelected = gameScreen.nodes.get(i);
								gameScreen.nodeSelected.selected = true;
							} else {
								if ((gameScreen.nodeSelected.posX != gameScreen.mousePosX || gameScreen.nodeSelected.posY != gameScreen.mousePosY) && !gameScreen.nodeSelected.connectedNodes.contains(gameScreen.nodes.get(i)) && !gameScreen.nodes.get(i).connectedNodes.contains(gameScreen.nodeSelected)){
									gameScreen.nodeIsSelected = false;
									gameScreen.nodeSelected.selected = false;
									gameScreen.nodeSelected.addConnection(gameScreen.nodes.get(i));
									gameScreen.nodes.get(i).addConnection(gameScreen.nodeSelected);
								}
							}
						}
					}
					break;
			case 4: for (int i = 0; i<gameScreen.nodes.size(); i++){
						if(gameScreen.nodes.get(i).posX == gameScreen.mousePosX && gameScreen.nodes.get(i).posY == gameScreen.mousePosY){
							if (!gameScreen.nodeIsSelected){
								gameScreen.nodeSelected = gameScreen.nodes.get(i);
								gameScreen.nodeIsSelected = true;
								gameScreen.nodeSelected.selected = true;
							} else if (gameScreen.nodeIsSelected && gameScreen.nodeSelected != gameScreen.nodes.get(i)) {
								gameScreen.nodeSelected.connectedNodes.remove(gameScreen.nodes.get(i));
								gameScreen.nodeIsSelected = false;
								gameScreen.nodeSelected.selected = false;
							}
						}
					}
					break;
			default: gameScreen.nodeIsSelected = false;
			}
		} else if (gameScreen.mouseOnScreen && button == 1) {
			if (gameScreen.activeButton == 3 || gameScreen.activeButton == 4) {
				gameScreen.nodeIsSelected = false;
				gameScreen.nodeSelected.selected = false;
			}
		}
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
		// Converting mouse position into grid coordinates
		gameScreen.mousePosX = Gdx.input.getX() * (GameScreen.sizeX + 20) / GameScreen.screenX;
		if (gameScreen.mousePosX >= 108) { 
			gameScreen.mouseOnScreen = false;
		} else {
			gameScreen.mouseOnScreen = true;
		}
		gameScreen.mousePosY = GameScreen.sizeY - Gdx.input.getY() * (GameScreen.sizeY) / GameScreen.screenY - 1;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
