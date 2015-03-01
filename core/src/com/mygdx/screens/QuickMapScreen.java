package com.mygdx.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.lines.MyShapeRenderer;

public class QuickMapScreen implements Screen {
	public static final int worldWidth = 300, worldHeight = 160;
	float w = 7.64f;
	float h = 5.82f;
	
	
	SpriteBatch batch = new SpriteBatch();
	ShapeRenderer rend = new ShapeRenderer();
	MyShapeRenderer myrend = new MyShapeRenderer();
	Sprite backgroundSprite;
	Viewport viewport;
	Stage stage;
	Random rand = new Random();

	Box2DDebugRenderer b2drend;
	
	public QuickMapScreen() {
		setupSprite();
		viewport = new StretchViewport(worldWidth, worldHeight);
		stage = new Stage(viewport,batch);	
		w = 7.64f; h = 5.82f;
	}

	private void setupSprite() {
		backgroundSprite = new Sprite(new Texture("wargame.png")); 	
		backgroundSprite.setSize(worldWidth, worldHeight);
	}


	@Override
	public void show() {

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(viewport.getCamera().combined);
		rend.setProjectionMatrix(viewport.getCamera().combined);
		myrend.setCamera(viewport.getCamera());
		batch.begin();
		backgroundSprite.draw(batch);
		batch.end();
				
		rend.begin(ShapeType.Line);
		
		float ww = 0;
		while (ww<worldWidth) {
			rend.line(ww, 0, ww, worldHeight);
			ww += w;
		}
		
		float hh = 3.1f;
		while(hh<worldHeight) {
			rend.line(0, hh, worldWidth, hh);
			hh += h;
		}
		
		rend.end();
		
				
		stage.draw();

	}

	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height,true);
		viewport.getCamera().lookAt(worldWidth/2, worldHeight/2, 0);

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
		// TODO Auto-generated method stub

	}

}
