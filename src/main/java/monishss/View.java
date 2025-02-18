// Monish S. S., Megan W., Andrea Ng
package monishss;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Timer;
import java.awt.*;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JPanel implements ActionListener {
    private Blackboard blackboard;
    private LinkedList<Integer> NumList;
    private Integer Length;
    private Integer randomNum;
    private Integer current;
    private JLabel Displayed;
    private JTextField Input;
    private JButton SubmitButton;
    private String test;
    private String mode;
    private Integer strike;

    public View() {
        blackboard = Blackboard.getInstance();
        NumList = new LinkedList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
        Length = 3;
        randomNum = ThreadLocalRandom.current().nextInt(0, 10);
        test = "numberSpan";
        mode = "forward";
        strike = 0;

        Displayed = new JLabel("Digit Span Test", SwingConstants.CENTER);
        blackboard.setDisplay("Digit Span Test");
        Displayed.setForeground(new Color(0,0,0));
        Displayed.setFont(new Font("Verdana", Font.PLAIN, 50));
        Displayed.setAlignmentX(Component.CENTER_ALIGNMENT);
        Displayed.setBorder(BorderFactory.createEmptyBorder());

        Input = new JTextField();
        Input.setForeground(new Color(125,125,125));
        Input.setFont(new Font("Verdana", Font.PLAIN, 50));
        Input.setEnabled(false);
        Input.setAlignmentX(Component.CENTER_ALIGNMENT);
        Input.setBorder(BorderFactory.createEmptyBorder());
        Input.setBorder(BorderFactory.createCompoundBorder(
                Input.getBorder(),
                BorderFactory.createEmptyBorder(50, 50, 50, 50)));

        SubmitButton = new JButton("Start");
        SubmitButton.setActionCommand("Submit");
        SubmitButton.addActionListener(this);
        SubmitButton.setEnabled(true);
        SubmitButton.setFont(new Font("Verdana", Font.PLAIN, 25));
        SubmitButton.setMaximumSize(new Dimension(200, 50));
        SubmitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        SubmitButton.setFocusPainted(false);
        SubmitButton.setMnemonic(KeyEvent.VK_ACCEPT);

        add(Displayed);
        add(Input);
        add(SubmitButton);
        setBorder(BorderFactory.createCompoundBorder(
                this.getBorder(),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
    }

    // ADMINISTER NUMBER SPAN TEST
    public void ShowNumbers(){
        if (NumList != null){
            NumList.clear();}
        Input.setEnabled(false);
        Timer timer = new Timer();
        current = 0;
        TimerTask task = new TimerTask() {
            public void run()
            {
                current ++;
                if (current > Length){
                    if (Objects.equals(mode, "forward")) {
                        Displayed.setText("Enter the sequence.");
                        blackboard.setDisplay("Enter the sequence.");
                    }
                    else{
                        Displayed.setText("Reverse the sequence.");
                        blackboard.setDisplay("Reverse the sequence.");
                    }
                    System.out.println("Waiting for input...");
                    timer.cancel();
                    SubmitButton.setEnabled(true);
                    Input.setEnabled(true);
                    return;
                }
                Integer temp = randomNum;
                do {
                    randomNum = ThreadLocalRandom.current().nextInt(0, 10);
                } while (Objects.equals(temp, randomNum));
                NumList.add(randomNum);
                System.out.println(randomNum);
                Displayed.setText(randomNum.toString());
                blackboard.setDisplay(randomNum.toString());
            };
        };
        timer.schedule(task, 1000, 1000);
    }

    // CHECK PLAYER INPUT/START
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Submit".equals(e.getActionCommand())) {
            if ((Objects.equals(SubmitButton.getText(), "Submit"))){
                if (Objects.equals(test, "numberSpan")){
                    SubmitButton.setEnabled(false);
                    StringBuilder correct = new StringBuilder();
                    String answer = Input.getText();
                    blackboard.setPlayerInput(answer);
                    Input.setText("");
                    Input.setEnabled(false);
                    for (int i = 0; i < NumList.size(); i++) {
                        correct.append(NumList.get(i).toString());
                    }
                    String correctString = correct.toString();
                    if (Objects.equals(mode, "backward")){
                        correctString = correct.reverse().toString();
                    }
                    if (Objects.equals(answer, correctString)){
                        // SET NEW SCORE
                        Displayed.setText("Correct! Current score: " + Length.toString());
                        blackboard.setDisplay("Correct! Current score: " + Length.toString());
                        blackboard.setScore(Length);
                        // INCREASE LENGTH OF NEXT
                        Length ++;
                        SubmitButton.setText("Continue");
                    }
                    else{
                        // DISPLAY CURRENT SCORE
                        Displayed.setText("Incorrect. Current score: " + blackboard.getScore());
                        blackboard.setDisplay("Incorrect. Current score: " + blackboard.getScore());
                        // ONE RETRY
                        if (test.equals("numberSpan") & (strike == 0)) {
                            strike ++;
                            SubmitButton.setText("Retry");
                        }
                        else{
                            // START REVERSE SEQUENCE
                            if (test.equals("numberSpan") & mode.equals("forward")) {
                                Length = 3;
                                mode = "backward";
                                blackboard.setScore(0);
                                SubmitButton.setText("Start Next");
                            }
                            else{
                                SubmitButton.setText("Retry");
                            }
                        }
                    }
                    SubmitButton.setEnabled(true);
                }
            }
            else {
                SubmitButton.setText("Submit");
                SubmitButton.setEnabled(false);
                if (Objects.equals(test, "numberSpan")) {
                    ShowNumbers();
                }
            }
        } else {
            System.out.println("hi");
        }
    }
}
