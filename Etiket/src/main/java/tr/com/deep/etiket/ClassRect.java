

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.deep.etiket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap; 
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author ersin
 */
public class ClassRect extends Rectangle {
    
    ClassEnum classEnum;
    ClassRect ownerRect;
    int index;
    
    public ClassRect() {
        super(new Point(0, 0),new Dimension(0, 0));
    }

    public ClassRect(Point p, Dimension d) {
        super(p, d);    
    }
    
    public ClassRect(int index){
        this.index = index; 
        setSize(12, 12);
    }

    public Color getColor() {
        if (classEnum==null){
            return Color.ORANGE;
        }
        return classEnum.getColor();
    }    
    
    public ClassEnum getClassEnum() {
        return classEnum;
    }

    public void setClassEnum(ClassEnum classEnum) {
        this.classEnum = classEnum;
    }

    public int getIndex() {
        return index;
    }
    
     public ClassRect getOwnerRect() {
        return ownerRect;
    }

    public void setOwnerRect(ClassRect ownerRect) {
        this.ownerRect = ownerRect;
    }
    
    
    public static Map<String, ClassEnum> getClassMap(){
        Map<String, ClassEnum> classMap = new LinkedHashMap<>();
         for (ClassEnum ce : ClassEnum.values()){
            classMap.put(ce.getLabel(), ce);
        } 
        return classMap;
    }
 

    @Override
    public String toString() {
        if (classEnum==null){
            return "ClassRect:null";
        }
        return "ClassRect{" + "sinif=" + classEnum.name()+":"+classEnum.getLabel()+" X:"+getX()+" Y:"+getY()+" Width:"+getWidth()+" Height:"+getHeight()+"}";
    }
    
}


/*
public class ClassRect extends Rectangle {
    
   
    String sinif;
    
    Color color = Color.ORANGE;
    
    ClassRect owner;
    int index;

    public ClassRect() {
        this(new Point(0, 0),new Dimension(0, 0));
    }

    public ClassRect(int index){
        this.index = index; 
        setSize(12, 12);
    }
    
    public ClassRect getOwner() {
        return owner;
    }

    public void setOwner(ClassRect owner) {
        this.owner = owner;
    }
    
    public int getIndex() {
        return index;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    
    public String getSinif() {
        return sinif;
    }

    public void setSinif(String sinif) {
        this.sinif = sinif;
    }
    

    public static HashMap<String, Color> colorMap = new HashMap<>();
 

    public ClassRect(Point p, Dimension d) {
        super(p, d);
        
        
    }

      static   {
        colorMap.put("sinif1", Color.BLUE);
        colorMap.put("sinif2", Color.red);
        colorMap.put("sinif3", Color.MAGENTA);
        colorMap.put("sinif4", Color.YELLOW);
    }

    @Override
    public String toString() {
        return "ClassRect{" + "sinif=" + sinif + ", color=" + color + '}';
    }  
}

*/
