
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

public class CharSel {
    char ch[]={'\u1b83','\u1b84','\u1b85','\u1b86','\u1b87','\u1b88','\u1b89','\u1b8a','\u1b8b','\u1b8c',
               '\u1b8d','\u1b8e','\u1b8f','\u1b90','\u1b91','\u1b92','\u1b93','\u1b94','\u1b95','\u1b96',
               '\u1b97','\u1b98','\u1b99','\u1b9a','\u1b9b','\u1b9c','\u1b9d','\u1b9e','\u1b9f','\u1ba0',
               '\u1bae','\u1baf','\u1bb0','\u1bb1','\u1bb2','\u1bb3','\u1bb4','\u1bb5','\u1bb6','\u1bb7',
               '\u1bb8','\u1bb9'};
    
    int chStart = 0, visibles;
    int chSelected=0;
    
    int stage[] = new int[10];
    int iStage=0;
    
    TulisinEx tulis = new TulisinEx();
    
    public CharSel(){
        tulis.setColor(0xD2D2D2);
    }
    
    public int getChar(){
        return ch[chStart+chSelected];
    }
    
    public void keyPressed(int keyCode) {

        if (keyCode==Canvas.LEFT | keyCode==-3){
            if (chSelected > 0) chSelected--;
            else if (chSelected==0 & chStart>0 ){
                chSelected=stage[iStage-1]-1;
                chStart-= stage[iStage-1];
                
                iStage--;
            }
        }
        
        if (keyCode==Canvas.RIGHT | keyCode==-4){
            if (chSelected<visibles-1) chSelected++;
            else if (chSelected>=visibles-1 & ch.length-chStart-1>visibles ) {
                chSelected=0;
                chStart+=visibles;
                
                stage[iStage]=visibles;
                
                iStage++;
            }
            
        }

        
    } 
    

    public void render(Graphics g, int y, int w, int h){
        
        int xTri = 4;
        int yTri = y+(h/2);
        
        g.setColor(0x00093F);
        g.fillRect(0, y, w, h);
        
        g.setColor(0xffffff);
        g.fillTriangle(xTri, yTri, xTri+4, yTri-4, xTri+4, yTri+4); // kiri
        g.fillTriangle(w-xTri, yTri, w-xTri-4, yTri-4, w-xTri-4, yTri+4);    // kanan
        
        int xChar = 15;
        int hChar = tulis.getHeight();
        int i = 0;
        
        g.setColor(0x000000);
        g.fillRect(0, y+h, w, h+8);
        
        for (int n=chStart; n<ch.length; n++){
            
            int wChar = tulis.charWidth(ch[n]);
            if (xChar+wChar < w-15){
               
                if (i==chSelected)
                    g.fillRect(xChar-3, y, wChar+6, h);
                
                
                tulis.drawChar(g, ch[n], xChar, y+((h-hChar)/2));

                xChar+=wChar+6;
                i++;
                
            } else
                break;
            
        }
        
        visibles =i;

    }
    
}
