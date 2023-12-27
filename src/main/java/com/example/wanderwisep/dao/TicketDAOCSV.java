package com.example.wanderwisep.dao;

import com.example.wanderwisep.model.Ticket;
import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TicketDAOCSV implements TicketDAO {
    private File fd;

    private final static int INDEX_ID_TICKET = 0;
    private final static int INDEX_STATE = 1;
    private final static int INDEX_PRENOTATION_DATE = 2;
    private final static int INDEX_MY_GUIDED_TOUR = 3;
    private final static int INDEX_USER = 4;
    //INSERISCI GUIDA
    //   private HashMap<String, Ticket> localCache;
    private static final String CSV_FILE_NAME = "src/main/resources/com/example/wanderwisep/ticketDBlocal.csv";

    public TicketDAOCSV() throws IOException {
        this.fd = new File(CSV_FILE_NAME);

        if (!fd.exists()) {
            fd.createNewFile();
        }

        //       this.localCache = new HashMap<String, Ticket>();
    }

    int id = 1;

    public void createTicket(String tourName, String user, LocalDate prenotationDate, String stateTicket) throws IOException {

        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)));
        String[] ticketRecord = new String[5];
        ticketRecord[INDEX_ID_TICKET] = String.valueOf(id);
        ticketRecord[INDEX_STATE] = stateTicket;
        ticketRecord[INDEX_PRENOTATION_DATE] = prenotationDate.toString();
        ticketRecord[INDEX_MY_GUIDED_TOUR] = tourName;
        ticketRecord[INDEX_USER] = user;
//        ticketRecord[INDEX_GUIDE] = guideName;
        csvWriter.writeNext(ticketRecord);
        csvWriter.flush();
        csvWriter.close();
        id++;
    }

    @Override
    public List<Ticket> retrieveTicket(String touristGuideName) {
        return null;
    }
}
