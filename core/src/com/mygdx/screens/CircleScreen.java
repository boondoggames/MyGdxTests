package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.lines.MyShapeRenderer;


/**
 * Attempting to draw pretty lines using the shape renderer
 * 
 * 
 * @author george
 *
 */

public class CircleScreen implements Screen {
	private MyShapeRenderer rend;
	private OrthographicCamera cam;
	private int worldWidth = 120, worldHeight = 100;
	
	
	public CircleScreen() {
		cam = new OrthographicCamera(worldWidth, worldHeight);
		cam.translate(new Vector2(worldWidth/2,worldHeight/2));
		rend = new MyShapeRenderer();
		rend.setCamera(cam);

	}
	


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//rend.drawLine(new Vector2(50,50), new Vector2(worldWidth,worldHeight), 5, Color.WHITE);
		rend.drawCircle(worldWidth/2f, worldHeight/2f, 30, Color.WHITE,0.99f, 100);
		cam.update();
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
