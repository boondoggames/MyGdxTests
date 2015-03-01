// varying space - shared between vertex and fragment shader
varying vec4 v_color;
varying vec2 v_texCoord0;

// unform - stuff specific to this work space 
uniform sampler2D u_sampler2D; // samples single pixels from textures (to do something later)
uniform vec2 u_resolution;
uniform float u_time;

// This is a copy of this: http://glsl.heroku.com/e#6607.4
const vec3 BLUE = vec3(29.0/255.0, 37.0/255.0, 49.0/255.0);

void main() {
	vec2 position = (gl_FragCoord.xy / u_resolution.xy);

	float c = length(position - 0.5);
	float len = length(position*3.0);
	float r = sin(gl_FragCoord.y*0.45 * (sin(len*1.2 + (u_time*0.015))/2.0) + u_time*0.1)*300.0;

	r = clamp(r, 0.8, 1.5);
	
	float vignette = clamp(smoothstep(0.95, 0.3, c), 0.0, 1.0);
	
	vec3 color = vec3(r) * BLUE * vignette;

	gl_FragColor = vec4(color, 1.0);
}