package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;


public class SoundScreen implements Screen {
	int counter =0; 
	Sound s;
	int phase = 0;
	
	@Override
	public void render(float delta) {
		if (counter % 30 == 0) {
			if (phase == 0) {
				s = Gdx.audio.newSound(Gdx.files.internal("8.12.mp3"));
				s.play();
			} else if (phase == 2) {
				s.stop();
			} else if (phase == 3) {
				s.dispose();
				phase = -1;
			}
			phase ++;
		}
		counter ++;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
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
		s.dispose();
	}

}
