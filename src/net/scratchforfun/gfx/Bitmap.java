package net.scratchforfun.gfx;

import net.scratchforfun.Window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Magnus on 8/3/2015.
 */
public class Bitmap {

    private int[] pixels;
    private int width, height;

    public Bitmap(int width, int height){
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }

    public static Bitmap getBitmap(String path){
        try {
            BufferedImage bImage = ImageIO.read(Window.class.getResourceAsStream(path));
            Bitmap bitmap = new Bitmap(bImage.getWidth(), bImage.getHeight());

            bImage.getRGB(0, 0, bitmap.width, bitmap.height, bitmap.pixels, 0, bitmap.width);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void renderBitmap(Bitmap bitmap, int x, int y){
        for(int wx = 0; wx < bitmap.width; wx++){
            for(int hy = 0; hy < bitmap.height; hy++){
                // TODO: Restrictions

                int pixel = bitmap.pixels[bitmap.getPixelPosition(wx, hy)];
                this.pixels[getPixelPosition(x + wx, y + hy)] = pixel;
            }
        }
    }

    private int getPixelPosition(int x, int y){
        return y*width + x;
    }

    public void setPixels(int[] pixels){
        this.pixels = pixels;
    }

    public void fill(int color){
        Arrays.fill(pixels, color);
    }

    @Deprecated
    public BufferedImage getImage(){
        BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bImage.setRGB(0, 0, width, height, pixels, 0, width);

        return bImage;
    }

}
