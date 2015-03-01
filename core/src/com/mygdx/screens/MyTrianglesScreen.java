package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.lines.MyShapeRenderer;


/**
 * Attempting to draw pretty lines using the shape renderer
 * 
 * 
 * @author george
 *
 */

public class MyTrianglesScreen implements Screen {
	private MyShapeRenderer rend;
	private ShapeRenderer srend;
	private OrthographicCamera cam;
	private int worldWidth = 120, worldHeight = 100;
	
	
	public MyTrianglesScreen() {
		cam = new OrthographicCamera(worldWidth, worldHeight);		
		rend = new MyShapeRenderer();
		srend = new ShapeRenderer();
		srend.setProjectionMatrix(cam.combined);
		rend.setCamera(cam);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Vector2 a = new Vector2(50,100),  b= new Vector2(100,10),  c = new Vector2(10,10);
		//rend.drawLine(new Vector2(50,50), new Vector2(worldWidth,worldHeight), 5, Color.WHITE);
//		rend.drawTriangleFeathered(a,b,c, Color.WHITE, 0.005f);

//		rend.drawTriangleFeathered(a,b,c, Color.RED, Color.GREEN, Color.BLUE,  0.9f);
		rend.drawArc(new Vector2(0,0), 0, 45, 5,5,Color.RED, 60);
		rend.drawArc(new Vector2(0,0), 90, 45, 5,5,Color.GREEN, 60);
		
		rend.drawArc(new Vector2(0,0), 90, 135, 5,5,Color.BLUE, 60);
		rend.drawArc(new Vector2(0,0), 90, 135, 10,5,Color.CYAN, 60);
		rend.drawArc(new Vector2(0,0), 90, 45, 5,5,Color.GREEN, 60);
		rend.drawArc(new Vector2(0,0), 0, 45, 5,5,Color.RED, 60);
		rend.drawArc(new Vector2(0,0), 90, 45, 5,5,Color.GREEN, 60);

	//	rend.drawCircle(0,0,10,Color.WHITE,10);
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		rend.dispose();
	}
	

}
