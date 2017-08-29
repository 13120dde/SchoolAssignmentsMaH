package BildoTextu2PointOperations;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Patrik Lind, Johan Held on 2016-11-17.
 *
 * This class is used to change a image's contrast and brightness values by doing a pointoperation and altering each
 * pixel by multiplying with contrast and adding brightness. Whenever the alteration changes a value that is outside of
 * the scope of RBG (0-255) then the value will either be set to 0 or 255.
 *
 * Instructions to run the application:
 *
 * - The image-files need to be in the same folder as this class.
 * - The modified image is saved to '/modifiedImages/' folder.
 * - Start a terminal and navigate to the '/src/' folder and type this to start the application:
 *      java bildTextU2PointOperations.PointOp imagename contrast brightness
 *  ex: java bildTextU2PointOperations.PointOp nature1.jpg 1.5 -40 //This will multiply each pixel's red, green, and blue
 *  values with 1.5 (contrast) and subtract by -40 (brightness).
 *
 */
public class PointOp {

    private float contrast, brightness;

    private static String imageName;

    private BufferedImage image;

    public PointOp(String[] args) {
        contrast = Float.parseFloat(args[1]);
        brightness = Float.parseFloat(args[2]);

        imageName = args[0];

        image =creteBufferedImage(imageName);

        changeContrastBrightness(image);
    }

    public PointOp() {

    }

    /**Iterates trough the BufferedImage passed in as argument and changes the image's every pixel's RGB-value by
     * multiplying it's colorchannels with 'contrast' and adding 'brightness'.
     *
     * This operation has O(n*m) time complexity since it iterates trough the whole 2d array of pixels.
     *
     *
     * @param image : BufferedImage
     */
    private void changeContrastBrightness(BufferedImage image) {

        System.out.println("Processing image...");

        int height = image.getHeight();
        int width = image.getWidth();
        Color c; // used to extract the value at each rgb channel.


        for(int u = 0; u<width; u++){
            for (int v = 0; v<height; v++){

                int color = image.getRGB(u,v);

                int red = ((color & 0xff0000) >> 16);
                int green =  (color & 0x00ff00) >> 8;
                int blue = (color & 0x0000ff);

                red = setValidRGBValue((int) (red*contrast+brightness));
                green = setValidRGBValue((int) (green*contrast+brightness));
                blue= setValidRGBValue((int) (blue*contrast+brightness));

                color =  ((red & 0xff)<<16) | ((green & 0xff)<<8) | blue & 0xff;

                image.setRGB(u,v, color);



            }
        }
        System.out.println("Done!");
        saveImage(image, imageName);
    }

    /**Check if i is within range 0 -255. If greater then sets i to 255 and if less then sets i to 0

     */
    private int setValidRGBValue(int i) {
        if(i>255){
            i=255;
        }
        if(i<0){
            i=0;
        }
        return i;
    }

    /**Creates and returns a BufferedImage-object. The String passed in as argument represents the image's name and the
     * image-file must be in the same directory as this class.
     *
     * @param imageName : String
     * @return imageToReturn : BufferedImage
     */
    protected BufferedImage creteBufferedImage(String imageName) {

        Image img = new ImageIcon(this.getClass().getResource(imageName)).getImage();
        BufferedImage imageToReturn = new BufferedImage(img.getWidth(null), img.getHeight(null),BufferedImage.TYPE_INT_RGB);


        Graphics g = imageToReturn.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return imageToReturn;
    }

    /**Save a image in the '/modifiedImages/' folder by passing in a BufferedImage and a String as arguments. The
     * String object represents which name the new Image should have.
     *
     * @param image : BufferedImage
     * @param imageName : String
     */
    protected static void saveImage(BufferedImage image, String imageName){
        File output = new File("bildTextU2PointOperations/modifiedImages/" +imageName);
        try {
            if(imageName.substring(imageName.indexOf('.')).equals(".jpg")){
                ImageIO.write(image,"jpg",output);
            }
            if(imageName.substring(imageName.indexOf('.')).equals(".png")){
                ImageIO.write(image,"png",output);
            }
            System.out.println("Image saved in directory:" +output.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        System.out.println("Image to modify: "+args[0]);
        System.out.println("Contrast: "+args[1]);
        System.out.println("Brightness: "+args[2]);

        new PointOp(args);

    }
}
