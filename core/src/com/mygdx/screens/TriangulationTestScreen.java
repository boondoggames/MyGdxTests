package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;



/**
 * Attempting to draw pretty lines using the shape renderer
 * 
 * 
 * @author george
 *
 */

public class TriangulationTestScreen implements Screen, InputProcessor {
	private ShapeRenderer rend;
	private OrthographicCamera cam;
	private int worldWidth = 120, worldHeight = 100;
	
	private FloatArray points = new FloatArray();
	private ShortArray triangles;
	private EarClippingTriangulator triangulator = new EarClippingTriangulator();
	
	
	public TriangulationTestScreen() {
		cam = new OrthographicCamera(worldWidth, worldHeight);		
		rend = new ShapeRenderer();
		rend.setProjectionMatrix(cam.combined);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		rend.begin(ShapeType.Line);
		rend.setColor(Color.WHITE);
		for (int i = 0; i < points.size; i+=2) {
			rend.circle(points.get(i), points.get(i+1), 1,50);
		}
		
		
		
		if (points.size > 2) {
			for (int i = 0; i < points.size - 2; i+=2) {
				rend.line(points.get(i), points.get(i+1), points.get(i+2), points.get(i+3));
			}
			rend.line(points.get(points.size-2), points.get(points.size-1), points.get(0), points.get(1));
		}
		
		rend.setColor(Color.RED);

		if (triangles!=null) {

			for (int i = 0; i < triangles.size; i += 3) {
				int p1 = triangles.get(i) * 2;
				int p2 = triangles.get(i + 1) * 2;
				int p3 = triangles.get(i + 2) * 2;
				rend.triangle( //
					points.get(p1), points.get(p1 + 1), //
					points.get(p2), points.get(p2 + 1), //
					points.get(p3), points.get(p3 + 1));
			}			
		}
		
		rend.end();
		
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

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.R) {
			points.clear();
			triangles = null;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector3 touchPos = new Vector3(screenX,screenY,0);
		cam.unproject(touchPos);
		points.add(touchPos.x);
		points.add(touchPos.y);
		if (points.size>6) {
			triangles= triangulator.computeTriangles(points);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
