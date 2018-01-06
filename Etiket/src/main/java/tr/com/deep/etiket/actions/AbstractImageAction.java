/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.deep.etiket.actions;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import tr.com.deep.etiket.ImageEditor;
import tr.com.deep.etiket.ImageMeta;

/**
 *
 * @author ersin
 */
public abstract class AbstractImageAction extends MouseAdapter implements KeyListener{

    protected ImageEditor imageEditor = null;

    protected Point pressedPosition;
    protected Point releasedPosition;

    public AbstractImageAction(ImageEditor imageEditor) {
        this.imageEditor = imageEditor;
    }
    
    public void paint(Graphics g){};

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
          System.out.println("keyReleased");
    }

    public void contentChanged(ImageMeta imageMeta) {
        
    }

}
