package com.example.wanderwisep.boundary;

import com.example.wanderwisep.bean.EmailBean;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailBookTourBoundary {

    String filePath = "src/main/resources/com/example/wanderwisep/email.txt";
    Logger logger = Logger.getLogger(EmailBookTourBoundary.class.getName());

    public void initializeEmail(EmailBean emailBean) {

        String guideDecision = "";

        if (Objects.equals(emailBean.getDecision(), "Confirmed")) {
            guideDecision = "accept";
        } else if (Objects.equals(emailBean.getDecision(), "Refused")) {
            guideDecision = "refuse";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("*************************************\n");
            writer.write("Email sent from tourist guide: " + emailBean.getTouristGuideEmail() + "\n");
            writer.write("Email sent to user: " + emailBean.getUserEmail() + "\n");
            writer.write("*************************************\n");
            writer.write("We inform you that the tourist guide has decided to " + guideDecision + " your request\nfor the booking of the guided tour: " + emailBean.getGuidedTourName());
            writer.write("\n-----------------------------------\n");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
