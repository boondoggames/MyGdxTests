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

public class MultiShaderTestScreen implements Screen {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera cam;
	ShaderProgram shader, shader2;
	long startTime;
	int counter; 
	@Override
	public void show() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		cam = new OrthographicCamera(200,100);
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(Gdx.files.internal("shaders/mattdes.vsh"),Gdx.files.internal("shaders/mattdes.fsh"));
		shader2 = new ShaderProgram(Gdx.files.internal("shaders/vignette.vsh"),Gdx.files.internal("shaders/vignette.fsh"));

		System.out.println(shader.isCompiled() ? "Shader compiled" : shader.getLog());
		batch.setShader(shader);
		startTime = System.currentTimeMillis();
		Gdx.input.setInputProcessor(getInputProcessor());
	}

	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


/*		for (String s : shader.getUniforms()) {
			System.out.println(s + ": " + shader.getUniformType(s));
		}*/
		shader.begin();
	    shader.setUniformf("u_time", (System.currentTimeMillis()- startTime)/1000f);
		shader.end();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.setShader(shader);
		batch.draw(img, -100,-50,100,50);
		batch.setShader(shader2);
		batch.draw(img, 0,0,100,50);
		batch.end();
		counter ++;

		if (counter % 100 == 0) {
			System.out.println(Gdx.graphics.getFramesPerSecond());
		}
		
	}
	
	@Override
	public void resize(int width, int height) {
		shader.begin();
	    shader.setUniformf("u_resolution", width,height);
		shader.end();
		shader2.begin();
	    shader2.setUniformf("u_resolution", width,height);
		shader2.begin();
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
