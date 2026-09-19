package engine.io.input;

import engine.io.Input;
import engine.render.Camera;

public class CameraController {

    private final Camera camera;
    private float speedMultiplier = 5;
    private float sensitivity = 0.15f;
    private double lastMouseX = -1, lastMouseY = -1;

    public CameraController(Camera camera) {
        this.camera = camera;
    }

    public void update(PlayerInput playerInput, Input input, float deltaTime){
        if(playerInput.isForward())
            camera.movePosition(0, 0, speedMultiplier * deltaTime);
        if(playerInput.isBackward())
            camera.movePosition(0, 0, -speedMultiplier * deltaTime);
        if(playerInput.isLeft())
            camera.movePosition(-speedMultiplier * deltaTime, 0, 0);
        if(playerInput.isRight())
            camera.movePosition(speedMultiplier * deltaTime, 0, 0);
        if(playerInput.isJump())
            camera.movePosition(0, speedMultiplier * deltaTime, 0);
        if(playerInput.isCrouch())
            camera.movePosition(0, -speedMultiplier * deltaTime, 0);

        double mouseX = input.getMouseX();
        double mouseY = input.getMouseY();

        if(lastMouseX == -1 || lastMouseY == -1){
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            return;
        }

        double deltaX = mouseX - lastMouseX;
        double deltaY = mouseY - lastMouseY;

        lastMouseX = mouseX;
        lastMouseY = mouseY;

        camera.moveRotations((float) (deltaY * sensitivity), (float) (deltaX * sensitivity), 0);

        if(camera.getRotation().x > 89)
            camera.getRotation().x = 89;

        if(camera.getRotation().x < -89)
            camera.getRotation().x = -89;
    }
}
