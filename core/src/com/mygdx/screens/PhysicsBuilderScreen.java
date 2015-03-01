package com.mygdx.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class PhysicsBuilderScreen implements Screen {
	public static final int worldWidth = 300, worldHeight = 160;

	SpriteBatch batch = new SpriteBatch();
	Sprite backgroundSprite;
	Viewport viewport;
	Stage stage;
	Random rand = new Random();

	Box2DDebugRenderer b2drend;
	World world;
	
	public PhysicsBuilderScreen() {
		setupSprite();
		viewport = new StretchViewport(worldWidth, worldHeight); 
		stage = new Stage(viewport,batch);
		world = new World(new Vector2(0,-5), true);
		b2drend = new Box2DDebugRenderer();
		
		
		int squares = 50;
		
		Vector2 size = new Vector2(1,1);
		Vector2 pos = new Vector2();
		Vector2 c = new Vector2(0,0);
		for (int x = 0; x < squares; x++) {
			for (int y = 0; y < squares; y++) {
				pos.x = x * (size.x*2) + c.x+rand.nextFloat();
				pos.y = y * (size.y*2) + c.y+rand.nextFloat();
				makeSquare(pos,size);
				
			}
		}
		
	}
	
	private void makeSquare(Vector2 centerPos, Vector2 size) {
		// Initialise the actual physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(centerPos.x, centerPos.y);
        bodyDef.bullet = false;
        Body body = world.createBody(bodyDef);

        
        // Apply some rotational force here?
//        PolygonShape borderShape = new PolygonShape();
//		borderShape.setAsBox(size.x,size.y); // b2d uses the half size and width
		CircleShape borderShape = new CircleShape();
		borderShape.setRadius(size.x);; // b2d uses the half size and width
					
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = borderShape;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 1f; // Make it bounce a little bit
		fixtureDef.density = 1f;
		
        body.createFixture(fixtureDef);
        borderShape.dispose();
        
	}


	private void setupSprite() {
		backgroundSprite = new Sprite(new Texture("ship.png")); 	
		backgroundSprite.setSize(11*0.65f, 11f);
		backgroundSprite.setCenter(0, 0);
	}


	@Override
	public void show() {

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(viewport.getCamera().combined);
		
		batch.begin();
		backgroundSprite.draw(batch);
		batch.end();
		
		stage.draw();
		b2drend.render(world, viewport.getCamera().combined);
		world.step(delta, 1, 1);
		
		
		Array<Body> bodies = new Array<Body>();
		 world.getBodies(bodies);
		if (rand.nextFloat() < 0.05) {
			System.out.println("happens");
			for (Body b : bodies) {
		        b.applyForceToCenter(new Vector2(rand.nextFloat()*500, rand.nextFloat()*500), true);

			}
		}
	}

	
	
	@Override
	public void resize(int width, int height) {
		viewport.getCamera().translate(-worldWidth/4, -worldHeight/4, 0);
		viewport.update(width, height);

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
