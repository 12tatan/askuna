
import javax.microedition.lcdui.*;

public class Tulisin extends Canvas implements CommandListener{
    Askuna midlet;
    
    private TextBox tbInput  = new TextBox("Masukan", "", 160, 0);
    private Command cmdInput = new Command("Input", Command.BACK, 1);
    private Command cmdExit  = new Command("Keluar", Command.EXIT, 1);
    private Command cmdConv  = new Command("Konversi", Command.OK, 1);
    
    private ToSundaUni TSU = new ToSundaUni();
    
    public Tulisin (Askuna midlet){
        this.midlet = midlet;
       
        tbInput.addCommand(cmdConv);
        tbInput.addCommand(cmdExit);
        tbInput.setCommandListener(this);
        
        addCommand(cmdInput);
        setCommandListener(this);
    }
    
    protected void keyPressed(int keyCode) {
    
    }
    
    public void commandAction(Command c, Displayable d) {
        
        if (c == cmdConv) {
            Display.getDisplay(midlet).setCurrent(this);
            repaint();
        }
        
        if (c == cmdInput) Display.getDisplay(midlet).setCurrent(tbInput);
        if (c == cmdExit) midlet.exitMIDlet();
    }
    
    protected void paint(Graphics g) {
        g.setColor(0xFFFFFF);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(0x000000);
        drawChar(g, TSU.Convert(tbInput.getString()), 5, 15);
        
    }
    
    private void drawChar(Graphics g, String s, int x, int y){
        Image img=null, imgNext=null;

        int lastx=x, lastw=0, hImg=14;
        
        char ch[] = s.toCharArray();
        char c=0, cNext=0;
        
        Font font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        g.setFont(font);
        
        for (int i = 0; i<ch.length; i++){
            c = ch[i];
            
            if (ch.length>i+1) 
                cNext = ch[i+1];
            else
                cNext = 0;
            
            System.out.println("CHR " + i + " : " +  Integer.toHexString(c));
            
            if ((c >= 0x1b83 & c <= 0x1ba0) | c == 0x1bae | c == 0x1baf |   // char hurup
                (c >= 0x1bb0 & c <= 0x1bb9) | c == 0x7c) {                  // char angka + vertical line
                
                if ((getWidth()-lastx) < 12) {
                    y += 30;    // baris anyar
                    lastx = 5;
                }
                
                if (cNext == 0x1ba6){   // panélég
                    img = getImageChar('\u1ba6');
                    g.drawImage(img, lastx, y+7, g.LEFT | g.TOP);
                    lastx += img.getWidth() + 1;
                    
                    img = getImageChar(c);
                    g.drawImage(img, lastx, y, g.LEFT | g.TOP);
                
                    lastx += img.getWidth() + 2;
                    lastw = img.getWidth();
                    
                    i+=1;   // luncat
                } else {
                
                    img = getImageChar(c);
                    g.drawImage(img, lastx, y, g.LEFT | g.TOP);

                    lastx += img.getWidth() + 2;
                    lastw = img.getWidth();
                }
                
            }
            else if (c == 0x1ba1) { // pamingkal
                img = getImageChar(c);
                
                g.drawImage(img, lastx-15, y+7, g.LEFT | g.TOP);
                
                lastx += 7;
                lastw = img.getWidth();
            }
            else if (isLuhur(c)) { // rarangkén luhur
                
                if (isLuhur(cNext)){    // double rarangkén
                    img = getImageChar(c);
                    imgNext = getImageChar(cNext);
                    
                    int x2 = lastx - (lastw/2) - ((img.getWidth()+imgNext.getWidth()))/2 - 3;
                    
                    g.drawImage(img, x2, y-img.getHeight(), g.LEFT | g.TOP);
                    g.drawImage(imgNext, x2+img.getWidth(), y-imgNext.getHeight(), g.LEFT | g.TOP);
                    
                    i+=1;   // luncat
                } else {
                    img = getImageChar(c);
                    g.drawImage(img, lastx-((lastw/2)+(img.getWidth()/2))-2, y-img.getHeight(), g.LEFT | g.TOP);
                }
                
            }
            else if (c == 0x1ba2 | c == 0x1ba5) { // rarangkén handap
                img = getImageChar(c);
                
                g.drawImage(img, lastx-((lastw/2)+(img.getWidth()/2))-3, y+hImg, g.LEFT | g.TOP);
                
            }
            else if (c == 0x1b82 | c == 0x1ba7 | c == 0x1baa) {   // rarangkén katuhu
                img = getImageChar(c);
                
                g.drawImage(img, lastx-1, y+7, g.LEFT | g.TOP);
                
                lastx += img.getWidth() + 1;
                lastw = img.getWidth();
            }
            else if (c == 0x20) {   //spasi
                System.out.println("---------------");
                lastx += 3;
            }
            else {
                if ((getWidth()-lastx) < 12) {
                    y += 30;
                    lastx = 5;
                }
                
                g.drawString(String.valueOf(c), lastx + 2, y, g.LEFT | g.TOP);
                
                lastx += font.charWidth(c)+4;
                lastw = font.charWidth(c)+4;  
            }
            
        }
        
    } 
    
    // Rarangkén Luhur
    private boolean isLuhur(char c){
        if (c == 0x1ba4 | c == 0x1ba8 | c == 0x1ba9 | c == 0x1b80 |c == 0x1b81)
            return true;
        else
            return false;
    }
    
    // Nyandak gambar
    private Image getImageChar(char c){
        Image img = null;
        
        try{
            img = Image.createImage(("/font/" + Integer.toHexString(c) + ".png"));
        } catch (Exception e) {

        }
        
        return img;
    }
    
    
}
