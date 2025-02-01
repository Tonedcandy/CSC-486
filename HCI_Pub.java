// Monish S. S., Megan W., Andrea Ng
package monishss;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class Publisher {
    String broker="tcp://test.mosquitto.org:1883";
    String topic="Spotify";
    String clientID = "CalPoly-ProjectNamePub";
    public Publisher() throws InterruptedException {

        try {
            MqttClient client = new MqttClient(broker, clientID);
            client.connect();

            System.out.println(client.isConnected());

            while(true) {
                if (client.isConnected()) {

                    String ms = "Please Keep Lab Computers On";

                    String[] spl = ms.split(" ");

                    // Sending hardcoded string word by word through a loop

                    for(String s : spl) {
                        MqttMessage message = new MqttMessage(s.getBytes());
                        client.publish(topic, message);
                    }


                }

                Thread.sleep(1000);
            }


        }
        catch ( MqttException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
            Publisher publisher = new Publisher();
    }
}
