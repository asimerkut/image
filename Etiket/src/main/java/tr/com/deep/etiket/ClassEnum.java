/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.deep.etiket;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author home
 */
public enum ClassEnum {
       C1("Yürür",Color.BLUE),
       C2("Oturur",Color.RED),
       C3("Zıplar",Color.MAGENTA),
       C4("Yatar",Color.YELLOW),
       ;
       String label;
       Color color;
       ClassEnum(String label, Color color){  
          this.label = label;
          this.color = color;
       }
       
       public Color getColor(){
           return this.color;
       }
       public String getLabel(){
           return this.label;
       }
       
}
