package engine.io.input;

import engine.io.Input;
import org.lwjgl.glfw.GLFW;

public final class PlayerInput {

    private static final PlayerInput INSTANCE = new PlayerInput();
    private boolean crouch, jump, forward, backward, left, right;

    public void update(Input input){
        crouch = input.getKeys()[GLFW.GLFW_KEY_LEFT_SHIFT];
        jump = input.getKeys()[GLFW.GLFW_KEY_SPACE];
        forward = input.getKeys()[GLFW.GLFW_KEY_W];
        backward = input.getKeys()[GLFW.GLFW_KEY_S];
        left = input.getKeys()[GLFW.GLFW_KEY_A];
        right = input.getKeys()[GLFW.GLFW_KEY_D];
    }

    public boolean isCrouch() {
        return crouch;
    }

    public boolean isJump() {
        return jump;
    }

    public boolean isForward() {
        return forward;
    }

    public boolean isBackward() {
        return backward;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public static PlayerInput getInstance(){
        return INSTANCE;
    }
}
