// varying space - shared between vertex and fragment shader
varying vec4 v_color;
varying vec2 v_texCoord0;

// unform - stuff specific to this work space 
uniform sampler2D u_sampler2D; // samples single pixels from textures (to do something later)

void main() {
	gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color; // Feedthrough
}