package com.pauson.recurrence.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pauson.recurrence.Recurrence;
import com.pauson.recurrence.input.InputHandler;

public class GameScreen implements Screen {

	Recurrence game;
	FPSLogger fpslogger;
	OrthographicCamera camera;
	ShapeRenderer shapeRenderer;
	
	Random random = new Random();
	
	int screenX = 1280;
	int screenY = 800;
	int sizeX = 108;
	int sizeY = 80;
	float width = screenX/(sizeX + 20);
	float height = screenY/sizeY;
	
	float[][] heightMap;
	
	Plot[][] plotMap = new Plot[sizeX][sizeY];
	
	SpriteBatch batch;
	
	int score = 2;
	BitmapFont font = new BitmapFont();
	
	int nodesLimit = 5;
	ArrayList<Node> nodes = new ArrayList<Node>();
	
	ArrayList<Goods> goods = new ArrayList<Goods>();
	ArrayList<Goods> goodsToRemove = new ArrayList<Goods>();
	
	float timer = 0;
	int connectionLimit = 10;
	int connectionCount = 0;
	
	int mousePosX = 0;
	int mousePosY = 0;
	boolean mouseOnScreen = false;

	public GameScreen(Recurrence game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		fpslogger.log();
		
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		// Plots render
		shapeRenderer.begin(ShapeType.Filled);
		for (int i = 0; i<sizeX; i++) {
			for (int j = 0; j<sizeY; j++) {
				if (plotMap[i][j].type.equals(Type.WATER)) {
					shapeRenderer.setColor(plotMap[i][j].color);
				} else if (plotMap[i][j].type.equals(Type.GRASS)) {
					shapeRenderer.setColor(plotMap[i][j].color);
				} else if (plotMap[i][j].type.equals(Type.FOREST)) {
					shapeRenderer.setColor(plotMap[i][j].color);
				} else if (plotMap[i][j].type.equals(Type.ROCK)) {
					shapeRenderer.setColor(plotMap[i][j].color);
				} 
				 shapeRenderer.rect(i*width, j*height, width, height);
			}
		}
		shapeRenderer.end();
		
		// Nodes render
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.MAROON);
		for (int i = 0; i < nodes.size(); i++) {
			shapeRenderer.rect(nodes.get(i).posX*width, nodes.get(i).posY*height, width, height);
		}
		shapeRenderer.end();
		
