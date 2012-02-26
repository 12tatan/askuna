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

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;


public class Askuna extends MIDlet {
    public Display layar;
            
    public Askuna(){
        layar = Display.getDisplay(this);
    }
    
    public void startApp() {
        
        int pixel = 0xEAFF4C;
        int opacity = 0x55121212;
        //int c = 0xFFFFFF;
        
        //int r = ((pixel & 0x00ff0000) >> 16);
        //int g = ((pixel & 0x0000ff00) >> 8);
        //int b = ((pixel & 0x000000ff) >> 0);
        
        
        
        int rgb = (pixel & 0x00ffffff);
        
        int baru = (opacity & 0xff000000) + rgb; //+ (r << 16) + (g << 8) + b;
        
        System.out.println(Integer.toHexString( baru));
                

        Golodog golodog = new Golodog(this);
        layar.setCurrent(golodog.lsMenu);
        
    }
    
    public void pauseApp() {
        
    }
    
    public void destroyApp(boolean unconditional) {
    
    }
    
    public void exitMIDlet(){
        destroyApp(true);
        notifyDestroyed();
    }
}
