
import javax.microedition.lcdui.*;


public class Tulisin extends Canvas implements CommandListener{
    Askuna midlet;
    
    private TextBox tbInput  = new TextBox("Masukan", "", 160, 0);
    private Command cmdInput = new Command("Input", Command.BACK, 1);
    private Command cmdExit  = new Command("Keluar", Command.EXIT, 1);
    private Command cmdConv  = new Command("Konversi", Command.OK, 1);
    
    private LatinToSunda LTS = new LatinToSunda();
    
    public Tulisin (Askuna midlet, String s){
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
        drawAksara(g, LTS.StartConvert(tbInput.getString()), 5, 15);
        
    }
    
    private void drawAksara(Graphics g, String s, int x, int y){
        Image img = null;
        String hex = "";
        int lastx = x, lastw=0, hImg = 14;
        
        for (int i = 0; i<s.length(); i++){
            hex = Integer.toHexString(s.charAt(i)).toUpperCase();
            
            System.out.println("CHR " + i + "\t : "+ hex);
            
            // a é i o u e eu
            if (hex.equals("41") | hex.equals("61") | hex.equals("7B") | hex.equals("49") | hex.equals("4F") | hex.equals("55") | hex.equals("45") | hex.equals("7D")){
                
                if ((getWidth()-lastx) < 10) {
                    y += 30;
                    lastx = 5;
                }
                
                img = getImage("/font/" + hex + ".png");
                
                g.drawImage(img, lastx, y, g.LEFT | g.TOP);
                
                lastx += img.getWidth() + 2;
                lastw = img.getWidth();
                
                
            }
            else if (hex.equals("59")){
                img = getImage("/font/" + hex + ".png");
                
                g.drawImage(img, lastx-15, y+7, g.LEFT | g.TOP);
                
                lastx += 7;
                lastw = img.getWidth();
            }
            else if (hex.equals("4E") | hex.equals("51") | hex.equals("5D") | hex.equals("65") | hex.equals("69")){ // atas
                img = getImage("/font/" + hex + ".png");
                
                g.drawImage(img, lastx-((lastw/2)+(img.getWidth()/2))-2, y-img.getHeight(), g.LEFT | g.TOP);
                
            }
            else if (hex.equals("52") | hex.equals("75")){ // bawah
                img = getImage("/font/" + hex + ".png");
                
                g.drawImage(img, lastx-((lastw/2)+(img.getWidth()/2))-2, y+hImg, g.LEFT | g.TOP);
                
            }
            else if (hex.equals("48") | hex.equals("6F") | hex.equals("3B")){   // kanan
                img = getImage("/font/" + hex + ".png");
                
                g.drawImage(img, lastx-1, y+7, g.LEFT | g.TOP);
                
                lastx += img.getWidth() + 1;
                lastw = img.getWidth();
            }
            else if (hex.equals("20")){
                lastx += 3;
            }
            else {
                if ((getWidth()-lastx) < 10) {
                    y += 30;
                    lastx = 5;
                }
                
                img = getImage("/font/" + hex + ".png");
                
                g.drawImage(img, lastx, y, g.LEFT | g.TOP);
                
                lastx += img.getWidth()+2;
                lastw = img.getWidth();
                
            }
        }
        
    } 
    
    Image getImage(String s){
        Image tmp = null;
        
        try {
            tmp = Image.createImage(s);
        } catch (Exception e) {
            tmp = getImage("/font/" + "00.png");
        }
        
        
        return tmp;
    }
    
    
}
