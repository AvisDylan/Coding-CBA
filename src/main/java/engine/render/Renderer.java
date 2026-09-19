package engine.render;

import engine.io.Window;
import engine.objects.GameObject;
import engine.utils.FileUtils;
import org.joml.Matrix4f;
import org.lwjgl.opengl.*;

import java.io.IOException;

public final class Renderer {

    private static final Renderer INSTANCE = new Renderer();
    private static final float Z_NEAR = 0.01f, Z_FAR = 1000;
    private final Transformation transformation = Transformation.getInstance();
    private final ShaderProgram mainShader = new ShaderProgram();

    public void init(Window window) throws IOException {
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL13.GL_MULTISAMPLE);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glViewport(0, 0, window.getWidth(), window.getHeight());

        mainShader.init();
        mainShader.createVertexShader(FileUtils.readFileToString("shader/main.vert"));
        mainShader.createFragmentShader(FileUtils.readFileToString("shader/main.frag"));
        mainShader.link();
        mainShader.createUniform("projectionMatrix");
        mainShader.createUniform("modelViewMatrix");
        mainShader.createUniform("textureSampler");
    }

    public void render(GameObject[] gameObjects, Camera camera, Window window){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        if(window.isResize()){
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(false);
        }

        mainShader.bind();

        Matrix4f projectionMatrix = transformation.getProjectionMatrix(90, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        mainShader.setUniform("projectionMatrix", projectionMatrix);
        mainShader.setUniform("textureSampler", 0);

        for(GameObject g : gameObjects){
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(g, viewMatrix);
            mainShader.setUniform("modelViewMatrix", modelViewMatrix);

            g.getMesh().render();
        }

        mainShader.unbind();
    }

    public void cleanUp(){
        mainShader.cleanUp();
    }

    public static Renderer getInstance(){
        return INSTANCE;
    }
}
