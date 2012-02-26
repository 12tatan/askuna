
import javax.microedition.lcdui.*;

public class Golodog implements CommandListener{
    Askuna midlet;
    
    private Display display;
    
    private String menu[] = {"Latin ka Sunda", "Serat Aksara Sunda", "Kaluar"};
    List lsMenu = new List("AskunaChar v0.1", List.IMPLICIT, menu, null);
    
    public Golodog (Askuna midlet){
        this.midlet = midlet;
        this.display = Display.getDisplay(midlet);
        
        lsMenu.setCommandListener(this);
        
        display.setCurrent(lsMenu);
    }
    
    protected void keyPressed(int keyCode) {

    }
    
    public void commandAction(Command c, Displayable d) {
        if (c.getCommandType() == Command.SCREEN) {
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
