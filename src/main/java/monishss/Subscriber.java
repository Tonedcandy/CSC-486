// Monish S. S., Megan W., Andrea Ng
package monishss;
import org.eclipse.paho.client.mqttv3.*;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class Subscriber implements MqttCallback {

    String broker="tcp://test.mosquitto.org:1883";
    String topic="Spotify";
    String clientID = "CalPoly-ProjectNameSub";
    MqttClient mqttClient;

    private StatusPanel statusPanel;

    public void connectToBroker() throws MqttException {
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
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost: " + throwable.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        Blackboard.getInstance().writeToBlackboard(message.toString());
        statusPanel.setCommandStatus(message.toString());
        statusPanel.repaint();
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    public void disconnectFromBroker() throws InterruptedException, MqttException {
        if (mqttClient != null && mqttClient.isConnected()) {
            mqttClient.disconnect();
        }
    }

    public Subscriber(StatusPanel statusPanel, String newBroker, String newTopic) throws InterruptedException {
        this.statusPanel = statusPanel;
        broker = newBroker;
        topic = newTopic;
    }
}
