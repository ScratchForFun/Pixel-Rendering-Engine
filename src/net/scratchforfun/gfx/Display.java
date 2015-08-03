package net.scratchforfun.gfx;

import net.scratchforfun.Window;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by Magnus on 8/1/2015.
 */
public class Display extends Canvas {

    private BufferedImage bImage;
    private Bitmap screen;

    private Bitmap PUTIN;

    public Display(Window window){
        setBounds(0, 0, window.WIDTH, window.HEIGHT);

        this.bImage = new BufferedImage(window.WIDTH, window.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.screen = new Bitmap(window.WIDTH, window.HEIGHT);
        this.screen.setPixels(((DataBufferInt) bImage.getRaster().getDataBuffer()).getData());

        // LOAD
        this.PUTIN = Bitmap.getBitmap("/putin.jpg");
    }

    public void render(Graphics g){
        // RENDER STUFF
        screen.fill(Color.CYAN.getRGB());
        screen.renderBitmap(PUTIN, 10, 10);
        // RENDER END

        g.drawImage(bImage, 0, 0, null);
    }

    public void tick(){

    }

}
