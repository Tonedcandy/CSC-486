// Monish S. S., Megan W., Andrea Ng
package monishss;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Blackboard extends PropertyChangeSupport {
    private static Blackboard blackboard;

    private String pad;

    private Blackboard() {
        super(new Object());

    }

    public static Blackboard getInstance() {
        if (blackboard == null) {
            blackboard = new Blackboard();
        }
        return blackboard;
    }

    public void writeToBlackboard(String pad) throws InterruptedException {
        String oldPad = "";
        this.pad = pad;

        System.out.println("WTB: "+pad);
        // Fire the event: property name, old value, and new value
        switch (pad){
            case "+++":
                firePropertyChange("+++", oldPad, pad);
                break;
            case "++-":
                firePropertyChange("++-", oldPad, pad);
                break;
            case "+-+":
                firePropertyChange("+-+", oldPad, pad);
                break;
            case "+--":
                firePropertyChange("+--", oldPad, pad);
                break;
            case "-++":
                firePropertyChange("-++", oldPad, pad);
                break;
            case "-+-":
                firePropertyChange("-+-", oldPad, pad);
                break;
            case "--+":
                firePropertyChange("--+", oldPad, pad);
                break;
            case "---":
                firePropertyChange("---", oldPad, pad);
                break;
            default:
                break;

        }



    }

    public void displayPADValues(){
        System.out.println("Pleasure: " + pad+" Arousal: " + pad+" Dominance: " + pad);
        System.out.println();
    }




}
