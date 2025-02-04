// Monish S. S., Megan W., Andrea Ng
package monishss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JPanel implements PropertyChangeListener {
    private Blackboard blackboard;
    JPanel padPanel1, padPanel2, padPanel3, padPanel4, padPanel5, padPanel6, padPanel7, padPanel8;

    public View() {
        blackboard = Blackboard.getInstance();
        blackboard.addPropertyChangeListener(this);
        setLayout(new GridLayout(2, 4, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.BLACK);
        padPanel1 = new JPanel();
        padPanel2 = new JPanel();
        padPanel3 = new JPanel();
        padPanel4 = new JPanel();
        padPanel5 = new JPanel();
        padPanel6 = new JPanel();
        padPanel7 = new JPanel();
        padPanel8 = new JPanel();

        padPanel1.setBackground(new Color(64, 64, 64));
        padPanel2.setBackground(new Color(64, 64, 64));
        padPanel3.setBackground(new Color(64, 64, 64));
        padPanel4.setBackground(new Color(64, 64, 64));
        padPanel5.setBackground(new Color(64, 64, 64));
        padPanel6.setBackground(new Color(64, 64, 64));
        padPanel7.setBackground(new Color(64, 64, 64));
        padPanel8.setBackground(new Color(64, 64, 64));


        add(padPanel1);
        add(padPanel2);
        add(padPanel3);
        add(padPanel4);
        add(padPanel5);
        add(padPanel6);
        add(padPanel7);
        add(padPanel8);



    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "+++":
                System.out.println("View detected +++");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        colorPanel(padPanel1);
                    }
                });
                break;
            case "++-":

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        colorPanel(padPanel2);
                    }
                });
                break;
            case "+-+":
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        colorPanel(padPanel3);
                    }
                });
                break;
            case "+--":
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        colorPanel(padPanel4);
                    }
                });
                break;
            case "-++":
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        colorPanel(padPanel5);
                    }
                });
                break;
            case "-+-":
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        colorPanel(padPanel6);
                    }
                });
                break;
            case "--+":
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        colorPanel(padPanel7);
                    }
                });
                break;
            case "---":
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        colorPanel(padPanel8);
                    }
                });
                break;
            default:
                break;
        }
    }

    public void colorPanel(JPanel panel) {
        panel.setBackground(new Color(57, 255, 20));

        final Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                final Color targetColor = new Color(64,64,64);
                final int changingSpeed = 5;

                final Color currentColor = panel.getBackground();

                // step 1
                int r = currentColor.getRed();
                int g = currentColor.getGreen();
                int b = currentColor.getBlue();

                // step 2
                double dr = targetColor.getRed() - r;
                double dg = targetColor.getGreen() - g;
                double db = targetColor.getBlue() - b;

                // step 3
                double norm = Math.sqrt(dr*dr+dg*dg+db*db);
                if (norm < .001) {
                    ((Timer)(evt.getSource())).stop();
                    return;
                }
                dr /= norm;
                dg /= norm;
                db /= norm;

                // step 4
                dr *= Math.min(changingSpeed, norm);
                dg *= Math.min(changingSpeed, norm);
                db *= Math.min(changingSpeed, norm);

                // step 5
                r += dr;
                g += dg;
                b += db;
                panel.setBackground(new Color(r,g,b));

                panel.repaint();
            }
        });
        timer.start();
    }
}
