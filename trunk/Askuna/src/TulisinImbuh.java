
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

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
    
    public int drawChar(Graphics g, char c, int x, int y){
        
        int w, i=0;
        
        switch (c){
            case '\u1b80': i=0; break;
            case '\u1b81': i=1; break;
            case '\u1b82': i=2; break;
            case '\u1ba1': i=3; break;
            case '\u1ba2': i=4; break;
            case '\u1ba3': i=5; break;
            case '\u1ba4': i=6; break;
            case '\u1ba5': i=7; break;
            case '\u1ba6': i=8; break; 
            case '\u1ba7': i=9; break;
            case '\u1ba8': i=10; break;
            case '\u1ba9': i=11; break;
            case '\u1baa': i=12; break;
        }
        
        w = idxFont[i+1]-idxFont[i];
        
        g.drawRegion(imgFont, idxFont[i], 1, w, imgFont.getHeight()-1, Sprite.TRANS_NONE, x, y, g.LEFT | g.TOP);
        
        return w;
    }
    
    public int charWidth(int c){
        
        int i=0;
        switch (c){
            case '\u1b80': i=0; break;
            case '\u1b81': i=1; break;
            case '\u1b82': i=2; break;
            case '\u1ba1': i=3; break;
            case '\u1ba2': i=4; break;
            case '\u1ba3': i=5; break;
            case '\u1ba4': i=6; break;
            case '\u1ba5': i=7; break;
            case '\u1ba6': i=8; break; 
            case '\u1ba7': i=9; break;
            case '\u1ba8': i=10; break;
            case '\u1ba9': i=11; break;
            case '\u1baa': i=12; break;
        }
        
        return (idxFont[i+1]-idxFont[i]);
    }
    
}
