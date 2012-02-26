
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class CharSel {
    // vokal, ngalagena, angka
    char ch[]={'\u1b83','\u1b84','\u1b85','\u1b86','\u1b87','\u1b88','\u1b89','\u1b8a','\u1b8b','\u1b8c',   
               '\u1b8d','\u1b8e','\u1b8f','\u1b90','\u1b91','\u1b92','\u1b93','\u1b94','\u1b95','\u1b96',  
               '\u1b97','\u1b98','\u1b99','\u1b9a','\u1b9b','\u1b9c','\u1b9d','\u1b9e','\u1b9f','\u1ba0',  
               '\u1bae','\u1baf','\u1bb0','\u1bb1','\u1bb2','\u1bb3','\u1bb4','\u1bb5','\u1bb6','\u1bb7',  
               '\u1bb8','\u1bb9'};
    
    char im[]={};
    
    String info[] = {"+ng (panyecek)", "+r (panglayar)", "+h (pangwisad)",
                     "a", "i", "u", "é", "o", "e", "eu",
                     "ka", "qa", "ga", "nga", "ca", "ja", "za", "nya", "ta", "da", "na", "pa", "fa", "va", "ba", "ma", "ya", "ra", "la", "wa", "sa", "xa", "ha", 
                     "-y- (pamingkal)", "-r- (panyakra)", "-l- (panyiku)", "a→i (panghulu)", "a→u (panyuku)", "a→é (panélég)", "a→o (panolong)", "a→e (pamepet)", "a→eu (paneuleug)", "(pamaeh)",
                     "", "", "", "kha", "sya",
                     "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    
    // key candybar
    private int lastKey = 0, keyIndex=0;
    private long tick;
    
    String[] keys = {"\u1bb1",                                // 1
                     "\u1b83\u1b98\u1b8e\u1bb2",              // a, ba, ca, 2
                     "\u1b93\u1b88\u1b96\u1b86\u1bb3",        // da, e, fa, é, 3
                     "\u1b8c\u1ba0\u1b84\u1bb4",              // ga, ha, i, 4
                     "\u1b8f\u1b8a\u1bae\u1b9c\u1bb5",        // j, k, kha, l, 5
                     "\u1b99\u1b94\u1b8d\u1b91\u1b87\u1bb6",  // ma, na, nga, nya, o, 6 
                     "\u1b95\u1b8b\u1b9b\u1b9e\u1baf\u1bb7",  // pa, qa, ra, sa, sya, 7
                     "\u1b92\u1b85\u1b97\u1bb8",              // ta, u, va, 8
                     "\u1b9d\u1b9f\u1b9a\u1b90\u1bb9",        // wa, xa, ya, za, 9
                     "\u0020\u1bb0"};                         // spasi, 0
    
    // kursor
    private int iSelected=0, chActive=-1, imVisibles ;
    private int ichStage=0;
    private int imStart = 0;
    private int imStage[] = new int[10];
    private int iimStage=0;
    private int level=1;

    private StringBuffer sbImbuh = new StringBuffer("");
    
    private Vector vtChar = new Vector();
    
    private TulisinUni tiUni = new TulisinUni();
    private TulisinImbuh tiImbuh =  new TulisinImbuh();
    
    private String sInfo="";
    
    private Font fnInfo = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    
    public CharSel(int w){
        tiUni.setColor(0xffffff);
        
        String part="";
               
        // 
        int wChars=0; 
        int wMax = w-8;
        for (int i=0; i<ch.length; i++){
            
            int wChar = tiUni.charWidth(ch[i])+6;
            
            if (wChars + wChar >= wMax){
                vtChar.addElement(part);
                part = String.valueOf(ch[i]);
                wChars = wChar;
            }
            else {
                part += ch[i];
                wChars += wChar;
            }
        }
        
        if (part.length()>0) vtChar.addElement(part);
        
        setInfo(getCharActive());
        
    }
    
    private char[] arChar(int i){
        char ret[] = {'\u1b80', '\u1b81', '\u1b82', '\u1ba1', '\u1ba2', '\u1ba3', '\u1ba4', '\u1ba5', '\u1ba6', 
                      '\u1ba7', '\u1ba8', '\u1ba9', '\u1baa'};
    
        if (i >= 0x1b83 & i <= 0x1b89)    // vokal
            ret = new char[]{'\u1b80', '\u1b81', '\u1b82'};
        
        else if (i == 0x1b94 | i == 0x1b9e )  // na | sa    --> tanpa pamingkal
            ret = new char[]{'\u1b80', '\u1b81', '\u1b82', '\u1ba2', '\u1ba3', '\u1ba4', '\u1ba5', '\u1ba6', 
                             '\u1ba7', '\u1ba8', '\u1ba9', '\u1baa'};
        
        else if (i >= 0x1bb0 & i <= 0x1bb9) // angka
            ret = new char[]{};
        
        return ret; 
    }

    public void reset(){
        
        chActive=-1;
        level = 1;
        sbImbuh.delete(0, sbImbuh.length());
        imStart = 0;
        setInfo(getCharActive());
    }
    
    private String getInfo(char c){
        return info[c - 0x1b80];   // - mulai char sunda
    }
    
    public String getInfo(){
        return sInfo;
    }
    
    public String getString(){
        if (chActive!=-1)
            return (String.valueOf((char)chActive) + sbImbuh.toString()).trim() ;
        else
            return "";
    }

    //public int getHeight(){
    //    return 1+22+24;
    //}
    
    private void setInfo(char c){
        sInfo = info[c - 0x1b80];   // - mulai char sunda
    }
    
    
    public void setSelected(char c){
        
        int iFound = -1;

        for (int i=0; i<vtChar.size(); i++){
            
            iFound = vtChar.elementAt(i).toString().indexOf(c);
            
            if (iFound != -1){
                level = 1;
                ichStage = i;
                iSelected = iFound;
                setInfo(c);
                break;
            }
        }
        
    }
    
    public void keyPressed(int keyCode) {

        keyEvent(keyCode);
        
        if (keyCode==Canvas.UP | keyCode==-1){
            
            
            if (level!=1){ 
                level=1;
                if (iSelected>vtChar.elementAt(ichStage).toString().length()-1)
                    iSelected = vtChar.elementAt(ichStage).toString().length()-1;
            } 
            else if (level==1 & chActive==-1){
                if (ichStage>0) ichStage--;
                if (iSelected>vtChar.elementAt(ichStage).toString().length()-1)
                    iSelected = vtChar.elementAt(ichStage).toString().length()-1;
            }
            
            setInfo(getCharActive());
            
        }
        
        if (keyCode==Canvas.DOWN | keyCode==-2){
            if (chActive!=-1 & im.length>0) {
                level=2;
                if (iSelected>imVisibles-1) iSelected = imVisibles-1;
                
                setInfo(im[imStart+iSelected]);
            }
            else if (level==1 & chActive==-1){
                if (ichStage<vtChar.size()-1) ichStage++;
                if (iSelected>vtChar.elementAt(ichStage).toString().length()-1)
                    iSelected = vtChar.elementAt(ichStage).toString().length()-1;
                
                setInfo(getCharActive());
            }
        }
        
        if (keyCode==Canvas.LEFT | keyCode==-3){
            if (level==1){
                
                if (iSelected > 0) iSelected--;
                else if (iSelected == 0 & ichStage > 0 & chActive==-1){

                    ichStage--;
                    iSelected = vtChar.elementAt(ichStage).toString().length()-1;
                    
                }
                
                setInfo(getCharActive());
                
            } else {
                if (iSelected > 0) iSelected--;
                else if (iSelected==0 & imStart>0 ){

                    iSelected= imStage[iimStage-1]-1;
                    imStart-= imStage[iimStage-1];

                    iimStage--;
                }
                
                setInfo(im[imStart+iSelected]);
            }
            
        }
        
        if (keyCode==Canvas.RIGHT | keyCode==-4){
            if (level==1){
                
                int nChar = vtChar.elementAt(ichStage).toString().length()-1;
                
                if (iSelected < nChar) iSelected++;
                else if (iSelected >= nChar & vtChar.size()-1 > ichStage & chActive==-1) {

                    iSelected=0;
                    ichStage++;
                }
                
                setInfo(getCharActive());
                
            } else {
                if (iSelected<imVisibles-1) iSelected++;
                else if (iSelected>=imVisibles-1 & im.length-imStart-1>imVisibles ) {

                    iSelected=0;
                    imStart+=imVisibles;

                    imStage[iimStage]=imVisibles;

                    iimStage++;
                }
                
                setInfo(im[imStart+iSelected]);
            }
            
        }

        //if (keyCode==Canvas.KEY_STAR) select();
    } 
    
    private char getCharActive(){
        return vtChar.elementAt(ichStage).toString().charAt(iSelected);
    }
    
    public void select(){
        if (level==1) {

            if (chActive == getCharActive()){
                chActive = -1;
                sbImbuh.delete(0, sbImbuh.length());
            } else
                chActive = getCharActive();
        
            im = arChar(chActive);
        }
        else{
            if (sbImbuh.toString().indexOf(im[imStart+iSelected])!=-1)
                sbImbuh.deleteCharAt(sbImbuh.toString().indexOf(im[imStart+iSelected]));
            else
                if (sbImbuh.length()<3 ){
                    char cs = im[imStart+iSelected];
                    
                    if (cs == '\u1ba1' | cs == '\u1ba2' | cs == '\u1ba1')
                        sbImbuh.insert(0, cs);
                    else
                        sbImbuh.append(cs);
                }
            
        }
    }
    
    public void render(Graphics g, int h, int w){
        
        int y = (chActive!=-1 & im.length>0) ? h-20-26 :  h-20-2;
        
        int xTri = 4;
        int yTri = y+(20/2);
        int yTri2 = y+20+(24/2);
        
        // garis luhur
        g.setColor(0x718191);
        g.fillRect(0, y-1, w, 1);
        
        
        // tempat aksara sunda
        g.setColor(0x708090);
        g.fillRect(0, y, w, 20);
        
        //g.fillTriangle(xTri, yTri, xTri+4, yTri-4, xTri+4, yTri+4); // panah kiri
        //g.fillTriangle(w-xTri, yTri, w-xTri-4, yTri-4, w-xTri-4, yTri+4);    // panah kanan
        
        // tempat rarangkén
        g.setColor(0x0A246A);

        if (chActive!=-1 & im.length>0){
            
            g.fillRect(0, y+20, w, 26);
        
            g.setColor(0x708090);
            g.fillTriangle(xTri, yTri2, xTri+4, yTri2-4, xTri+4, yTri2+4); // panah kiri
            g.fillTriangle(w-xTri, yTri2, w-xTri-4, yTri2-4, w-xTri-4, yTri2+4);    // panah kanan
            
        }
        else {
            g.fillRect(0, y+20, w, 2);
            
            //int xTriCH = w-6, yTriCH1= y+5, yTriCH=yTriCH1+8;
            //g.fillTriangle(xTriCH-3, yTriCH1+3, xTriCH, yTriCH1, xTriCH+3, yTriCH1+3);    // panah atas
            //g.fillTriangle(xTriCH-3, yTriCH-3, xTriCH, yTriCH, xTriCH+3, yTriCH-3);    // panah bawah
        }
        
        int xChar = 0;
        int hChar = tiUni.getHeight();
        
        String part = vtChar.elementAt(ichStage).toString();
        
        g.setColor(0x0A246A);
        
        int wChars=0;
        for (int i=0; i<part.length(); i++){
            wChars+=tiUni.charWidth(part.charAt(i))+6;
        }
        
        xChar = ((w-wChars)/2)+4;
        
        for (int i=0; i<part.length(); i++){
            
            int wChar = tiUni.charWidth(part.charAt(i));
             
            if (part.charAt(i) == chActive){
                g.setColor(0x0A246A);
                g.fillRect(xChar-3, y, wChar+6, 20);
            }

            if (i == iSelected  & level == 1){
                g.setColor(0x596672);
                g.fillRect(xChar-2, y+1, wChar+4, 20-2);
            }
            
            tiUni.drawChar(g, part.charAt(i), xChar, y+((20-hChar)/2), 0);
            xChar+=wChar+6;
        }
        
        //System.out.println("xChar " + xChar);
        
        xChar=15;
        int i=0;
        
        if (chActive!=-1){

            for (int n=imStart; n<im.length; n++){

                int wChar = tiImbuh.charWidth(im[n]);
                if (xChar+wChar < w-15){

                    if (sbImbuh.toString().indexOf(im[n])>=0){
                        g.setColor(0x00194C);
                        g.fillRect(xChar-3, y+20+1, wChar+6, 24-1);
                    }

                    if (i == iSelected & level == 2){
                        g.setColor(0x596672);
                        g.fillRect(xChar-2, y+20+2, wChar+4, 24-3);
                    }

                    tiImbuh.drawChar(g, im[n], xChar, y+20+3);

                    xChar+=wChar+6;
                    i++;

                } else
                    break;
            }
            
            imVisibles = i;
        }
        
        g.setFont(fnInfo);
        g.setColor(0x596672);
        g.drawString(getInfo(), 2,  y - fnInfo.getHeight() -2, g.LEFT | g.TOP);
        
    }

    //
    public void keyEvent(int cod){

       if (cod >= Canvas.KEY_NUM1 && cod <= Canvas.KEY_NUM9){
            if (cod == lastKey){

                if (System.currentTimeMillis() - tick < 900)
                    keyIndex ++;
                else
                    keyIndex = 0;

            }else
                keyIndex = 0;
            
            int pushedKey=0;
            
            switch(cod){
                case Canvas.KEY_NUM1: pushedKey=0; break;
                case Canvas.KEY_NUM2: pushedKey=1; break;
                case Canvas.KEY_NUM3: pushedKey=2; break;
                case Canvas.KEY_NUM4: pushedKey=3; break;
                case Canvas.KEY_NUM5: pushedKey=4; break;
                case Canvas.KEY_NUM6: pushedKey=5; break;
                case Canvas.KEY_NUM7: pushedKey=6; break;
                case Canvas.KEY_NUM8: pushedKey=7; break;
                case Canvas.KEY_NUM9: pushedKey=8; break;
                //case Canvas.KEY_NUM0: pushedKey=9; break;
            }

            if (keys[pushedKey].length() == keyIndex) keyIndex = 0;

            setSelected(keys[pushedKey].charAt(keyIndex));
         } else {}

        lastKey = cod;
        
        tick = System.currentTimeMillis();
    }
    
}
