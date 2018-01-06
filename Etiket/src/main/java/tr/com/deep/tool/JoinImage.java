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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author ersin
 */
public class JoinImage {

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_DEFAULT);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static BufferedImage join(BufferedImage background, BufferedImage face, int newX, int newY) {

        int width = background.getWidth();
        int height = background.getHeight();

        BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(background, 0, 0, null);
        g2d.drawImage(face, newX, newY, null);
        g2d.dispose();

        return dimg;
    }

    public static void main(String[] args) throws IOException {

        Random rnd = new Random();

        String[] facedir = new String[]{
            "f", "m"
        };

        int i = 0;
        String targetd = null;

        String backgroudDir = "/work/ws/ResimUret/resimarka";
        String targetvalid = "/work/ws/ResimUret/resimvalid/";
        String targettest = "/work/ws/ResimUret/resimtest/";
        String targetdir = "/work/ws/ResimUret/resimhedef/";
        File backDir = new File(backgroudDir);
        //PrintWriter pwAll = new PrintWriter("/work/ws/ResimUret/metadata.csv");

        for (int j = 0; j < 5; j++) {
            for (String dirf : facedir) {
                String facefolder = "/work/ws/ResimUret/resimfm/" + dirf;


                File root = new File(facefolder);
                for (File child : root.listFiles()) {
                    if (child.isFile() && child.getName().endsWith(".jpg")) {

                        try {

                            File[] backFiles = backDir.listFiles();
                            File backFile = backFiles[rnd.nextInt(backFiles.length)];
                            BufferedImage backImage = ImageIO.read(backFile);
                            BufferedImage faceImg = ImageIO.read(child);

                            double dvalue = rnd.nextDouble();
                            double scale = dvalue <0.5? 0.5: dvalue ;

                            int nw = (int) (120 * scale);
                            int nh = (int) (160 * scale);

                            int x = rnd.nextInt(1248 - nw);
                            int y = rnd.nextInt(384 - nh);
                            BufferedImage resizedFace = resize(faceImg, nw, nh);
                            BufferedImage target = join(backImage, resizedFace, x, y);

                            if (i%5==0){
                                targetd=targetvalid;
                            } else if (i%11==0){
                                targetd=targettest;
                            } else {
                                targetd=targetdir;
                            }
                            ImageIO.write(target, "jpg", new File(targetd + dirf + "_" + i + ".jpg"));

                            //pwAll.format("%d,%s,%d,%d,%d,%d,%s,%s,%s", i, dir, x, y, (x + nw), (y + nh), backFile.getName(), child.getName(), (dir + "_" + i));
                            //pwAll.println();
                            
                            //if(i%100==0) pwAll.flush();

                            PrintWriter pwTxt = new PrintWriter(targetd + dirf + "_" + i + ".txt");
                            pwTxt.format("%s 0.0 0 0.0 %d.00 %d.00 %d.00 %d.00 0.0 0.0 0.0 0.0 0.0 0.0 0.0", dirf, (x), (y), (x + nw), (y + nh));
                            pwTxt.println();
                            pwTxt.flush();
                            pwTxt.close();

                            //if (i>50) {
                                //pwAll.flush();
                            //    break;
                            //}
                            i++;
                        } catch(NullPointerException e) {
                        }

                    }
                }
            }
        }

        //pwAll.close();

    }
}
