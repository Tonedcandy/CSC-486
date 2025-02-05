// Monish S. S., Megan W., Andrea Ng
package monishss;
import org.eclipse.paho.client.mqttv3.*;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class Subscriber implements MqttCallback {

    String broker="tcp://test.mosquitto.org:1883";
    String topic="Spotify";
    String clientID = "CalPoly-ProjectNameSub";

    public Subscriber() {
        try {
            MqttClient mqttClient = new MqttClient(broker, clientID);
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

        Blackboard.getInstance().addValue("mqttMessage: ", message.toString());

    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }


    public static void main(String[] args) throws MqttException {
        Subscriber subscriber = new Subscriber();
    }
}
