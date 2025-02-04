// Monish S. S., Megan W., Andrea Ng
package monishss;
import org.eclipse.paho.client.mqttv3.*;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class Subscriber implements MqttCallback {

    String broker="tcp://test.mosquitto.org:1883";
    String topic="Spotify";
    String clientID = "CalPoly-ProjectNameSub";

    ArrayList<String> req = new ArrayList<String>();

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

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Inside Subscriber: "+message.toString());

        // String Array List is limited to 20 words for convenience. Arraylist is cleared when it reaches 21 words

        if (req.size()<=20) {
            req.add(message.toString());
        }
        else{
            System.out.println("Data Structure is Full, clearing it");
            req.clear();
        }
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }


    public static void main(String[] args) throws MqttException {
        Subscriber subscriber = new Subscriber();
    }
}
