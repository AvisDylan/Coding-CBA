package engine.io;

import org.lwjgl.glfw.*;

public final class Input {

    private static final Input INSTANCE = new Input();
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private double mouseX, mouseY;
    private double scrollX, scrollY;
    private final GLFWKeyCallback keyCallback;
    private final GLFWCursorPosCallback cursorPosCallback;
    private final GLFWMouseButtonCallback mouseButtonCallback;
    private final GLFWScrollCallback scrollCallback;

    public Input(){
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long l, int i, int i1, int i2, int i3) {
                keys[i] = (i2 != GLFW.GLFW_RELEASE);
            }
        };
        cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long l, double v, double v1) {
                mouseX = v;
                mouseY = v1;
            }
        };
        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long l, int i, int i1, int i2) {
                buttons[i] = (i1 != GLFW.GLFW_RELEASE);
            }
        };
        scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long l, double v, double v1) {
                scrollX += v;
                scrollY += v1;
            }
        };
    }

    public void cleanUp(){
        keyCallback.free();
        scrollCallback.free();
        mouseButtonCallback.free();
        cursorPosCallback.free();
    }

    public boolean[] getKeys() {
        return keys;
    }

    public void setKeys(boolean[] keys) {
        this.keys = keys;
    }

    public boolean[] getButtons() {
        return buttons;
    }

    public void setButtons(boolean[] buttons) {
        this.buttons = buttons;
    }

    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public double getScrollX() {
        return scrollX;
    }

    public void setScrollX(double scrollX) {
        this.scrollX = scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    public void setScrollY(double scrollY) {
        this.scrollY = scrollY;
    }

    public GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    public GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    public GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

    public static Input getInstance(){
        return INSTANCE;
    }
}
