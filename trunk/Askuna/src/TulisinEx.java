/* Aksara Sunda J2ME
 * Copyright (C) 2011 A. Sofyan Wahyudin
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;


public class TulisinEx {
    
    private Font font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
    private Image imgFont=null;
    private int idxFont[], cl;
    
    public TulisinEx(){

        init("/font/sundauni.png");
    
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
    
    public void setColor(int cl){
        this.cl = cl;
        
        int rgb[] = new int[imgFont.getWidth()*imgFont.getHeight()];  

        imgFont.getRGB(rgb, 0, imgFont.getWidth(), 0, 0, imgFont.getWidth(), imgFont.getHeight());  
        
        for (int i=idxFont[idxFont.length-1]; i<rgb.length; i++)
            if (rgb[i]==0xff000000) rgb[i] += cl;
        
        imgFont = Image.createRGBImage(rgb, imgFont.getWidth(), imgFont.getHeight(), true);
        
        rgb = null;
    }

    public int stringWidth(String s){
        char ch[] = s.toCharArray();
        int w=0;
        
        for (int i=0; i<ch.length; i++)
            w += charWidth(ch[i]);
        
        return w;
    }
    
    public int charWidth(char c){
        int n = c - 0x1b80;
        
        if (c==0x20)    // spasi
            return 3;
        //else if (c <= 0x1b83 | c >= 0x1ba0) // luar
        //    return font.charWidth(c);
        else
            return (idxFont[n+1]-idxFont[n])-1;
    }
    
    
    public void drawString(Graphics g, String s, int x, int y){

        int lastx=x, lastw=0, hImg=12, w;
        
        char ch[] = s.toCharArray();
        char c, cNext;
        
        
        g.setFont(font);
        g.setColor(cl);
        
        for (int i = 0; i<ch.length; i++){
            c = ch[i];
            
            cNext = (ch.length>i+1) ? ch[i+1] : 0;
            
            System.out.println("CHR " + i + " : " +  Integer.toHexString(c));
            
            if ((c >= 0x1b83 & c <= 0x1ba0) | c == 0x1bae | c == 0x1baf |   // char hurup + kha, sya
                (c >= 0x1bb0 & c <= 0x1bb9) | c == 0x7c) {                  // char angka + vertical line
                
                // pake panélég teu
                if (cNext == 0x1ba6){
                    
                    w = drawChar(g, '\u1ba6', lastx, y+6);    // panélég
                    lastx += w + 1;
                    
                    w = drawChar(g, c, lastx, y);
                    
                    lastx += w + 2;
                    lastw = w;
                    
                    i+=1;   // luncat
                    
                } else {
                
                    w = drawChar(g, c, lastx, y);
                    
                    lastx += w + 2;
                    lastw = w;
                }
                
            }
            else if (c == 0x1ba1) { // pamingkal

                w = drawChar(g, c, lastx-15, y+6);
                
                lastx += 6;
                lastw = w;
                
            }
            else if (isLuhur(c)) { // rarangkén luhur
                
                w = charWidth(c);
                
                if (isLuhur(cNext)){    // double rarangkén
                    
                    int w2 = charWidth(cNext);
                    int x2 = lastx - (lastw/2) - ((w+w2+1))/2 - 3;   // +1 = spasi
                    
                    drawChar(g, c, x2, y-5);
                    drawChar(g, cNext, x2+w+1, y-5);
                    
                    i+=1;   // luncat

                } else
                    drawChar(g, c, lastx-((lastw/2)+(w/2))-2, y-5);
                
                
            }
            else if (c == 0x1ba2 | c == 0x1ba5) { // rarangkén handap

                w = charWidth(c);
                
                drawChar(g, c, lastx-((lastw/2)+(w/2))-3, y+hImg+1);
                
            }
            else if (c == 0x1b82 | c == 0x1ba7 | c == 0x1baa) {   // rarangkén di katuhu

                w = charWidth(c);
                System.out.println("-- "+w);
                drawChar(g, c, lastx-1, y+6);
                
                lastx += w + 1;
                lastw = w;
            }
            else if (c == 0x20) {   // spasi
                
                lastx += 3; System.out.println("---------------");
                
            }
            else {
                g.drawString(String.valueOf(c), lastx + 2, y+hImg, g.LEFT | g.BASELINE);
                
                lastx += font.charWidth(c)+4;
                lastw = font.charWidth(c)+4;  
            }
            
        }
        
    } 
    
    // Rarangkén Luhur
    private static boolean isLuhur(char c){
        if (c == 0x1ba4 | c == 0x1ba8 | c == 0x1ba9 | c == 0x1b80 |c == 0x1b81)
            return true;
        else
            return false;
    }
    
    // Nyandak gambar
    private static Image getImage(String s){
        Image img = null;
        
        try{
            img = Image.createImage(s);
        } catch (Exception e) { }
        
        return img;
    }
    
    public int drawChar(Graphics g, int c, int x, int y){
        
        int n, w;
        
        if (c==0x7c)    // pembatas angka
            n = idxFont.length-2;
        else
            n = c - 0x1b80;
        
        w = idxFont[n+1]-idxFont[n]-1;
        
        g.drawRegion(imgFont, idxFont[n], 1, w, 12, Sprite.TRANS_NONE, x, y, g.LEFT | g.TOP);
        
        return w;
    }
}
