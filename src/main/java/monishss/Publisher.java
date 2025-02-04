// Monish S. S., Megan W., Andrea Ng
package monishss;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Publisher {
    String broker="tcp://test.mosquitto.org:1883";
    String topic="Spotify";
    String clientID = "CalPoly-ProjectNamePub";

    private String pad;
    private MqttClient client;
    private String combo;
    private List<String> combos;

    public void readFromFile(String filePath){
        String line;
        String csvDelimiter = ",";
        //Blackboard blackboard = Blackboard.getInstance();
        combos = new ArrayList<>();




        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                pad = "";
                String[] columns = line.split(csvDelimiter);

                pad+=columns[0]+columns[1]+columns[2];

                combos.add(pad);

                //blackboard.writeToBlackboard(pad);
                //blackboard.displayPADValues();

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Loaded CSV File");
    }

    public void processCSV() {
        if (combos.isEmpty()) {
            System.out.println("CSV is empty or could not be read.");
            return;
        }
        final int[] index = {0};  // mutable index for inner class

        // Create a Swing Timer that fires every 2000ms (2 seconds)
        Timer timer = new Timer(250, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index[0] < combos.size() && client.isConnected()) {
                    combo = combos.get(index[0]);
                    System.out.println("Processing combo: " + combo);
                    try {
                        // Write the combo to the blackboard, which should fire a property change event.
                        Blackboard.getInstance().writeToBlackboard(combo);
                        Blackboard.getInstance().displayPADValues();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    index[0]++;
                } else {
                    // All combos processed; stop the timer.
                    ((Timer) e.getSource()).stop();
                    System.out.println("All CSV rows processed.");
                }
                try {
                    publishMessage(combo);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        timer.start();
    }
    public Publisher() throws InterruptedException {
        try {
            client = new MqttClient(broker, clientID);
            client.connect();

            System.out.println(client.isConnected());
        }
        catch (MqttException e) {
            e.printStackTrace();
        }


    }

    public void publishMessage(String messageText) throws  InterruptedException {
        try {
            if (client != null && client.isConnected()) {
                MqttMessage message = new MqttMessage(messageText.getBytes());
                client.publish(topic, message);
                System.out.println("Published pad value: " + messageText);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }



    }

    public void stopPublisher() throws InterruptedException, MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
        }
    }
}
