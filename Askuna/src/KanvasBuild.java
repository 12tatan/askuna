
import javax.microedition.lcdui.*;

public class KanvasBuild extends Canvas implements CommandListener{
    Askuna midlet;
    
    private Command cmdSelect  = new Command("Select", Command.OK, 1);
    private Command cmdApply  = new Command("Apply", Command.SCREEN, 2);
    private Command cmdClose = new Command("Close", Command.BACK, 2);
    
    private ToLatin TL = new ToLatin();
    private TulisinUni tiUni = new TulisinUni();
    private CharSel charSel = new CharSel(getWidth());
    
    int xStart;
    
    private Font fnInfo = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    private Font fnLatin = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
    
    private StringBuffer sbUntun = new StringBuffer("");
    
    public KanvasBuild (Askuna midlet){
        this.midlet = midlet;
       
        addCommand(cmdSelect);
        addCommand(cmdClose);
        setCommandListener(this);

    }
    
    protected void keyPressed(int keyCode) {
        // hapus
        if (keyCode == Canvas.KEY_POUND || keyCode == -8)
            if (sbUntun.length()>0) sbUntun.delete(sbUntun.length()-1, sbUntun.length());
            
        if (keyCode == Canvas.KEY_NUM0)
            sbUntun.append('\u0020');
            
        charSel.keyPressed(keyCode);
        
        repaint();
    }
    
    public void commandAction(Command c, Displayable d) {
        
        if (c == cmdApply) {
            sbUntun.insert(sbUntun.length(), charSel.getString());
            charSel.reset();
        }
        
        if (c == cmdSelect) charSel.select();
        
        if (c == cmdClose) {
            Golodog gg = new Golodog(midlet);
            Display.getDisplay(midlet).setCurrent(gg.lsMenu);
        }
        
        if (charSel.getString().equals("")) {
            removeCommand(cmdApply);
            addCommand(cmdClose);
        } else {
            removeCommand(cmdClose);
            addCommand(cmdApply);
        }
        
        repaint();
    }
    
    
    protected void paint(Graphics g) {
        g.setColor(0xffffff);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        charSel.render(g, getHeight()- charSel.getHeight(), getWidth());
        
        xStart = tiUni.drawString(g,  sbUntun.toString(), 5, 10, 0);
        tiUni.drawString(g,  charSel.getString(), 5+xStart, 10, 0x97ABBF);
        
        g.setColor(0x718191);
        g.fillRect(0, (getHeight()/3)-2, getWidth(), 1);
        
        g.setFont(fnLatin);
        g.setColor(0x16274C);
        g.drawString(TL.convert(sbUntun.toString()), 5, getHeight()/3, g.LEFT | g.TOP);
        
        g.setColor(0x97ABBF);
        g.fillRect(5 + fnLatin.stringWidth(TL.convert(sbUntun.toString())), getHeight()/3, fnLatin.stringWidth(TL.convert(charSel.getString())), fnLatin.getHeight());
        
        g.setColor(0x1F3468);
        g.drawString(TL.convert(charSel.getString()), 5 + fnLatin.stringWidth(TL.convert(sbUntun.toString())), getHeight()/3, g.LEFT | g.TOP);
        
        g.setFont(fnInfo);
        g.setColor(0x596672);
        g.drawString(charSel.getInfo(), 2,  getHeight() - fnInfo.getHeight() - charSel.getHeight() -2, g.LEFT | g.TOP);
    }

}
