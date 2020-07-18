#version 330

layout (location = 0) in vec3 position0;

uniform mat4 modelViewProjectionMatrix;

layout(location=1) in vec3 normal;
smooth out vec3 vertexColour;
smooth out vec3 vertexNormal;

void main()
{
    vertexColour = vec3(0.7f, 0.f, 0.f);//
    vertexNormal = normal; //

    gl_Position = modelViewProjectionMatrix * vec4(position0,1);
}