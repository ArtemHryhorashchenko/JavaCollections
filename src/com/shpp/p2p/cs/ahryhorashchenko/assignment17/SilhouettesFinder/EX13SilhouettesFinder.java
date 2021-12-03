package com.shpp.p2p.cs.ahryhorashchenko.assignment17.SilhouettesFinder;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Program find silhouettes at the image what entered
 * <p>
 * by default the file is assets\\test.jpg
 * if you check other files you can throw them to the root
 * you can just write the file name and you can also throw
 * them in the assets folder to make it easier for you to enter
 * file names comment out line 23 and uncomment 22 and you can
 * will not write assets but only the file name
 */
public class EX13SilhouettesFinder implements EX13Constants {

    /**
     * Method which run the program
     *
     * @param args arguments of program for entering arguments open Run/Debug Configurations
     *             if you entered nothing by default arguments will be DEFAULT_FILE from constants
     */
    public void run(String[] args) throws Exception {
        BufferedImage image;
        if (args.length == 0) {
            args = new String[1];
            args[0] = DEFAULT_FILE;
        }

        try {
            //image = ImageIO.read(new File("assets\\" + args[0]));
            image = ImageIO.read(new File(args[0]));
            EX13ImageProcessing im = new EX13ImageProcessing(image);
            int silhouettes = im.findSilhouettes();
            System.out.println(silhouettes + " silhouette(s)");
        } catch (Exception e) {
            System.out.println("Can`t find file!");
        }
    }


}
