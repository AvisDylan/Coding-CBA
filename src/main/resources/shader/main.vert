#version 450 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoords;

out vec2 outTexCoords;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main() {
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1);
    outTexCoords = texCoords;
}