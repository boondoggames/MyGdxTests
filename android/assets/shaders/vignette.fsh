// varying space - shared between vertex and fragment shader
varying vec4 v_color;
varying vec2 v_texCoord0;

// unform - stuff specific to this work space 
uniform sampler2D u_sampler2D; // samples single pixels from textures (to do something later)
uniform vec2 u_resolution;

const float outer_radius = 0.45, inner_radius = 0.4;
const float intensity = 0.65;
void main() {
	vec4 color =  texture2D(u_sampler2D, v_texCoord0) * v_color; // Feedthrough
	
	vec2 relativePosition = gl_FragCoord.xy / u_resolution - 0.5;

	// We can multiply by the aspect ratio here (u_resolution.x/u_resolution.y) for a perfect circle
	relativePosition.x *= (u_resolution.x/u_resolution.y);

	float len = length(relativePosition);

	float vignette = smoothstep(outer_radius,0.4,len);
	
	color.rgb = mix(color.rgb, color.rgb * vignette, intensity);

	gl_FragColor = color;
}