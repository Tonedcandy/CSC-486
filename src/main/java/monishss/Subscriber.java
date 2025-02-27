// Monish S. S., Megan W., Andrea Ng
package monishss;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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

        // parse  JSON data
        JSONObject jsonData = new JSONObject();

        double leftEyeGazeX = jsonData.getJSONObject("leftEyeGaze").getDouble("x");
        double leftEyeGazeY = jsonData.getJSONObject("leftEyeGaze").getDouble("y");
        double leftEyeGazeZ = jsonData.getJSONObject("leftEyeGaze").getDouble("z");

        double rightEyeGazeX = jsonData.getJSONObject("rightEyeGaze").getDouble("x");
        double rightEyeGazeY = jsonData.getJSONObject("rightEyeGaze").getDouble("y");
        double rightEyeGazeZ = jsonData.getJSONObject("rightEyeGaze").getDouble("z");

        double eyeFixationPointX = jsonData.getJSONObject("eyeFixationPoint").getDouble("x");
        double eyeFixationPointY = jsonData.getJSONObject("eyeFixationPoint").getDouble("y");
        double eyeFixationPointZ = jsonData.getJSONObject("eyeFixationPoint").getDouble("z");

        double headX = jsonData.getJSONObject("head").getDouble("x");
        double headY = jsonData.getJSONObject("head").getDouble("y");
        double headZ = jsonData.getJSONObject("head").getDouble("z");

        double leftArmUpX = jsonData.getJSONObject("leftArmUp").getDouble("x");
        double leftArmUpY = jsonData.getJSONObject("leftArmUp").getDouble("y");
        double leftArmUpZ = jsonData.getJSONObject("leftArmUp").getDouble("z");

        double leftArmLowX = jsonData.getJSONObject("lefArmLow").getDouble("x");
        double leftArmLowY = jsonData.getJSONObject("lefArmLow").getDouble("y");
        double leftArmLowZ = jsonData.getJSONObject("lefArmLow").getDouble("z");

        double rightArmUpX = jsonData.getJSONObject("rightArmUp").getDouble("x");
        double rightArmUpY = jsonData.getJSONObject("rightArmUp").getDouble("y");
        double rightArmUpZ = jsonData.getJSONObject("rightArmUp").getDouble("z");

        double rightArmLowX = jsonData.getJSONObject("rightArmLow").getDouble("x");
        double rightArmLowY = jsonData.getJSONObject("rightArmLow").getDouble("y");
        double rightArmLowZ = jsonData.getJSONObject("rightArmLow").getDouble("z");

        Map<String, Double> map = new HashMap<String, Double>();
        map.put("leftEyeGazeX", leftEyeGazeX);
        map.put("leftEyeGazeY", leftEyeGazeY);
        map.put("leftEyeGazeZ", leftEyeGazeZ);

        map.put("rightEyeGazeX", rightEyeGazeX);
        map.put("rightEyeGazeY", rightEyeGazeY);
        map.put("rightEyeGazeZ", rightEyeGazeZ);

        map.put("eyeFixationPointX", eyeFixationPointX);
        map.put("eyeFixationPointY", eyeFixationPointY);
        map.put("eyeFixationPointZ", eyeFixationPointZ);

        map.put("headX", headX);
        map.put("headY", headY);
        map.put("headZ", headZ);

        map.put("leftArmUpX", leftArmUpX);
        map.put("leftArmUpY", leftArmUpY);
        map.put("leftArmUpZ", leftArmUpZ);

        map.put("leftArmLowX", leftArmLowX);
        map.put("leftArmLowY", leftArmLowY);
        map.put("leftArmLowZ", leftArmLowZ);

        map.put("rightArmUpX", rightArmUpX);
        map.put("rightArmUpY", rightArmUpY);
        map.put("rightArmUpZ", rightArmUpZ);

        map.put("rightArmLowX", rightArmLowX);
        map.put("rightArmLowY", rightArmLowY);
        map.put("rightArmLowZ", rightArmLowZ);

        // send gaze data to the Blackboard
        Blackboard.getInstance().addValue(map);
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