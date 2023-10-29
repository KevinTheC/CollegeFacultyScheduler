package Q1;
import java.io.File;

import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class script {
	public static void main(String[] args) throws Exception{
		      File file = new File("C:\\Users\\toonl\\git\\CollegeFacultyScheduler\\Project\\src\\Q1\\bg.png");
		      BufferedImage img = ImageIO.read(file);
		      for (int y = 0; y < img.getHeight(); y++) {
		         for (int x = 0; x < img.getWidth(); x++) {
		            //Retrieving contents of a pixel
		            int pixel = img.getRGB(x,y);
		            //Creating a Color object from pixel value
		            Color color = new Color(pixel, true);
		            //Retrieving the R G B values
		            //Modifying the RGB values
		            double total = color.getRed()+(color.getGreen()*.908)+color.getBlue()*.622;
		            int val = (int)total/3;
		            val = roof(val+(val%2));
		            color = new Color(val,(int)(val*.908),(int)(val*.622));
		            img.setRGB(x, y, color.getRGB());
		         }
		      }
		      //Saving the modified image
		      file = new File("C:\\Users\\toonl\\git\\CollegeFacultyScheduler\\Project\\src\\Q1\\withgoodbehavior.jpg");
		      ImageIO.write(img, "jpg", file);
		      System.out.println("Done...");
		   }
	public static int m(int i, double d) {
		i = (int)(i*d);
		return i+100;
	}
	public static int roof(int i) {
		if (i>255)
			return 254;
		return i;
	}
}
