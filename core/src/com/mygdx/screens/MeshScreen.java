package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;


/**
 * Attempting a mesh
 * 
 * @author mattdesl george
 * @see https://gist.github.com/mattdesl/5793041
 */
public class MeshScreen implements Screen {
	
	public static final String VERT_SHADER =  
			"attribute vec2 a_position;\n" +
			"attribute vec4 a_color;\n" +			
			"uniform mat4 u_projTrans;\n" + 
			"varying vec4 vColor;\n" +			
			"void main() {\n" +  
			"	vColor = a_color;\n" +
			"	gl_Position =  u_projTrans * vec4(a_position.xy, 0.0, 1.0);\n" +
			"}";
	
	public static final String FRAG_SHADER = 
            "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
			"varying vec4 vColor;\n" + 			
			"void main() {\n" +  
			"	gl_FragColor = vColor;\n" + 
			"}";
	
	protected static ShaderProgram createMeshShader() {
		ShaderProgram.pedantic = false;
		ShaderProgram shader = new ShaderProgram(VERT_SHADER, FRAG_SHADER);
		String log = shader.getLog();
		if (!shader.isCompiled())
			throw new GdxRuntimeException(log);		
		if (log!=null && log.length()!=0)
			System.out.println("Shader Log: "+log);
		return shader;
	}
	
	Mesh mesh;
	OrthographicCamera cam;
	ShaderProgram shader;
		
	//Position attribute - (x, y) 
	public static final int POSITION_COMPONENTS = 2;
	
	//Color attribute - (r, g, b, a)
	public static final int COLOR_COMPONENTS = 4;
	
	//Total number of components for all attributes
	public static final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;

	//The maximum number of triangles our mesh will hold
	public static final int MAX_TRIS = 1;
	
	//The maximum number of vertices our mesh will hold
	public static final int MAX_VERTS = MAX_TRIS * 4;
	
	//The array which holds all the data, interleaved like so:
	//    x, y, r, g, b, a
	//    x, y, r, g, b, a, 
	//    x, y, r, g, b, a, 
	//    ... etc ...
	private float[] verts = new float[MAX_VERTS * NUM_COMPONENTS];
	
	//The index position
	private int idx = 0;
	
	public MeshScreen () {
		mesh = new Mesh(true, MAX_VERTS, 0, 
				new VertexAttribute(Usage.Position, POSITION_COMPONENTS, "a_position"),
				new VertexAttribute(Usage.Color, COLOR_COMPONENTS, "a_color"));
		
		shader = createMeshShader();
		cam = new OrthographicCamera();
	}

	@Override
	public void resize(int width, int height) {
	}
	
	void flush() {
		//if we've already flushed
		if (idx==0)
			return;
		
		//sends our vertex data to the mesh
		mesh.setVertices(verts);
		
		//no need for depth...
		Gdx.gl.glDepthMask(false);
		
		//enable blending, for alpha
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		//number of vertices we need to render
		int vertexCount = (idx/NUM_COMPONENTS);
		
		//update the camera with our Y-up coordiantes
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//start the shader before setting any uniforms
		shader.begin();
		
		//update the projection matrix so our triangles are rendered in 2D
		shader.setUniformMatrix("u_projTrans", cam.combined);
		
		//render the mesh
		mesh.render(shader, GL20.GL_TRIANGLE_STRIP, 0, vertexCount);
		
		shader.end();
		
		//re-enable depth to reset states to their default
		Gdx.gl.glDepthMask(true);
		
		//reset index to zero
		idx = 0;
	}
	
	void drawTriangle(float x, float y, float width, float height, Color color1, Color color2, Color color3) {
		//we don't want to hit any index out of bounds exception...
		//so we need to flush the batch if we can't store any more verts
		if (idx==verts.length)
			flush();
		
		//now we push the vertex data into our array
		//we are assuming (0, 0) is lower left, and Y is up
		
		//bottom left vertex
		verts[idx++] = x; 			//Position(x, y) 
		verts[idx++] = y;
		verts[idx++] = color1.r; 	//Color(r, g, b, a)
		verts[idx++] = color1.g;
		verts[idx++] = color1.b;
		verts[idx++] = color1.a;
		
		//top left vertex
		verts[idx++] = x; 			//Position(x, y) 
		verts[idx++] = y + height;
		verts[idx++] = color2.r; 	//Color(r, g, b, a)
		verts[idx++] = color2.g;
		verts[idx++] = color2.b;
		verts[idx++] = color2.a;

		//bottom right vertex
		verts[idx++] = x + width;	 //Position(x, y) 
		verts[idx++] = y;
		verts[idx++] = color3.r;		 //Color(r, g, b, a)
		verts[idx++] = color3.g;
		verts[idx++] = color3.b;
		verts[idx++] = color3.a;
	}
	
