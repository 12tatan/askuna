
import javax.microedition.lcdui.*;

public class KanvasConv extends Canvas implements CommandListener{
    Askuna midlet;
    
    private TextBox tbInput  = new TextBox("Masukan Teks", "", 160, 0);
    

    private Command cmdExit  = new Command("Exit", Command.EXIT, 1);
    private Command cmdConv  = new Command("Convert", Command.OK, 1);
    private Command cmdInput  = new Command("Input", Command.OK, 1);
    
    private ToSundaUni TSU = new ToSundaUni();
    private TulisinUni tulisin = new TulisinUni();
    
    int xStart;
    
    public KanvasConv (Askuna midlet){
        this.midlet = midlet;
       
        addCommand(cmdInput);
        
        tbInput.addCommand(cmdConv);
        tbInput.addCommand(cmdExit);
        tbInput.setCommandListener(this);
        
        setCommandListener(this);
    }
    
    protected void keyPressed(int keyCode) {

    }
    
    public void commandAction(Command c, Displayable d) {
        
        if (c == cmdInput) Display.getDisplay(midlet).setCurrent(tbInput);
        
        if (c == cmdConv) {
            Display.getDisplay(midlet).setCurrent(this);
            repaint();
        }
        
        if (c == cmdExit) midlet.exitMIDlet();
    }
    
    
    protected void paint(Graphics g) {
        g.setColor(0xFFFFFF);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(0x000000);

        tulisin.drawString(g, TSU.convert(tbInput.getString()), 5, 15, 0);
    }

}
