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