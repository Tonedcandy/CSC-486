// Monish S. S., Megan W., Andrea Ng
package monishss;

import org.eclipse.paho.client.mqttv3.MqttException;

import javax.swing.*;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    String fileAbsolutePath;
    private StatusPanel statusPanel;
    private String url = "tcp://test.mosquitto.org:1883";
    private String topic = "Spotify";
    Subscriber subscriber;

    public Window() {
        super("CSC486 HCISE Subscriber");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(new Color(0,0,0));

        statusPanel = new StatusPanel();
        Subscriber subscriber = new Subscriber(statusPanel);


        JMenuBar menuBar = new JMenuBar();

        JMenu serverMenu = new JMenu("Server");
        JMenu helpMenu = new JMenu("Help");
        JMenu settingsMenu = new JMenu("Settings");



        JMenuItem startServer = new JMenuItem("Start");
        JMenuItem stopServer = new JMenuItem("Stop");
        JMenuItem about = new JMenuItem("About");
        JMenuItem changeURL = new JMenuItem("Change URL");
        JMenuItem changeTopic = new JMenuItem("Change Topic");



        ActionListener menuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();

                if (action.equals("Start")){
                    System.out.println("Starting server...");
                    subscriber.connectToBroker();
                    //subscriber.processCSV();
                    statusPanel.setConnectionStatus("Connected to MQTT Broker");
                }

                if (action.equals("Stop")){
                    System.out.println("Stopping server...");
                    try {
                        subscriber.disconnectFromBroker();
                        statusPanel.setConnectionStatus("Disconnected from MQTT Broker");
                    } catch (MqttException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (action.equals("About")){
                    JDialog dialog = new JDialog(Window.this, "About", true);

                    dialog.setLayout(new BorderLayout());

                    JLabel label = new JLabel("<html>This implements a Graphical User Interface (GUl) to enhance usability and functionality and follow Nielsen's Usability Heuristics. Developed By Monish S. S., Megan Waller and Andrea Ng.</html>", SwingConstants.CENTER);
                    dialog.add(label, BorderLayout.CENTER);


                    JPanel buttonPanel = new JPanel();
                    JButton closeButton = new JButton("Close");
                    closeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.dispose();
                        }
                    });
                    buttonPanel.add(closeButton);

                    dialog.add(buttonPanel, BorderLayout.SOUTH);

                    dialog.setSize(300, 300);
                    dialog.setLocationRelativeTo(Window.this);
                    dialog.setVisible(true);


                }
                if (action.equals("Change URL")){
                    String message = String.format("Current URL: %s", url);
                    String input = JOptionPane.showInputDialog(message + "\nPlease input a valid URL value.");
                    if (input == null){
                        JOptionPane.showMessageDialog(Window.this, "Invalid URL value.");
                    }
                    else{
                        url = input;
                    }
                }
                if (action.equals("Change Topic")){
                    String message = String.format("Current Topic: %s", topic);
                    String input = JOptionPane.showInputDialog(message + "\nPlease input a topic name.");
                    if (input == null){
                        JOptionPane.showMessageDialog(Window.this, "Invalid topic value.");
                    }
                    else{
                        topic = input;
                    }
                }

            }
        };

        startServer.addActionListener(menuListener);
        stopServer.addActionListener(menuListener);
        about.addActionListener(menuListener);
        changeURL.addActionListener(menuListener);
        changeTopic.addActionListener(menuListener);

        menuBar.add(serverMenu);
        menuBar.add(helpMenu);
        menuBar.add(settingsMenu);


        serverMenu.add(startServer);
        serverMenu.add(stopServer);

        helpMenu.add(about);

        settingsMenu.add(changeURL);
        settingsMenu.add(changeTopic);

        add(menuBar, BorderLayout.NORTH);

        add(new View(), BorderLayout.CENTER);
        add(statusPanel,BorderLayout.SOUTH);
        setVisible(true);

    }
}
