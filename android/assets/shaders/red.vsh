// This runs over every VERTEX!


//Types: vec2, vec3, vec4, bool, float, int
// matrix mat2 mat3 mat4


// attributes are final!
attribute vec4 a_color; 	// rgba
attribute vec3 a_position;  // x,y,z
attribute vec2 a_texCoord0; // 2d texture coordinate


// varying space - shared between vertex and fragment shader
varying vec4 v_color;
varying vec2 v_texCoord0;

// uniform - these are our 'normal' variables, specific to the vertex shader
uniform mat4 u_projTrans; 	// given by spritebatch, NOT opengl
// this is getProjectionMatrix();



void main() {
	v_color = a_color;
	v_texCoord0 = a_texCoord0;
	gl_Position = u_projTrans * vec4(a_position, 1.0);
}