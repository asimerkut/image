package tr.com.deep.etiket;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImageMeta {

    String fileName;

    transient BufferedImage miniImage;

    Dimension dimension;

    List<ClassRect> rectList = new ArrayList<>();

    public List<ClassRect> getRectList() {
        return rectList;
    }

    public void setRectList(List<ClassRect> rectList) {
        this.rectList = rectList;
    }

    public ImageMeta() {
    }

    public ImageMeta(String fileName, BufferedImage miniImage, Dimension dimension) {
        this.fileName = fileName;
        this.miniImage = miniImage;
        this.dimension = dimension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BufferedImage getMiniImage() {
        return miniImage;
    }

    public void setMiniImage(BufferedImage miniImage) {
        this.miniImage = miniImage;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

}
