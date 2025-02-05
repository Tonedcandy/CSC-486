// Monish S. S., Megan W., Andrea Ng
package monishss;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;

public class Blackboard extends PropertyChangeSupport {
    private static Blackboard blackboard;
    private LinkedList<String> samples;

    private String pad;

    private Blackboard() {
        super(new Object());
        samples = new LinkedList<>();

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
        samples.add(pad);

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

        if (samples.size() > 999){
            samples.clear();
        }



    }

    public void displayPADValues(){
        System.out.println("Pleasure: " + pad+" Arousal: " + pad+" Dominance: " + pad);
        System.out.println();
    }




}
