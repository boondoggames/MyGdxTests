package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticleEffectScreen implements Screen {
	SpriteBatch batch = new SpriteBatch();
	ParticleEffect effect = new ParticleEffect();
	
	@Override
	public void show() {
		effect.load(Gdx.files.internal("particles/jetfumes.p"), Gdx.files.internal("particles"));
		effect.start();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		effect.setPosition(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY());
		effect.update(delta);
		batch.begin();
		effect.draw(batch);
		batch.end();
		
		if (effect.isComplete()) {
			effect.reset();
		}
	}

	
	
	@Override
	public void resize(int width, int height) {
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
		// TODO Auto-generated method stub

	}

}
