// Monish S. S., Megan W., Andrea Ng
package monishss;
import org.eclipse.paho.client.mqttv3.*;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class Subscriber implements MqttCallback {

    String broker="tcp://test.mosquitto.org:1883";
    String topic="javiergs/tobii/gazedata";
    String clientID = "CalPoly-ProjectNameSub";
    private MqttClient mqttClient;
    private StatusPanel statusPanel;
    public Subscriber(StatusPanel statusPanel, String broker, String topic) {
    this.statusPanel = statusPanel;
    this.broker = broker;
    this.topic = topic;

    }
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost: " + throwable.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        statusPanel.setCommandStatus(message.toString());
        statusPanel.repaint();
        Blackboard.getInstance().addValue(message.toString());

        // parse  JSON data
        JSONObject jsonData = new JSONObject(data);

        double leftX = jsonData.getJSONObject("left_eye").getDouble("x");
        double leftY = jsonData.getJSONObject("left_eye").getDouble("y");
        double leftPupil = jsonData.getJSONObject("left_eye").getDouble("pupil");

        double rightX = jsonData.getJSONObject("right_eye").getDouble("x");
        double rightY = jsonData.getJSONObject("right_eye").getDouble("y");
        double rightPupil = jsonData.getJSONObject("right_eye").getDouble("pupil");

        // Create string to represent  data
        String gazeData = "Left Eye - X: " + leftX + ", Y: " + leftY + ", Pupil: " + leftPupil +
                          " | Right Eye - X: " + rightX + ", Y: " + rightY + ", Pupil: " + rightPupil;

        // Save data to a text file (gaze_data.txt)
        try (FileWriter file = new FileWriter("gaze_data.txt", true)) {
            file.write(gazeData + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // send  gaze data to the Blackboard???
        Blackboard.getInstance().addValue("mqttMessage", gazeData.toString());
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }


    public void connectToBroker() {
        try {
            mqttClient = new MqttClient(broker, clientID);
            mqttClient.setCallback(this);
            mqttClient.connect();
            mqttClient.subscribe(topic);


        }
        catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnectFromBroker() throws MqttException {
        if (mqttClient.isConnected()){
            mqttClient.disconnect();
        }
    }
}