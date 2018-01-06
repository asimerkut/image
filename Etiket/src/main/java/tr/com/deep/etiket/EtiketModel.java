package tr.com.deep.etiket;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class EtiketModel {

    File folder = null;

    ImageMeta[] imageMetaList = null;

    Dimension dimension = new Dimension(255, 255);
    private ActionListener actionListener;

    public ImageMeta[] loadFromDirectory() {

        if (folder == null) {
            throw new IllegalArgumentException("dizin null olamaz!");
        }

        List<ImageMeta> array = new ArrayList<>();

        if (imageMetaList == null) {
            
            double count = folder.list().length;
            int i =0;
            for (File f : folder.listFiles()) {
                if(f.isDirectory()){
                    continue;
                }
                try {
                    array.add(createImageMeta(f));
                    actionListener.actionPerformed(new ActionEvent(this, 0, String.valueOf(i++/count * 100)));
                } catch (IOException ex) {
                    Logger.getLogger(EtiketModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            imageMetaList = array.toArray(new ImageMeta[0]);
        }

        return imageMetaList;

    }

    public EtiketModel() {
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {

        this.folder = folder;
        imageMetaList = null;
    }

    public ImageMeta createImageMeta(File f) throws IOException {

        BufferedImage miniImage = resize(ImageIO.read(f),(int)dimension.getWidth(),(int)dimension.getHeight());

        ImageMeta result = new ImageMeta(f.getPath(), miniImage, dimension);

        return result;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_DEFAULT);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    void setActionListener(ActionListener actionListener) {
      this.actionListener = actionListener;
    }

    public ImageMeta[] getImageMetaList() {
        return imageMetaList;
    }

    public void setImageMetaList(ImageMeta[] imageMetaList) { 
        this.imageMetaList = imageMetaList;
    }
    
    

}