	void drawLine(Vector2 a, Vector2 b, float width, Color color) {
		drawHalfLine(a,b,width,color,true);
		drawHalfLine(a,b,width,color,false);
	}
	
	void drawHalfLine(Vector2 a, Vector2 b, float width, Color color, boolean top) {
		//we don't want to hit any index out of bounds exception...
		//so we need to flush the batch if we can't store any more verts
		if (idx==verts.length) {
			flush();
		}
		
		// These positions are positions on a horizontal line a ------ b 
		Vector2 norm,bottomLeft, bottomRight, topLeft, topRight;
		
		norm = b.cpy();
		norm.sub(a);
		norm.rotate(90);
		norm.nor();
		norm.scl(width/2);
		if (top) {
			bottomLeft = a;
			bottomRight = b;
			topLeft = a.cpy().add(norm);
			topRight = b.cpy().add(norm);
		} else {
			bottomLeft = a.cpy().sub(norm);
			bottomRight = b.cpy().sub(norm);
			topLeft = a;
			topRight = b;
		}

		
		// Bottom left of line
		verts[idx++] = bottomLeft.x;
		verts[idx++] = bottomLeft.y;
		verts[idx++] = color.r; 	//Color(r, g, b, a)
		verts[idx++] = color.g;
		verts[idx++] = color.b;
		verts[idx++] = top ? 1 : 0;
		
		//top left vertex
		verts[idx++] = topLeft.x; 			//Position(x, y) 
		verts[idx++] = topLeft.y;
		verts[idx++] = color.r; 	//Color(r, g, b, a)
		verts[idx++] = color.g;
		verts[idx++] = color.b;
		verts[idx++] = top ? 0 : 1;

		//bottom right vertex
		verts[idx++] = bottomRight.x;	 //Position(x, y) 
		verts[idx++] = bottomRight.y;
		verts[idx++] = color.r;		 //Color(r, g, b, a)
		verts[idx++] = color.g;
		verts[idx++] = color.b;
		verts[idx++] = top ? 1 : 0;
		
		//top right vertex
		verts[idx++] = topRight.x;	 //Position(x, y) 
		verts[idx++] = topRight.y;
		verts[idx++] = color.r;		 //Color(r, g, b, a)
		verts[idx++] = color.g;
		verts[idx++] = color.b;
		verts[idx++] = top ? 0 : 1;
		
		
		
				
	}
	void drawSquare(float x, float y, float width, float height, Color color1, Color color2, Color color3, Color color4) {
		//we don't want to hit any index out of bounds exception...
		//so we need to flush the batch if we can't store any more verts
		if (idx==verts.length)
			flush();
		
		//now we push the vertex data into our array
		//we are assuming (0, 0) is lower left, and Y is up
		
		//bottom left vertex
		verts[idx++] = x; 			//Position(x, y) 
		verts[idx++] = y;
		verts[idx++] = color1.r; 	//Color(r, g, b, a)
		verts[idx++] = color1.g;
		verts[idx++] = color1.b;
		verts[idx++] = color1.a;
		
		//top left vertex
		verts[idx++] = x; 			//Position(x, y) 
		verts[idx++] = y + height;
		verts[idx++] = color2.r; 	//Color(r, g, b, a)
		verts[idx++] = color2.g;
		verts[idx++] = color2.b;
		verts[idx++] = color2.a;

		//bottom right vertex
		verts[idx++] = x + width;	 //Position(x, y) 
		verts[idx++] = y;
		verts[idx++] = color3.r;		 //Color(r, g, b, a)
		verts[idx++] = color3.g;
		verts[idx++] = color3.b;
		verts[idx++] = color3.a;
		
		//top right vertex
		verts[idx++] = x + height;	 //Position(x, y) 
		verts[idx++] = y + width;
		verts[idx++] = color4.r;		 //Color(r, g, b, a)
		verts[idx++] = color4.g;
		verts[idx++] = color4.b;
		verts[idx++] = color4.a;
	}
	
	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		mesh.dispose();
		shader.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//this will push the triangles into the batch
	//	drawSquare(50, 50, 400, 300, Color.BLUE, Color.RED, new Color(0,1,0,0.5f), Color.WHITE);
		long start = System.nanoTime();
		drawLine(new Vector2(50,50), new Vector2(500,100),5, Color.WHITE);
		System.out.println("time taken:" + ((double)(System.nanoTime() - start)/1000000d) + "  fps: " + Gdx.graphics.getFramesPerSecond());
		//this will render the triangles to GL
		flush();		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}