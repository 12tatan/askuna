
import javax.microedition.lcdui.*;

public class Kanvas extends Canvas implements CommandListener{
    Askuna midlet;
    
    private TextBox tbInput  = new TextBox("Masukan", "", 160, 0);
    private Command cmdInput = new Command("Input", Command.BACK, 1);
    private Command cmdExit  = new Command("Keluar", Command.EXIT, 1);
    private Command cmdConv  = new Command("Konversi", Command.OK, 1);
    
    private ToSundaUni TSU = new ToSundaUni();
    private ToLatin TL = new ToLatin();
    private TulisinEx tulisin = new TulisinEx();
    
    private int idx=0;
    
    public Kanvas (Askuna midlet){
        this.midlet = midlet;
       
        tbInput.addCommand(cmdConv);
        tbInput.addCommand(cmdExit);
        tbInput.setCommandListener(this);
        
        addCommand(cmdInput);
        setCommandListener(this);

        // tes
        System.out.println("LATIN :"+TL.convert("ᮕᮨᮁᮞᮤᮘ᮪ ᮙᮅᮀ ᮘᮔ᮪ᮓᮥᮀ"));
        
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
        tulisin.setColor(0x0000ff);
        tulisin.drawString(g, TSU.convert(tbInput.getString()), 5, 15);
        
        //drawCharChooser(g, 20);
    }

}
