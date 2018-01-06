/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.deep.tool;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ersin
 */
public class Resize {

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_DEFAULT);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static void main(String[] args) throws IOException {

        String[] dirs = new String[]{
            "agaclar", "agaclikmanzara", "bar", "bathroom", "calisma odasi", "camasir odasi", "decoration", "house", "kitchen", "library", "meeting room", "mutfak", "officeroom", "park", "restaurant", "room", "street", "toplanti odasi"
        };

        int i =0;
        for (String dir : dirs) {

            String sfile = "/work/ws/ResimUret/resimcesitli/" + dir;

            File root = new File(sfile);
            for (File child : root.listFiles()) {
                if (child.isFile() && child.getName().endsWith(".jpg")) {
                    BufferedImage source = ImageIO.read(child);
                    int nw = 1248;
                    int nh = 384;

                    BufferedImage target = resize(source, nw, nh);

                    ImageIO.write(target, "png", new File("/work/ws/ResimUret/resimarka/" +dir+"_"+i + ".png"));
                    i++;
                    //System.out.println(dir+"_"+i);
                }
            }
        }
        System.out.println("Bitti");
    }

}
