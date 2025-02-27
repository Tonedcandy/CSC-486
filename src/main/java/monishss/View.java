// Monish S. S., Megan W., Andrea Ng
package monishss;
import java.awt.*;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

public class View extends JPanel implements PropertyChangeListener {
    private Blackboard blackboard;
    double xOffset = 0;
    double yOffset = 0;
    double radius = 100;
    Color circleColor = Color.BLUE;
    public View() {
        blackboard = Blackboard.getInstance();
        blackboard.addPropertyChangeListener(this);
    }
    public void propertyChange(PropertyChangeEvent evt) {
        Map<String,Double> values = (Map<String, Double>) evt.getNewValue();
        // get offset
        xOffset = (values.get("leftEyeGazeX") + values.get("rightEyeGazeX"))/2;
        yOffset = (values.get("leftEyeGazeY") + values.get("rightEyeGazeY"))/2;

        // this is where you would
        // set circle color if you knew
        // how to check leftArmUp/leftArmLow
        // and rightArmUp/RightArmLow
        // to determine whether your right or
        // left arm are up

        // get size
        radius = (values.get("headZ") * 50) + 50;

        // redraw circle
        repaint();
    }
    public void paintComponent ( Graphics gr )
    {
        super.paintComponent( gr );

        int newRadius = (int) Math.round(radius);
        int x = (int) Math.round((double) getWidth()/2 + ((double) getWidth()/2 * xOffset));
        int y = (int) Math.round((double) getHeight()/2 + ((double) getHeight()/2 * yOffset));

        gr.setColor(circleColor);
        gr.fillOval( x - newRadius, y - newRadius, newRadius * 2, newRadius * 2);
    }
}

