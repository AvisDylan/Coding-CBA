package engine.render;

import engine.objects.GameObject;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class Transformation {

    private static final Transformation INSTANCE = new Transformation();
    private final Matrix4f projectionMatrix, modelViewMatrix, viewMatrix;

    public Transformation() {
        this.projectionMatrix = new Matrix4f();
        this.modelViewMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
    }

    public Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar){
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, width / height, zNear, zFar);

        return projectionMatrix;
    }

    public Matrix4f getModelViewMatrix(GameObject gameObject, Matrix4f viewMatrix){
        Vector3f rotation = gameObject.getRotation();

        modelViewMatrix.identity().translate(gameObject.getPosition()).rotateX((float) Math.toRadians(-rotation.x)).rotateY((float) Math.toRadians(-rotation.y)).rotateZ((float) Math.toRadians(-rotation.z)).scale(gameObject.getScale());

        Matrix4f view = new Matrix4f(viewMatrix);

        return view.mul(modelViewMatrix);
    }

    public Matrix4f getViewMatrix(Camera camera){
        Vector3f cameraPos = camera.getPosition();
        Vector3f cameraRotations = camera.getRotation();

        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians(cameraRotations.x), new Vector3f(1, 0, 0)).rotate((float) Math.toRadians(cameraRotations.y), new Vector3f(0, 1, 0));
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        return viewMatrix;
    }

    public static Transformation getInstance(){
        return INSTANCE;
    }
}
