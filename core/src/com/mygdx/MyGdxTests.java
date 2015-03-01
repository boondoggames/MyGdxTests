package com.mygdx;

import com.badlogic.gdx.Game;
import com.mygdx.screens.*;


public class MyGdxTests extends Game {
	@Override
	public void create () {
		//setScreen(new PlanetShaderTestScreen());
		setScreen(new TriangulationTestScreen());
	}
}
