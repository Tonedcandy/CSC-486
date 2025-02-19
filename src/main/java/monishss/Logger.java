package monishss;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private String fileName;
    public Logger(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatter);
        fileName = "cognitive_test_data_" + timestamp + ".csv";
    }

    public void logData(String data){
        try (
                FileWriter file = new FileWriter(fileName, true)) {
            file.write(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
