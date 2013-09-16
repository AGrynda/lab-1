import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Andrii
 * Date: 15.09.13
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class ParallelImageGrayscale {
    public static void main(String[] args)
    {
        int threadCount = Integer.parseInt(args[0]);
        String imagePath = args[1];
        new ParallelImageGrayscale(threadCount, imagePath);
    }

    public  ParallelImageGrayscale(int threadCount, String imagePath)
    {
        try {
            File file = new File(imagePath);
            BufferedImage img = ImageIO.read(file);
            String fileExtension = imagePath.substring(imagePath.lastIndexOf('.') + 1);
            String newFileName = imagePath.substring(0, imagePath.lastIndexOf("."))
                                     + "_gray."
                                     + fileExtension;
            int delta = img.getHeight()/threadCount;
            System.out.print("THREADS  -  " + threadCount + "\n");
            MyThread[] threads  = new MyThread[threadCount];
            Date date = new Date();
            for (int i = 0; i < threadCount - 1; i++){
                threads[i] = new MyThread(img, i*delta, delta);
                threads[i].start();
                threads[i].join();
            }

            threads[threadCount - 1] = new MyThread(img, (threadCount - 1)*delta, img.getHeight() - (threadCount - 1)*delta - 1);
            threads[threadCount - 1].start();
            threads[threadCount - 1].join();

            System.out.print("TIME  -  " + ((new Date()).getTime() - date.getTime()));

            File outputfile = new File(newFileName);
            ImageIO.write(img, fileExtension, outputfile);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
