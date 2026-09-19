package engine.objects.impl;

import engine.objects.GameObject;
import engine.render.ModelLoader;

public class StansfordBunny extends GameObject {

    public StansfordBunny() {
        super(ModelLoader.loadModel("src/main/resources/models/bunny.obj", "src/main/resources/textures/CustomUVChecker_byValle_1K.png"));
    }
}
