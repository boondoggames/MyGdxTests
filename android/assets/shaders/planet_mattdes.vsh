// attributes are final!
attribute vec3 a_position;  // x,y,z

// uniform - these are our 'normal' variables, specific to the vertex shader
uniform mat4 u_projTrans; 	// given by spritebatch, NOT opengl
uniform vec2 u_resolution;

void main() {
	gl_Position = u_projTrans * vec4(a_position, 1.0);
}