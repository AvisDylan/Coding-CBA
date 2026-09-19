package engine;

import engine.io.Input;
import engine.io.Window;
import engine.io.input.CameraController;
import engine.io.input.MiscController;
import engine.io.input.MiscInput;
import engine.io.input.PlayerInput;
import engine.objects.GameObject;
import engine.objects.impl.StansfordBunny;
import engine.objects.impl.StansfordDragon;
import engine.render.Camera;
import engine.render.Material;
import engine.render.Mesh;
import engine.render.Renderer;
import engine.time.Time;
import org.joml.Vector3f;

import java.io.IOException;

public final class Engine {
//a
    private static final Engine INSTANCE = new Engine();
    private final Window window = Window.getInstance();
    private final Renderer renderer = Renderer.getInstance();
    private final PlayerInput playerInput = PlayerInput.getInstance();
    private final MiscInput miscInput = MiscInput.getInstance();
    private final Input input = Input.getInstance();
    private final Camera camera = new Camera();
    private final CameraController cameraController = new CameraController(camera);
    private final MiscController miscController = new MiscController();
//    private StansfordDragon stansfordDragon;
    private StansfordBunny stansfordBunny;

    public void run(){
        try{
            start();
            loop();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cleanUp();
        }
    }

    private void start() throws IOException {
        window.init();
        renderer.init(window);
//        stansfordDragon = new StansfordDragon();
        stansfordBunny = new StansfordBunny();
    }

    private void loop(){
        while(!window.shouldClose()){
            Time.update();
            miscInput.update(input);
            playerInput.update(input);
            miscController.update(miscInput, window);
            cameraController.update(playerInput, input, Time.getDeltaTime());
            renderer.render(new GameObject[]{ /*stansfordDragon, */stansfordBunny }, camera, window);
            window.loop();
        }
    }

    private void cleanUp(){
        stansfordBunny.getMesh().cleanUp();
//        stansfordDragon.getMesh().cleanUp();
        renderer.cleanUp();
        window.cleanUp();
    }

    public static Engine getInstance(){
        return INSTANCE;
    }
}
