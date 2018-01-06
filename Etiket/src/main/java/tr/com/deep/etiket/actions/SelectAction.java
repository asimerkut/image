/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.deep.etiket.actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import tr.com.deep.etiket.ClassEnum;
import tr.com.deep.etiket.ClassRect;
import tr.com.deep.etiket.ImageEditor;
import tr.com.deep.etiket.SinifSecim;

/**
 *
 * @author ersin
 *
 * Sınıf İşaretçisi
 */
public class SelectAction extends AbstractImageAction {

    public SelectAction(ImageEditor imageEditor) {
        super(imageEditor);
        init(imageEditor);
    }

    private void init(ImageEditor imageEditor) {
        ActionMap actMap = imageEditor.getActionMap();
        actMap.clear();
        
    
        
    }
    protected Stroke dashed = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3, 7, 10, 7}, 1);

    @Override
    public void mousePressed(MouseEvent e) {
        pressedPosition = e.getPoint();

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {

                boolean v = false;
                while (pressedPosition != null) {
                    if (v) {
                        dashed = new BasicStroke(6, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3, 7, 10, 7}, 1);

                    } else {
                        dashed = new BasicStroke(6, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10, 7, 3, 7}, 0);
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ImageEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    v = !v;

                    imageEditor.repaint();
                }

            }
        });

        t.start();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        releasedPosition = e.getPoint();

        ClassRect r = createRect(releasedPosition);

        SinifSecim sinifSecim = new SinifSecim();

        sinifSecim.setAl(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClassEnum selClass = ClassRect.getClassMap().get(e.getActionCommand());
                imageEditor.setTempEnum(selClass);
            }
        });

        sinifSecim.setSecimler(ClassRect.getClassMap().values());
        int secim = JOptionPane.showConfirmDialog(imageEditor, sinifSecim);

        if (secim == JOptionPane.OK_OPTION) {

            r.setClassEnum(imageEditor.getTempEnum());
            imageEditor.getImageMeta().getRectList().add(r);
        }

        imageEditor.setTempRect(null);
        pressedPosition = null;

        imageEditor.repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (pressedPosition != null) {

            imageEditor.setTempRect(createRect(e.getPoint()));
            imageEditor.repaint();

        }
    }

    private ClassRect createRect(Point rp) {
        Dimension d = new Dimension();
        d.width = Math.abs(pressedPosition.x - rp.x);
        d.height = Math.abs(pressedPosition.y - rp.y);
        Integer x = pressedPosition.x < rp.x ? pressedPosition.x : rp.x;
        Integer y = pressedPosition.y < rp.y ? pressedPosition.y : rp.y;
        Point p = new Point(x, y);
        ClassRect r = new ClassRect(p, d);
        return r;
    }

    public void paint(java.awt.Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        if (imageEditor.getTempRect() != null) {

            g2.setXORMode(Color.WHITE);

            g2.setStroke(dashed);
            g2.setColor(imageEditor.getTempRect().getColor());
            g2.draw(imageEditor.getTempRect());
        }
    }
;

}
