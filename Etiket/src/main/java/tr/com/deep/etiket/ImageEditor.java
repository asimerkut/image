/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.deep.etiket;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import tr.com.deep.etiket.actions.AbstractImageAction;

/**
 *
 * @author ersin
 */
public class ImageEditor extends JPanel {

    BufferedImage image = null;

    protected ClassEnum tempEnum;
    protected ClassRect tempRect = new ClassRect();

    protected AbstractImageAction action;

    public ImageEditor() {

       

    }

    public AbstractImageAction getAction() {
        return action;
    }

    public ClassEnum getTempEnum() {
        return tempEnum;
    }

    public void setTempEnum(ClassEnum tempEnum) {
        this.tempEnum = tempEnum;
    }

    public synchronized ClassRect getTempRect() {
        return tempRect;
    }

    public void setTempRect(ClassRect tempRect) {
        this.tempRect = tempRect;
    }

    public void setAction(AbstractImageAction action) {

        AbstractImageAction old = this.action;
        this.action = action;

        this.removeMouseListener(old);

        this.removeMouseMotionListener(old);

        this.removeKeyListener(old);

        if (this.action != null) {
            this.addMouseListener(action);
            this.addMouseMotionListener(action);
            this.addKeyListener(action);
        }
    }

    ImageMeta imageMeta = new ImageMeta();

    public ImageMeta getImageMeta() {
        return imageMeta;
    }

    public void setImageMeta(ImageMeta imageMeta) {
        this.imageMeta = imageMeta;

        getAction().contentChanged(getImageMeta());
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;

    }

    public void paint(java.awt.Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        super.paint(g2);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (image != null) {

            g2.drawImage(image, 0, 0, null);

        }

        for (ClassRect r : imageMeta.getRectList()) {
            g2.setStroke(new BasicStroke(4));
            g2.setColor(r.getColor());
            g2.draw(r);

            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString(r.getClassEnum().name(), (int) r.getCenterX(), (int) r.getCenterY());

        }

        getAction().paint(g);

        g2.setColor(Color.BLACK);

        g2.dispose();

    }

}
