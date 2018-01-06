/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.deep.tool;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import tr.com.deep.etiket.ClassRect;

/**
 *
 * @author home
 */
public class FileEditor {

    String targetdir = "c://work/target//";

    private String getTxtName(File file) {
        String labelName = file.getName().replace(".jpg", ".txt").replace(".png", ".txt");
        return targetdir + labelName;
    }

    private String getJpgName(File file) {
            String jpgName = file.getName().replace(".png", ".jpg");
        return targetdir + jpgName;
    }

    void saveLabel(File file, List<ClassRect> rectList) {
        try {
            PrintWriter pwTxt = new PrintWriter(getTxtName(file));
            for (ClassRect cr : rectList) {
                pwTxt.format(cr.getClassEnum()+ " " + cr.getX() + " " + cr.getY() + " " + (cr.getX() + cr.getWidth()) + " " + (cr.getY() + cr.getHeight()));
                pwTxt.println();
            }
            pwTxt.flush();
            pwTxt.close();

            BufferedImage source = ImageIO.read(file);
            ImageIO.write(source, "jpg", new File(getJpgName(file)));

        } catch (Exception ex) {

        }

    }

    List<ClassRect> readLabel(File file) {
        List<ClassRect> list = new ArrayList<>();

        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(getTxtName(file)))));
            String line = "";
            while ((line = fr.readLine()) != null) {
                System.out.print(line);
            }
        } catch (Exception ex) {

        }
        return list;
    }

    
}
