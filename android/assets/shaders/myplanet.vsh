// attributes are final!
attribute vec3 a_position;  // x,y,z
attribute vec2 a_texCoord0; // 2d texture coordinate
attribute vec4 a_color; 	// rgba

// uniform - these are our 'normal' variables, specific to the vertex shader
uniform mat4 u_projTrans; 	// given by spritebatch, NOT opengl
uniform vec2 u_resolution;

// varying space - shared between vertex and fragment shader
varying vec2 v_texCoord0;

void main() {
	v_texCoord0 = a_texCoord0;
	gl_Position = u_projTrans * vec4(a_position, 1.0);
}