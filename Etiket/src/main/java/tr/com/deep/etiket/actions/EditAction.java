/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.deep.etiket.actions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import tr.com.deep.etiket.ClassRect;
import tr.com.deep.etiket.ImageEditor;
import tr.com.deep.etiket.ImageMeta;

/**
 *
 * @author ersin
 *
 * Boyutlandırma ve Taşıma
 */
public class EditAction extends AbstractImageAction {

    ClassRect[] resizeArray = {new ClassRect(0), new ClassRect(1), new ClassRect(2), new ClassRect(3)};//tl,tr,bl,br
    ClassRect resizer;
    
    ClassRect selected = null;
    Point relative = null;

    public EditAction(ImageEditor imageEditor) {
        super(imageEditor);
        initActionMap(imageEditor);
    }

    private void initActionMap(ImageEditor imageEditor) {

        ActionMap actMap = imageEditor.getActionMap();
        actMap.clear();

        InputMap inMap = imageEditor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        KeyStroke delete = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false);

        inMap.put(delete, getClass().getSimpleName() + "_DELETE");
        actMap.put(getClass().getSimpleName() + "_DELETE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected != null) {
                    imageEditor.getImageMeta().getRectList().remove(selected);
                    selected = null;
                    imageEditor.repaint();
                }
            }
        });

        KeyStroke bringToFront = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.SHIFT_DOWN_MASK, false);

        inMap.put(bringToFront, getClass().getSimpleName() + "_BRING_TO_FRONT");
        actMap.put(getClass().getSimpleName() + "_BRING_TO_FRONT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected != null) {
                    imageEditor.getImageMeta().getRectList().remove(selected);
                    imageEditor.getImageMeta().getRectList().add(selected);

                    imageEditor.repaint();
                }
            }
        });

        KeyStroke sendToBack = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.SHIFT_DOWN_MASK, false);

        inMap.put(sendToBack, getClass().getSimpleName() + "_SEND_TO_BACK");
        actMap.put(getClass().getSimpleName() + "_SEND_TO_BACK", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected != null) {
                    imageEditor.getImageMeta().getRectList().remove(selected);
                    imageEditor.getImageMeta().getRectList().add(0, selected);

                    imageEditor.repaint();
                }
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        if (selected != null) {

            Color before = g.getColor();
            g.setColor(Color.GRAY);
            ((Graphics2D) g).draw(selected);

            g.setColor(Color.GRAY);
            for (ClassRect resize : resizeArray) {
                ((Graphics2D) g).fill(resize);
            }
            g.setColor(before);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent e) {
        bodyPressed(e);
        resizer = null;

        for (ClassRect resize : resizeArray) {
            if (resize.contains(e.getPoint())) {
                resizer = resize;
                selected = (ClassRect) resizer.getOwnerRect();
            }
        }
        imageEditor.repaint();

    }

    private void resizerPaint(ClassRect selected) {
        for (ClassRect resize : resizeArray) {
            switch (resize.getIndex()) {
                case 0: {//tl
                    resize.setLocation((int) selected.getX() - 10, (int) selected.getY() - 10);
                    break;
                }
                case 1: {//tr
                    resize.setLocation((int) selected.getMaxX() + 0, (int) selected.getY() - 10);
                    break;
                }
                case 2: {//bl
                    resize.setLocation((int) selected.getX() - 10, (int) selected.getMaxY() + 0);
                    break;
                }
                case 3: {//br
                    resize.setLocation((int) selected.getMaxX() + 0, (int) selected.getMaxY() + 0);
                    break;
                }
            }
            resize.setOwnerRect(selected);
        }
    }

    private void bodyPressed(MouseEvent e) {
        selected = null;

        int size = imageEditor.getImageMeta().getRectList().size();
        for (int i = size - 1; i >= 0; i--) {

            ClassRect cr = imageEditor.getImageMeta().getRectList().get(i);

            if (cr.contains(e.getPoint()) && isNotContainsResizers(e)) {
                selected = cr;

                relative = new Point((int) (e.getX() - selected.getMinX()), (int) (e.getY() - selected.getMinY()));

                resizerPaint(selected);

                imageEditor.getImageMeta().getRectList().remove(selected);
                imageEditor.getImageMeta().getRectList().add(selected);

                imageEditor.repaint();
                break;
            }

        }
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.6
     */
    @Override
    public void mouseDragged(MouseEvent e) {//resize

        if (relative == null) {
            return;
        }

        if (resizer != null) {
            resizer.setLocation((int) e.getPoint().x, (int) e.getPoint().y);
            int minSize = 10;

            int newWidth = new Double(selected.getWidth()).intValue();
            int newHeight = new Double(selected.getHeight()).intValue();

            int newX = new Double(selected.getX()).intValue();
            int newY = new Double(selected.getY()).intValue();

            switch (resizer.getIndex()) {
                case 0: {//tl
                    if ((newWidth - (e.getPoint().x - selected.getX())) > minSize) {
                        newWidth -= e.getPoint().x - newX;
                        newX = e.getPoint().x;
                    }
                    if ((newHeight - (e.getPoint().y - selected.getY())) > minSize) {
                        newHeight -= e.getPoint().y - newY;
                        newY = e.getPoint().y;
                    }
                    break;
                }
                case 1: {//tr
                    if (e.getPoint().x > (selected.x + minSize)) {
                        newWidth = e.getPoint().x - selected.x;
                    }
                    if ((newHeight - (e.getPoint().y - selected.getY())) > minSize) {
                        newHeight -= e.getPoint().y - newY;
                        newY = e.getPoint().y;
                    }
                    break;
                }
                case 2: {//bl
                    if ((newWidth - (e.getPoint().x - selected.getX())) > minSize) {
                        newWidth -= e.getPoint().x - newX;
                        newX = e.getPoint().x;
                    }
                    if (e.getPoint().y > (selected.y + minSize)) {
                        newHeight = e.getPoint().y - selected.y;
                    }
                    break;
                }
                case 3: {//br
                    if (e.getPoint().x > (selected.x + minSize)) {
                        newWidth = e.getPoint().x - selected.x;
                    }

                    if (e.getPoint().y > (selected.y + minSize)) {
                        newHeight = e.getPoint().y - selected.y;
                    }
                    break;
                }
            }

            ((ClassRect) resizer.getOwnerRect()).x = newX;
            ((ClassRect) resizer.getOwnerRect()).y = newY;
            ((ClassRect) resizer.getOwnerRect()).setSize(newWidth, newHeight);

            resizerPaint(resizer.getOwnerRect());
        } else {
            bodyDragged(e);
        }
        imageEditor.repaint();
    }

    private void bodyDragged(MouseEvent e) {
        Point newTop = new Point((int) (e.getX() - relative.getX()), (int) (e.getY() - relative.getY()));
        if (selected != null) {
            selected.x = newTop.x;
            selected.y = newTop.y;
            resizerPaint(selected);
        }
    }

    @Override
    public void contentChanged(ImageMeta imageMeta) {
        this.selected = null;
    }

    private boolean isNotContainsResizers(MouseEvent e) {
        for (ClassRect resize : resizeArray) {
            if (resize.contains(e.getPoint())) {
                return false;
            }
        }
        return true;
    }

}
