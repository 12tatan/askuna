
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sofyan
 */
public class TulisinImbuh {
    private Image imgFont=null;
    private int idxFont[];
    
    public TulisinImbuh(){

        init("/font/imbuh.png");
    
    }
    
    private void init(String s){
        Vector vt = new Vector();
        
        imgFont = getImage(s);
        int rgb[] = new int[imgFont.getWidth()*imgFont.getHeight()];
        
        imgFont.getRGB(rgb, 0, imgFont.getWidth(), 0, 0, imgFont.getWidth(), imgFont.getHeight());
        
        // index char
        for (int i=0; i<rgb.length; i++){
            if (rgb[i]==0xff0000ff)
                vt.addElement(Integer.toString(i+1));
            else if (rgb[i]==0xffff0000){
                vt.addElement(Integer.toString(i+1));
                break;
            }
        }

        idxFont = new int[vt.size()];
        for(int i=0; i<vt.size(); i++)
            idxFont[i] = Integer.parseInt((String)vt.elementAt(i));
        
    }
    
    // Nyandak gambar
    private static Image getImage(String s){
        Image img = null;
        
        try{
            img = Image.createImage(s);
        } catch (Exception e) { }
        
        return img;
    }
    
    public int drawChar(Graphics g, int i, int x, int y){
        
        int w;
        
        w = idxFont[i+1]-idxFont[i];
        
        g.drawRegion(imgFont, idxFont[i], 1, w, imgFont.getHeight()-1, Sprite.TRANS_NONE, x, y, g.LEFT | g.TOP);
        
        return w;
    }
    
    public int charWidth(int i){
        return (idxFont[i+1]-idxFont[i]);
    }
    
}
