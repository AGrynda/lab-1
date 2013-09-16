import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Andrii
 * Date: 15.09.13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class MyThread extends Thread {
    BufferedImage image;
    int firstline, lineCount;

    public MyThread(BufferedImage img, int firstline, int lineCount){
        this.firstline = firstline;
        this.lineCount = lineCount;
        this.image  = img;
    }

    @Override
    public void run() {
         this.Convert();
    }

    private  void Convert()
    {
        int alpha, red, green, blue, grey;

        for (int j = this.firstline; j <= this.firstline + this.lineCount; j++) {
            for(int i = 0; i < this.image.getWidth(); i++){
                alpha = new Color(image.getRGB(i, j)).getAlpha();
                red = new Color(image.getRGB(i, j)).getRed();
                green = new Color(image.getRGB(i, j)).getGreen();
                blue = new Color(image.getRGB(i, j)).getBlue();

                grey = (int) (0.2126 * red + 0.71526 * green + 0.0722 * blue);

                image.setRGB(i, j, colorToRGB(alpha, grey));
            }
        }
    }

    private static int colorToRGB(int alpha, int grey) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += grey;
        newPixel = newPixel << 8;
        newPixel += grey;
        newPixel = newPixel << 8;
        newPixel += grey;

        return newPixel;
    }
}
