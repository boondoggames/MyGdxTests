package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PlanetShaderTestScreen implements Screen {
	final Vector2 CAMERA = new Vector2(200,100);
	int width, height;
	
	SpriteBatch batch;
	Texture img;
	OrthographicCamera cam;
	ShaderProgram shader;
	long startTime;
	int counter; 
		
	Vector3 planetPosition = new Vector3(0,0,0);
	float planetSize = 10;
	
	
	@Override
	public void show() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		cam = new OrthographicCamera(CAMERA.x,CAMERA.y);
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(Gdx.files.internal("shaders/myplanet.vsh"),Gdx.files.internal("shaders/myplanet.fsh"));

		System.out.println(shader.isCompiled() ? "Shader compiled" : shader.getLog());
		batch.setShader(shader);
		startTime = System.currentTimeMillis();
		Gdx.input.setInputProcessor(getInputProcessor());
	}

	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


/*		for (String s : shader.getUniforms()) {
			System.out.println(s + ": " + shader.getUniformType(s));
		}*/
		shader.begin();
	    shader.setUniformf("u_time", (System.currentTimeMillis()- startTime)/1000f);
		shader.end();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(img, -50,-25,100,50);
		batch.end();
		counter ++;

		if (counter % 100 == 0) {
			System.out.println(Gdx.graphics.getFramesPerSecond());
		}
		
	}
	
	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height= height;
		shader.begin();
		Vector3 tmp = planetPosition.cpy();
		cam.project(tmp);
		Vector2 tmp2 = new Vector2(tmp.x, tmp.y);
	    shader.setUniformf("u_planetPosition", tmp2);

	    
	    shader.setUniformf("u_resolution", width,height);
	    shader.setUniformf("u_planetSize", worldToScreen(planetSize));
	    
		shader.end();
	}

	public float worldToScreen(float x) {
		return (width/CAMERA.x) * x; 
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
	
	private InputProcessor getInputProcessor() {
		return new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
				switch(keycode) {
				case Keys.T:
					startTime = System.currentTimeMillis();
					break;
				
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
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub
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
			
		};
	}


}
