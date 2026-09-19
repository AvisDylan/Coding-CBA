package engine.io.input;

import engine.io.Window;

public class MiscController {

    public void update(MiscInput miscInput, Window window){
        if(miscInput.isFocus()){
            window.setFullscreen(!window.isFullscreen());
            window.setMouseState(!window.isLock());
        }
    }
}
