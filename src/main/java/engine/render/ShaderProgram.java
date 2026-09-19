package engine.render;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class ShaderProgram {

    private final Map<String, Integer> uniforms = new HashMap<>();
    private int programId;
    private int vertexId;
    private int fragmentId;

    public void init(){
        programId = GL30.glCreateProgram();

        if(programId == 0)
            throw new RuntimeException("Failed to create shader program");
    }

    public void createVertexShader(String code){
        vertexId = createShader(code, GL30.GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String code){
        fragmentId = createShader(code, GL30.GL_FRAGMENT_SHADER);
    }

    public void createUniform(String name){
        int uniformLocation = GL20.glGetUniformLocation(programId, name);

        if(uniformLocation < 0)
            throw new RuntimeException("Can't fidn uniform " + name);

        uniforms.put(name, uniformLocation);
    }

    public void setUniform(String name, int value){
        GL20.glUniform1i(uniforms.get(name), value);
    }

    public void setUniform(String name, Matrix4f value){
        try(MemoryStack stack = MemoryStack.stackPush()){
            FloatBuffer floatBuffer = stack.mallocFloat(16);

            value.get(floatBuffer);

            GL20.glUniformMatrix4fv(uniforms.get(name), false, floatBuffer);
        }
    }

    public void link(){
        GL30.glLinkProgram(programId);

        if(GL30.glGetProgrami(programId, GL30.GL_LINK_STATUS) == 0)
            throw new RuntimeException("Failed to link shader");

        if(vertexId != 0)
            GL30.glDetachShader(programId, vertexId);

        if(fragmentId != 0)
            GL30.glDetachShader(programId, fragmentId);

        GL30.glValidateProgram(programId);

        if(GL30.glGetProgrami(programId, GL30.GL_VALIDATE_STATUS) == 0)
            System.err.println("Warning validating shader code" + GL30.glGetProgramInfoLog(programId, 4028));
    }

    public void bind(){
        GL30.glUseProgram(programId);
    }

    public void unbind(){
        GL30.glUseProgram(0);
    }

    public void cleanUp(){
        unbind();

        if(programId != 0)
            GL30.glDeleteProgram(programId);
    }

    private int createShader(String code, int type){
        int shaderId = GL30.glCreateShader(type);

        if(shaderId == 0)
            throw new RuntimeException("Failed to create shader");

        GL30.glShaderSource(shaderId, code);
        GL30.glCompileShader(shaderId);

        if(GL30.glGetShaderi(shaderId, GL30.GL_COMPILE_STATUS) == 0)
            throw new RuntimeException("Failed to compile shader");

        GL30.glAttachShader(programId, shaderId);

        return shaderId;
    }
}
