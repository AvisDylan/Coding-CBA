package engine.io.input;

import engine.io.Input;
import org.lwjgl.glfw.GLFW;

public final class MiscInput {

    private static final MiscInput INSTANCE = new MiscInput();
    private boolean focus;

    public void update(Input input){
        focus = input.getKeys()[GLFW.GLFW_KEY_F11];
    }

    public boolean isFocus() {
        return focus;
    }

    public static MiscInput getInstance(){
        return INSTANCE;
    }
}
