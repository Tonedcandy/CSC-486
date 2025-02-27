package monishss;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;

/**
 * Taken from https://github.com/javiergs/NielsenSkeleton
 *
 * Blackboard class is a Singleton that contains a list of samples.
 * It extends PropertyChangeSupport to notify the observers when a new sample is added.
 *
 * @author javiergs
 * @version 1.0
 */
public class Blackboard extends PropertyChangeSupport {

    private static Blackboard instance;
    private LinkedList<Map<String,Double>> samples;
    private Map<String,Double> receivedValues;

    private Blackboard() {
        super(new Object());
        samples = new LinkedList<>();
    }

    public static Blackboard getInstance() {
        if (instance == null) {
            instance = new Blackboard();
        }
        return instance;
    }

    public void addValue(Map<String,Double> values) {
        samples.add(values);
        Map<String, Double> oldValues = receivedValues;
        this.receivedValues = values;
        firePropertyChange("values", oldValues, this.receivedValues);
        if (samples.size() > 999){
            samples.clear();
        }
    }
}