		// Connection render
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.NAVY);
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.get(i).connectedNodes.size(); j++) {
				shapeRenderer.line(	nodes.get(i).posX*width + width/2, nodes.get(i).posY*height + height/2, 
									nodes.get(i).connectedNodes.get(j).posX*width + width/2, nodes.get(i).connectedNodes.get(j).posY*height + height/2);
			}
		}
		shapeRenderer.end();
		
		// Goods render
		shapeRenderer.begin(ShapeType.Filled);
		if (goods.size() != 0) {
			for (int i = 0; i < goods.size(); i++) {
				shapeRenderer.circle(goods.get(i).posX*width, goods.get(i).posY*height, width/2);
			}
		}
		shapeRenderer.end();
		
		// GUI render
		batch.begin();
		font.draw(batch, "Score: " + score, 50, screenY - 30);
		if (mouseOnScreen) {
			font.draw(batch, "Plot: " + plotMap[mousePosX][mousePosY].type, screenX - 18*width, screenY - 20);
			font.draw(batch, "Height: " + plotMap[mousePosX][mousePosY].height, screenX - 18*width, screenY - 40);
			
			for (int i = 0; i < nodes.size(); i++) {
				if (nodes.get(i).posX == mousePosX && nodes.get(i).posY == mousePosY) {
					font.draw(batch, "Population: " + nodes.get(i).population, screenX - 18*width, screenY - 200);
					font.draw(batch, "Wood: " + nodes.get(i).woodAmount, screenX - 18*width, screenY - 220);
					font.draw(batch, "Rock: " + nodes.get(i).rockAmount, screenX - 18*width, screenY - 240);
					font.draw(batch, "Food: " + nodes.get(i).foodAmount, screenX - 18*width, screenY - 260);
					font.draw(batch, "Water: " + nodes.get(i).waterAmount, screenX - 18*width, screenY - 280);
				}
			}
			
		}
		batch.end();
		
		
		update(delta);
	}
	
	public void update(float delta) {
		// Goods generation
		for (int i = 0; i < nodes.size(); i++) {
			nodes.get(i).timer += delta;
			if (nodes.get(i).timer > 1) {
				nodes.get(i).timer--;
				if (nodes.get(i).connectedNodes.size() != 0) {
					for (int j = 0; j < nodes.get(i).connectedNodes.size(); j++) {
						float distance = (float) Math.sqrt((nodes.get(i).connectedNodes.get(j).posX - nodes.get(i).posX)*(nodes.get(i).connectedNodes.get(j).posX - nodes.get(i).posX) + (nodes.get(i).connectedNodes.get(j).posY - nodes.get(i).posY)*(nodes.get(i).connectedNodes.get(j).posY - nodes.get(i).posY));
						float velX = (nodes.get(i).connectedNodes.get(j).posX - nodes.get(i).posX)*Goods.velBase*score/distance;
						float velY = (nodes.get(i).connectedNodes.get(j).posY - nodes.get(i).posY)*Goods.velBase*score/distance;
						float lifetime = distance/(Goods.velBase*score);
						goods.add(new Goods(nodes.get(i).posX + 0.5f, nodes.get(i).posY + 0.5f, velX, velY, lifetime, nodes.get(i), nodes.get(i).connectedNodes.get(j)));
					}
				}
			}
		}
		
		// Goods position update
		if (goods.size() != 0) {
			for (int i = 0; i < goods.size(); i++) {
				goods.get(i).posX += goods.get(i).velX*delta;
				goods.get(i).posY += goods.get(i).velY*delta;
				goods.get(i).lifetime -= delta;
				if (goods.get(i).lifetime < 0) {
					goodsToRemove.add(goods.get(i));
				}
			}
		}
		
		goods.removeAll(goodsToRemove);
		
		mousePosX = Gdx.input.getX() * (sizeX + 20) / screenX;
		if (mousePosX >= 108) { 
			mouseOnScreen = false;
		} else {
			mouseOnScreen = true;
		}
		mousePosY = sizeY - Gdx.input.getY() * (sizeY) / screenY - 1;
		
	}	

	@Override
	public void resize(int width, int height) {
	
	}

	@Override
	public void show() {
		camera = new OrthographicCamera(screenX, screenY);
		camera.translate(camera.viewportWidth/2, camera.viewportHeight/2);
		
		shapeRenderer = new ShapeRenderer();
		
		fpslogger = new FPSLogger();
		
		Gdx.input.setInputProcessor(new InputHandler(game));
		
		batch = new SpriteBatch();
				
		// Map generation
		heightMap = GeneratePerlinNoise(GenerateWhiteNoise(sizeX, sizeY), 6);
		
		for (int i = 0 ; i < heightMap.length; i++) {
			for (int j = 0; j < heightMap[0].length; j++) {
				plotMap[i][j] = new Plot(i, j, (int) Math.round((heightMap[i][j] - 0.3f)*100));
				if (plotMap[i][j].height <= 0) {
					plotMap[i][j].type = Type.WATER;
					plotMap[i][j].color = Color.BLUE.cpy().mul(1 - 5*(Math.abs(heightMap[i][j] - 0.3f)));
				} else if (plotMap[i][j].height >= 55) {
					plotMap[i][j].type = Type.ROCK;
					plotMap[i][j].color = Color.GRAY.cpy().mul(1 - (Math.abs(heightMap[i][j] - 0.3f)));
				} else if (plotMap[i][j].height < 55 && plotMap[i][j].height > 30) {
					plotMap[i][j].type = Type.FOREST;
					plotMap[i][j].color = Color.OLIVE.cpy().mul(1 - (Math.abs(heightMap[i][j] - 0.3f)));
				} else if (plotMap[i][j].height > 0 && plotMap[i][j].height <= 30) {
					plotMap[i][j].type = Type.GRASS;
					plotMap[i][j].color = Color.GREEN.cpy().mul(1 - 2*(Math.abs(heightMap[i][j] - 0.3f)));
				}
			}
		}
		
		// Nodes generation
		for (int i = 0; i < 5; i++) {
			nodes.add(new Node(random.nextInt(100) + 14, random.nextInt(70) + 5));
		}
		
		for (int i = 0; i < 10; i++) {
			int n1;
			int n2;
			do {
				 n1 = random.nextInt(nodes.size());
				 n2 = random.nextInt(nodes.size());
			} while((n1 == n2) || (nodes.get(n1).connectedNodes.contains(nodes.get(n2))) || (nodes.get(n2).connectedNodes.contains(nodes.get(n1))) ||
					(nodes.get(n1).connectedNodes.size() > nodes.get(n1).connectionLimit) || (nodes.get(n2).connectedNodes.size() > nodes.get(n2).connectionLimit));
			nodes.get(n1).addConnection(nodes.get(n2));
			nodes.get(n2).addConnection(nodes.get(n1));
		}
	}

	@Override
	public void hide() {
	
	}

	@Override
	public void pause() {
	
	}

	@Override
	public void resume() {
	
	}

	@Override
	public void dispose() {
	
	}

	public void print(String  string) {
		System.out.println(string);
	}
	
	public float[][] GenerateWhiteNoise(int width, int height){
	    float[][] noise = new float[width][height];
	 
	    for (int i = 0; i < width; i++) {
	        for (int j = 0; j < height; j++) {
	            noise[i][j] = (float)random.nextDouble() % 1;
	        }
	    }
	 
	    return noise;
	}
	
	public float[][] GenerateSmoothNoise(float[][] baseNoise, int octave)
	{
	   int width = baseNoise.length;
	   int height = baseNoise[0].length;
	 
	   float[][] smoothNoise = new float[width][height];
	 
	   int samplePeriod = 1 << octave; // calculates 2 ^ k
	   float sampleFrequency = 1.0f / samplePeriod;
	 
	   for (int i = 0; i < width; i++) {
	      //calculate the horizontal sampling indices
	      int sample_i0 = (i / samplePeriod) * samplePeriod;
	      int sample_i1 = (sample_i0 + samplePeriod) % width; //wrap around
	      float horizontal_blend = (i - sample_i0) * sampleFrequency;
	 
	      for (int j = 0; j < height; j++) {
	         //calculate the vertical sampling indices
	         int sample_j0 = (j / samplePeriod) * samplePeriod;
	         int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
	         float vertical_blend = (j - sample_j0) * sampleFrequency;
	 
	         //blend the top two corners
	         float top = Interpolate(baseNoise[sample_i0][sample_j0],
	            baseNoise[sample_i1][sample_j0], horizontal_blend);
	 
	         //blend the bottom two corners
	         float bottom = Interpolate(baseNoise[sample_i0][sample_j1],
	            baseNoise[sample_i1][sample_j1], horizontal_blend);
	 
	         //final blend
	         smoothNoise[i][j] = Interpolate(top, bottom, vertical_blend);
	      }
	   }
	 
	   return smoothNoise;
	}
	
	public float Interpolate(float x0, float x1, float alpha) {
	   return x0 * (1 - alpha) + alpha * x1;
	}
	
	public float[][] GeneratePerlinNoise(float[][] baseNoise, int octaveCount) {
	   int width = baseNoise.length;
	   int height = baseNoise[0].length;
	 
	   float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing
	 
	   float persistance = 0.5f;
	 
	   //generate smooth noise
	   for (int i = 0; i < octaveCount; i++) {
	       smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
	   }
	 
	    float[][] perlinNoise = new float[width][height];
	    float amplitude = 1.0f;
	    float totalAmplitude = 0.0f;
	 
	    //blend noise together
	    for (int octave = octaveCount - 1; octave >= 0; octave--) {
	       amplitude *= persistance;
	       totalAmplitude += amplitude;
	 
	       for (int i = 0; i < width; i++) {
	          for (int j = 0; j < height; j++) {
	             perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
	          }
	       }
	    }
	 
	   //Normalisation
	   for (int i = 0; i < width; i++) {
	      for (int j = 0; j < height; j++) {
	         perlinNoise[i][j] /= totalAmplitude;
	      }
	   }
	 
	   return perlinNoise;
	}

}
