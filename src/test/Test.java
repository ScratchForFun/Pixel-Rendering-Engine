package test;

import net.scratchforfun.Window;

/**
 * Created by Magnus on 24/07/2015.
 */
public class Test {

    public static void main(String[] args){
        new Test();
    }

    public Test(){
        Window window = new Window("Pixel Rendering Engine", 640, 480);
        window.start();
        //window.stop();
    }

}
