package engine.objects.impl;

import engine.objects.GameObject;
import engine.render.ModelLoader;

public class StansfordDragon extends GameObject {

    public StansfordDragon() {
        super(ModelLoader.loadModel("src/main/resources/models/dragon.obj", "src/main/resources/textures/CustomUVChecker_byValle_1K.png"));
    }
}
