package engine.io;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.system.MemoryUtil;

public final class Window {

    private static final Window INSTANCE = new Window();
    private final Input input = Input.getInstance();
    private long window;
    private String title = "Coding CBA";
    private int width = 800, height = 600;
    private boolean resize = false, fullscreen = false, lock = false;
    private GLFWWindowSizeCallback windowSizeCallback;

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        GLFW.glfwInitHint(GLFW.GLFW_PLATFORM, GLFW.GLFW_PLATFORM_X11);

        if(!GLFW.glfwInit())
            throw new IllegalStateException("Failed to init GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 5);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 8);

        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);

        if(window == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create window");

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(0);

        createCallbacks();
    }

    public void loop(){
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public void cleanUp(){
        input.cleanUp();
        windowSizeCallback.free();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    private void createCallbacks(){
        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long l, int i, int i1) {
                width = i;
                height = i1;
                resize = true;
            }
        };

        GLFW.glfwSetKeyCallback(window, input.getKeyCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getCursorPosCallback());
        GLFW.glfwSetScrollCallback(window, input.getScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, windowSizeCallback);
    }

    public void setMouseState(boolean lock){
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, lock ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
        this.lock = lock;
    }

    public boolean shouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        resize = true;

        if(fullscreen){
            GLFW.glfwSetWindowPos(window, ((GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).width() - width) / 2), ((GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).height() - height) / 2));
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).width(), GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).height(), 0);
        }else {
            width = 800;
            height = 600;

            GLFW.glfwSetWindowMonitor(window, MemoryUtil.NULL, ((GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).width() - width) / 2), ((GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).height() - height) / 2), width, height, 0);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        GLFW.glfwSetWindowTitle(window, title);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isResize() {
        return resize;
    }

    public void setResize(boolean resize) {
        this.resize = resize;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public static Window getInstance(){
        return INSTANCE;
    }
}
