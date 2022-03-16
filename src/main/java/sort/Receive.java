package sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class Receive {

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "message", containerFactory = "myFactory")
    public void receiveMessage(Message message) {
        System.out.println(message);
        writeInFile(message);
    }

    private void writeInFile(Message message){
        File file = new File("RESULT.txt");

        FileWriter fileWriter;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            fileWriter = new FileWriter(file,true);
            fileWriter.write(message.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
