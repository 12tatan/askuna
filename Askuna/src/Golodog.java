
import javax.microedition.lcdui.*;

public class Golodog implements CommandListener{
    Askuna midlet;
    
    private Display display;
    
    private String menu[] = {"Latin ka Sunda", "Rangkai Aksara Sunda", "Kaluar"};
    List lsMenu = new List("AskunaChar v0.1", List.IMPLICIT, menu, null);
    private Command cmdOK = new Command("OK", Command.OK, 1);
    
    public Golodog (Askuna midlet){
        this.midlet = midlet;
        this.display = Display.getDisplay(midlet);
        
        lsMenu.addCommand(cmdOK);
        lsMenu.setCommandListener(this);
        
        display.setCurrent(lsMenu);
    }
    
    protected void keyPressed(int keyCode) {

    }
    
    public void commandAction(Command c, Displayable d) {
        if (c==cmdOK) {
            switch (lsMenu.getSelectedIndex()){
                case 0: display.setCurrent(new KanvasConv(midlet));
                    break;
                case 1:  display.setCurrent(new KanvasBuild(midlet));
                    break;
                case 2: midlet.exitMIDlet(); 
                    break;
            }
        }
    }
    
    
}
