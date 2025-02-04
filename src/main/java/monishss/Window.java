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
    private Publisher publisher;
    public Window() {
        super("CSC486 HCISE Publisher");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(new Color(0,0,0));


        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu serverMenu = new JMenu("Server");
        JMenu aboutMenu = new JMenu("About");



        JMenuItem loadFile = new JMenuItem("Load");
        JMenuItem startServer = new JMenuItem("Start");
        JMenuItem stopServer = new JMenuItem("Stop");



        ActionListener menuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                if (action.equals("Load")) {
                    System.out.println("Loading file...");
                    try {
                        publisher = new Publisher();

                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                        int result = fileChooser.showOpenDialog(Window.this);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                             fileAbsolutePath= selectedFile.getAbsolutePath();
                             publisher.readFromFile(fileAbsolutePath);

                        }


                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                if (action.equals("Start")){
                    System.out.println("Starting server...");
                    publisher.processCSV();
                }

                if (action.equals("Stop")){
                    System.out.println("Stopping server...");
                    try {
                        publisher.stopPublisher();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (MqttException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };

        loadFile.addActionListener(menuListener);
        startServer.addActionListener(menuListener);
        stopServer.addActionListener(menuListener);


        menuBar.add(fileMenu);
        menuBar.add(serverMenu);
        menuBar.add(aboutMenu);

        fileMenu.add(loadFile);

        serverMenu.add(startServer);
        serverMenu.add(stopServer);

        add(menuBar, BorderLayout.NORTH);

        add(new View());
        setVisible(true);

    }
}
