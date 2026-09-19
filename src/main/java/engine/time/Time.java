package engine.time;

public final class Time {

    private static float deltaTime;
    private static long lastTime = System.nanoTime();

    public static void update(){
        long now = System.nanoTime();
        deltaTime = (now - lastTime) / 1_000_000_000.0f;
        lastTime = now;
    }

    public static float getDeltaTime() {
        return deltaTime;
    }
}
