package engine.render;

import org.joml.Vector3f;

public class Camera {

    private Vector3f position, rotation;

    public Camera(){
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ){
        float yaw = (float) Math.toRadians(rotation.y);

        float forwardX = (float) Math.sin(yaw);
        float forwardZ = (float) -Math.cos(yaw);
        float strafeX = (float) Math.sin(yaw + Math.PI / 2);
        float strafeZ = (float) -Math.cos(yaw + Math.PI / 2);

        position.x += forwardX * offsetZ;
        position.z += forwardZ * offsetZ;

        position.x += strafeX * offsetX;
        position.z += strafeZ * offsetX;

        position.y += offsetY;
    }

    public void moveRotations(float offsetX, float offsetY, float offsetZ){
        rotation.add(offsetX, offsetY, offsetZ);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
