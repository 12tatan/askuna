
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

public class CharSel {
    char ch[]={'\u1b83','\u1b84','\u1b85','\u1b86','\u1b87','\u1b88','\u1b89','\u1b8a','\u1b8b','\u1b8c',
               '\u1b8d','\u1b8e','\u1b8f','\u1b90','\u1b91','\u1b92','\u1b93','\u1b94','\u1b95','\u1b96',
               '\u1b97','\u1b98','\u1b99','\u1b9a','\u1b9b','\u1b9c','\u1b9d','\u1b9e','\u1b9f','\u1ba0',
               '\u1bae','\u1baf','\u1bb0','\u1bb1','\u1bb2','\u1bb3','\u1bb4','\u1bb5','\u1bb6','\u1bb7',
               '\u1bb8','\u1bb9'};
    
    char im[]={'\u1b80', '\u1b81', '\u1b82', '\u1ba1', '\u1ba2', '\u1ba3', '\u1ba4', '\u1ba5', '\u1ba6', 
               '\u1ba7', '\u1ba8', '\u1ba9', '\u1baa'};
    
    int chStart = 0, visibles1, visibles2;
    int Selected=0, chActive=-1, imActive=2 ;
    int selectedx=0;
    
    int im1=-1, im2=-1, im3=-1;
    
    String imStr="";
    
    int chStage[] = new int[10];
    int ichStage=0;
    
    int imStart = 0;
    int imStage[] = new int[10];
    int iimStage=0;
    
    int level=1;
    
    TulisinUni tulis = new TulisinUni();
    TulisinImbuh tui =  new TulisinImbuh();
    
    public CharSel(){
        tulis.setColor(0xD2D2D2);
    }
    
    public String getString(){
        if (chActive!=-1)
            return String.valueOf(ch[chActive]) + imStr ;
        else
            return "";
    }
    
    public void reset(){
        chActive=-1;
        //chStart = 0;
        //imStart = 0;
        level = 1;
        imStr = "";
    }
    
    public int getHeight(){
        return 1+22+24;
    }
    
    public int setActive(){
        
        if (chActive==chStart+Selected)
            chActive = -1; 
        else
            chActive = chStart+Selected;    
            
        return chActive;
    }
    
    public void keyPressed(int keyCode) {

        if (keyCode==Canvas.UP | keyCode==-1){
            level=1;
            if (Selected>visibles1-1) Selected = visibles1-1;
        }
        
        if (keyCode==Canvas.DOWN | keyCode==-2){
            if (chActive!=-1) {
                level=2;
                if (Selected>visibles2-1) Selected = visibles2-1;
            }
        }
        
        if (keyCode==Canvas.LEFT | keyCode==-3){
            if (level==1){
                if (Selected > 0) Selected--;
                else if (Selected==0 & chStart>0 ){

                    Selected= chStage[ichStage-1]-1;
                    chStart-= chStage[ichStage-1];

                    ichStage--;
                }
            } else {
                if (Selected > 0) Selected--;
                else if (Selected==0 & imStart>0 ){

                    Selected= imStage[iimStage-1]-1;
                    imStart-= imStage[iimStage-1];

                    iimStage--;
                }
            }
            
        }
        
        if (keyCode==Canvas.RIGHT | keyCode==-4){
            if (level==1){
                if (Selected<visibles1-1) Selected++;
                else if (Selected>=visibles1-1 & ch.length-chStart-1>visibles1 ) {

                    Selected=0;
                    chStart+=visibles1;

                    chStage[ichStage]=visibles1;

                    ichStage++;
                }
            } else {
                if (Selected<visibles2-1) Selected++;
                else if (Selected>=visibles2-1 & im.length-imStart-1>visibles2 ) {

                    Selected=0;
                    imStart+=visibles2;

                    imStage[iimStage]=visibles2;

                    iimStage++;
                }
            }
            
        }

        if (keyCode==Canvas.KEY_STAR){
            if (level==1)
                if (chActive==chStart+Selected){
                    chActive = -1;
                    imStr = "";
                } else
                    chActive = chStart+Selected;
            
            else{
                //imStr = replace(imStr, String.valueOf(im[Selected]), "");
                if (imStr.indexOf(im[imStart+Selected])!=-1)
                    imStr = hapusChar(imStr, im[imStart+Selected]);
                else
                    if (imStr.length()<3 ) imStr += im[imStart+Selected];
            }
        }
        
        
    } 
    
    private String hapusChar(String s, char c){
        char ca[] = s.toCharArray();
        
        String ret = "";
        
        for (int i = 0; i<ca.length; i++)
            if (ca[i] != c) ret += ca[i];
        
        return ret;
    }

    public void render(Graphics g, int y, int w){
        
        int h = 22;
        int xTri = 4;
        int yTri = y+(h/2);
        int yTri2 = y+h+(24/2);
        
        g.setColor(0x718191);
        g.fillRect(0, y-1, w, 1);
        
        g.setColor(0x708090);
        g.fillRect(0, y, w, h);
        
        g.setColor(0x0A246A);
        g.fillTriangle(xTri, yTri, xTri+4, yTri-4, xTri+4, yTri+4); // kiri
        g.fillTriangle(w-xTri, yTri, w-xTri-4, yTri-4, w-xTri-4, yTri+4);    // kanan
        
        
        
        int xChar = 15;
        int hChar = tulis.getHeight();
        int i1 = 0, i2=0;
        
        
        g.fillRect(0, y+h, w, 26);
        
        g.setColor(0x708090);
        g.fillTriangle(xTri, yTri2, xTri+4, yTri2-4, xTri+4, yTri2+4); // kiri
        g.fillTriangle(w-xTri, yTri2, w-xTri-4, yTri2-4, w-xTri-4, yTri2+4);    // kanan
        
        g.setColor(0x0A246A);
        
        for (int n=chStart; n<ch.length; n++){
            
            int wChar = tulis.charWidth(ch[n]);
            if (xChar+wChar < w-15){
                
                if (n==chActive){
                    g.setColor(0x0A246A);
                    g.fillRect(xChar-3, y, wChar+6, h);
                }
                
                if (i1==Selected  & level==1){
                    g.setColor(0x596672);
                    g.fillRect(xChar-2, y+1, wChar+4, h-2);
                }
                
                tulis.drawChar(g, ch[n], xChar, y+((h-hChar)/2), 0);

                xChar+=wChar+8;
                i1++;
                
            } else
                break;
            
        }
        
        xChar=15;
        if (chActive!=-1){
            System.out.println("imstar "+ imStart);
            for (int n=imStart; n<im.length; n++){

                int wChar = tui.charWidth(n);
                if (xChar+wChar < w-15){

                    if (imStr.indexOf(im[n])>=0){
                        g.setColor(0x00194C);
                        g.fillRect(xChar-3, y+h+1, wChar+6, 24-1);
                    }

                    if (i2==Selected & level==2){
                        g.setColor(0x596672);
                        g.fillRect(xChar-2, y+h+2, wChar+4, 24-3);
                    }

                    tui.drawChar(g, n, xChar, y+h+3);

                    xChar+=wChar+8;
                    i2++;

                } else
                    break;

            }
        }
        
        visibles1 = i1;
        visibles2 = i2;

    }
    
   
    
}
