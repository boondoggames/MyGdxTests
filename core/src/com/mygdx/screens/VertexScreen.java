package com.mygdx.screens;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.delaunay.Pnt;
import com.mygdx.game.delaunay.Triangle;
import com.mygdx.game.delaunay.Triangulation;
import com.mygdx.game.lines.MyShapeRenderer;


/**
 * Attempting to draw pretty lines using the shape renderer
 * 
 * 
 * @author george
 *
 */

public class VertexScreen implements Screen {
	private MyShapeRenderer rend;
	private OrthographicCamera cam;
	private Random rand = new Random();
	private Triangle initialTriangle;
	private Triangulation dt;
	private HashMap<Pnt,Color> cols = new HashMap<Pnt,Color>();
	private HashMap<Pnt,Boolean> free = new HashMap<Pnt,Boolean>();
	private int worldWidth = 120, worldHeight = 100;
	
	
	public VertexScreen() {
		cam = new OrthographicCamera(worldWidth, worldHeight);
		cam.translate(new Vector2(worldWidth/2,worldHeight/2));
		rend = new MyShapeRenderer();
		rend.setCamera(cam);

		initialTriangle = new Triangle(
            new Pnt(0, 0),
            new Pnt(worldWidth*50, 0),
            new Pnt(           0,  worldHeight*50));
		
		
		dt = new Triangulation(initialTriangle);
		
		for (int i = 0; i < 300; i++) {
			dt.delaunayPlace(new Pnt(rand.nextFloat() * worldWidth,rand.nextFloat()*worldHeight));
		}
		doColors();
	}
	


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	//	cam.lookAt(worldWidth/2, -worldHeight/2, 0);
	//	cam.lookAt(0.1f, 0, 0);
		
		if (Gdx.input.isTouched()) {
			Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
			cam.unproject(pos);
			dt.delaunayPlace(new Pnt(pos.x,pos.y));
			doColors();
		}
		
		
		drawTrianglulation();
		cam.update();
	}

	private void doColors() {
		Iterator<Triangle> ti = dt.iterator();
		while(ti.hasNext()) {
			Triangle t = ti.next();
			Iterator<Pnt> pi = t.iterator();
			while(pi.hasNext()) {
				Pnt p = pi.next();
				if (!cols.containsKey(p)) {
					cols.put(p, new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),1));
				}
				if (!free.containsKey(p)) {
					free.put(p, false);
				}
			}
		}
	}



	private void drawTrianglulation() {
		Iterator<Triangle> it = dt.iterator();
		while(it.hasNext()) {
			Triangle t = it.next();
			drawTriangle(t);
		}
	}



	private void drawTriangle(Triangle t) {
		Vector2 v1,v2,v3;
		Color c1,c2,c3;
		v1 = new Vector2((float)t.get(0).coord(0), (float)t.get(0).coord(1));
		v2 = new Vector2((float)t.get(1).coord(0), (float)t.get(1).coord(1));
		v3 = new Vector2((float)t.get(2).coord(0), (float)t.get(2).coord(1));
		
		c1 = cols.get(t.get(0));
		c2 = cols.get(t.get(1));
		c3 = cols.get(t.get(2));
		Color c1cp = c1.cpy();
		if (rand.nextFloat()<0.0000f) {
			float amount = 0.9f;
			float mul = 4;
			c1.mul(c2);
			c1.mul(mul);
			c2.mul(c3);
			c2.mul(mul);
			c3.lerp(c1cp, amount);
			c3.mul(mul);
		}
		rend.drawTriangle(v1, v2, v3, c1,c2,c3);		
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
