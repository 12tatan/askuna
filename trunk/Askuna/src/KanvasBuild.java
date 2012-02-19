
import javax.microedition.lcdui.*;

public class KanvasBuild extends Canvas implements CommandListener{
    Askuna midlet;
    
    private Command cmdApply  = new Command("Apply", Command.ITEM, 1);
    private Command cmdReset = new Command("Reset", Command.ITEM, 1);
    private Command cmdExit = new Command("Exit", Command.ITEM, 1);
    
    private ToSundaUni TSU = new ToSundaUni();
    private ToLatin TL = new ToLatin();
    private TulisinUni tulisin = new TulisinUni();
    private CharSel charSel = new CharSel();
    
    private TulisinImbuh tui = new TulisinImbuh();
    
    private String strRangkai ="";
    int xStart;
    
    public KanvasBuild (Askuna midlet){
        this.midlet = midlet;
       
        addCommand(cmdApply);
        addCommand(cmdReset);
        addCommand(cmdExit);
        setCommandListener(this);

    }
    
    protected void keyPressed(int keyCode) {
        charSel.keyPressed(keyCode);
        repaint();
    }
    
    public void commandAction(Command c, Displayable d) {
        
        if (c == cmdApply) {
            strRangkai += charSel.getString();
            charSel.reset();
            repaint();
        }
        
        if (c == cmdReset) {
            strRangkai = "";
            charSel.reset();
            repaint();
        }
        
        if (c == cmdExit) midlet.exitMIDlet();
    }
    
    
    protected void paint(Graphics g) {
        g.setColor(0xFFFFFF);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        charSel.render(g, getHeight()- charSel.getHeight(), getWidth());
        
        xStart = tulisin.drawString(g,  strRangkai, 5, 10, 0);
        tulisin.drawString(g,  charSel.getString(), 5+xStart, 10, 0x97ABBF);
        
        g.drawString(TL.convert(strRangkai), 5, 55, g.LEFT | g.TOP);
        g.drawString(TL.convert(charSel.getString()), 5, 75, g.LEFT | g.TOP);
    }

}
