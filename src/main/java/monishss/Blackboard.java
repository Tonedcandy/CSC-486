package monishss;

import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private LinkedList<String> samples;
    private Logger logger;
    String combo;
    Integer score;
    String display;
    String playerInput;

    private Blackboard() {
        super(new Object());
        logger = new Logger();
        samples = new LinkedList<>();
        score = 0;
        display = "";
        playerInput = "";
        logger.logData("\"UnixTimestamp\",\"Timestamp\",\"Display\",\"Input\",\"Score\"");
    }

    public static Blackboard getInstance() {
        if (instance == null) {
            instance = new Blackboard();
        }
        return instance;
    }

    public void addValue(String combo) {
        samples.add(combo);
        String oldCombo = "";
        this.combo= combo;
        firePropertyChange(combo, oldCombo, this.combo);
        System.out.println("WTB: "+ this.combo);

        if (samples.size() > 999){
            samples.clear();
        }
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setPlayerInput(String playerInput) {
        this.playerInput = playerInput;
    }

    public Integer getScore() {
        return score;
    }

    public void updateLog(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String timestamp = now.format(formatter);
        Long unixTimestamp = System.currentTimeMillis();
        String data = String.format("%d,\"%s\", \"%s\", \"%s\", %d", unixTimestamp,
                timestamp, display, playerInput, score);
        logger.logData(data);
    }

    public String initializeAppend(){
        return ",\"Display\",\"Input\",\"Score\"";
    }

    public String generateAppend(){
        return String.format(",\"%s\",\"%s\",%d", display, playerInput, score);
    }
}