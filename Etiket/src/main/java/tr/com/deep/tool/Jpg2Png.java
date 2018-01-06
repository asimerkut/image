/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.deep.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Jpg2Png {

    public static void main(String[] args) throws IOException {
            int sirano = 300;
            String sfile = "/work/png/";
            File root = new File(sfile);
            for (File child : root.listFiles()) {
                if (child.isFile() && child.getName().endsWith(".png")) {
                    BufferedImage source = ImageIO.read(child);
                    
                    //BufferedImage target = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
                    //target.createGraphics().drawImage(source, 0, 0, Color.WHITE, null);
          
                    ImageIO.write(source, "png", new File(sfile+"otur_"+(++sirano)+".png"));
                    System.out.println(sirano+" "+child.getName());
                }
            }            
    }
}
