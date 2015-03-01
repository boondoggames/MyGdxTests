// varying space - shared between vertex and fragment shader
varying vec4 v_color;
varying vec2 v_texCoord0;

// unform - stuff specific to this work space 
uniform sampler2D u_sampler2D; // samples single pixels from textures (to do something later)

void main() {
	vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
	color.rgb = 1. - color.rgb;
	gl_FragColor = color; // Feedthrough
}