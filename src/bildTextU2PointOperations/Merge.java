package BildoTextu2PointOperations;

import java.awt.image.BufferedImage;

/**
 * Created by Patrik Lind, Johan Held on 2016-11-17.
 *
 * This class is used to blend two images and create a  third gray-scale image from the blended images.
 * Both the blend and convert to graycale functions are iterated within the same nestled loop hense the time complexity
 * for this algorithm is O(n*m) where n = image width and m = image height.
 *
 * Instructions to run the application:
 * - The image-files need to be in the same folder as this class.
 * - The modified image is saved to '/modifiedImages/' folder.
 * - All images passed in must be of the same size( equal width and equal height).
 *
 * - Start a terminal and navigate to the '/src/' folder and type this to start the application:
 *      java bildTextU2PointOperations.Merge house1.jpg space1.jpg template1.png
 */
public class Merge {

    private BufferedImage imageBG, imageFG, imageGrey;


    public Merge(String[] args) {

        PointOp pointOp = new PointOp();

        imageBG = pointOp.creteBufferedImage(args[0]);
        imageFG = pointOp.creteBufferedImage(args[1]);
        imageGrey = pointOp.creteBufferedImage(args[2]);

        if(imageBG.getWidth()!= imageFG.getWidth() || imageBG.getHeight() != imageFG.getHeight()){
            System.out.println("Image "+args[0]+" and "+args[1]+"have different size.\n Terminating application.");
        }

        mergeImages(imageBG, imageFG, imageGrey);
    }

    /**Creates a new image by merging the three BufferedImages passed in as argument. The third image is used to
     * extract the alpha value which is used when merging the images. The resulting image is saved to
     * '/modifiedImages' directory.
     *
     * This method does the merge by computing the new pixelvalue by: pixel = pixel1*alpha + pixel2*(1-alpha) for
     * each color-channel.
     *
     * Then the alpha value is computed by:
     *      L = ((redValue*redWeight +blueValue*blueWeight+greenValue*greenWeight)/3)/256
     * Where the value of the weights are:
     *      Red = 0.2125, Blue = 0.072, Green = 0.7154;
     *
     *
     * This algorithm has O(n * m) time complexity.
     *
     * @param image1 : BufferedImage
     * @param image2 : BufferedImage
     * @param imageGrey : BufferedImage
     */
    private void mergeImages(BufferedImage image1, BufferedImage image2, BufferedImage imageGrey) {

       BufferedImage mergedImage = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_RGB);

        int height = mergedImage.getHeight();
        int width = mergedImage.getWidth();
        double alpha;

        //Recommended weights according to ITU-BT.709
       double weightRed = 0.2125, weightBlue = 0.072, weightGreen = 0.7154;

        System.out.println("Processing image...");
        for(int u = 0; u<width; u++){
            for(int v = 0; v<height; v++){

                //Get the color-value from the grey image and isolate the channels.
                int greycolor = imageGrey.getRGB(u,v);
                int r0 = (greycolor& 0xff0000)>>16;
                int g0 = (greycolor& 0x00ff00)>>8;
                int b0 = (greycolor& 0x0000ff);

                //compute the luminance and divide by 256 to get the alpha-value.
                alpha = ((r0*weightRed)+(g0*weightGreen)+(b0*weightBlue) / 3)/256;

                //Get the color-value from image1 and isolate the channels.
                int color1 = image1.getRGB(u,v);
                int r1 = (color1 & 0xff0000)>>16;
                int g1 = (color1 & 0x00ff00)>>8;
                int b1 = (color1& 0x0000ff);

                //Get the color-value from image2 and isolate the channels.
                int color2 = image2.getRGB(u,v);
                int r2 = (color2 & 0xff0000)>>16;
                int g2 = (color2 & 0x00ff00)>>8;
                int b2 = (color2 & 0x0000ff);

                //perform the merge and by: color1 * alpha + color2 * (1-alpha)
                int r3 = (int) (r1*alpha+r2*(1.0-alpha));
                int g3 = (int) (g1*alpha+g2*(1.0-alpha));
                int b3 = (int) (b1*alpha+b2*(1.0-alpha));


                int newColor =  ((r3& 0xff)<<16) | ((g3& 0xff)<<8) | b3& 0xff;

                mergedImage.setRGB(u,v, newColor);

            }
        }


        System.out.println("Done!");
        PointOp.saveImage(mergedImage, "nyBild.jpg");
    }

    public static void main(String[] args) {

        System.out.println("Image 1: "+args[0]);
        System.out.println("Image 2: "+args[1]);
        System.out.println("Grey image: "+args[2]);
        new Merge(args);
    }

}
