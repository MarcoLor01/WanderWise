package com.example.wanderwisep.dao;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.DuplicateTourException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.model.Ticket;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.valueOf;

public class TicketDAOCSV implements TicketDAO {
    private File fd;
    private final static int INDEX_ID_TICKET = 0;
    private final static int INDEX_STATE = 1;
    private final static int INDEX_PRENOTATION_DATE = 2;
    private final static int INDEX_MY_GUIDED_TOUR = 3;
    private final static int INDEX_USER = 4;
    private final static int INDEX_TOURIST_GUIDE = 5;

    private HashMap<String, Ticket> localCache;
    private static final String CSV_FILE_NAME = "src/main/resources/com/example/wanderwisep/ticketDBlocal.csv";

    public TicketDAOCSV() throws IOException {
        this.fd = new File(CSV_FILE_NAME);

        if (!fd.exists()) {
            boolean fileCreated = fd.createNewFile();
            if (!fileCreated) {
                throw new IOException("Failed to create the file: " + CSV_FILE_NAME);
            }
        }

        this.localCache = new HashMap<>();
    }

    public void createTicket(String idTicket, stateEnum state, LocalDate prenotationDate, String myGuidedTour, String myTouristGuide, String user) throws DuplicateTourException, IOException, CsvValidationException {

        boolean duplicatedRecordId = false;
        synchronized (this.localCache) {
            duplicatedRecordId = (this.localCache.get(valueOf(idTicket)) != null);
        }

        if (!duplicatedRecordId) {
            try {
                List<Ticket> ticketsList = retrieveByIdTicket(this.fd, idTicket);
                duplicatedRecordId = (ticketsList.size() != 0);
            } catch (DAOException e) {
                duplicatedRecordId = false;
            }
        }

        if (duplicatedRecordId) {
            DuplicateTourException e = new DuplicateTourException(
                    "Duplicated Instance ID. Id " + idTicket + " was already assigned");
            throw e;
        }

        insertTicket(this.fd, idTicket, state, prenotationDate, myGuidedTour, myTouristGuide, user);
    }

    private static synchronized void insertTicket(File fd, String idTicket, stateEnum state, LocalDate prenotationDate, String myGuidedTour, String myTouristGuide, String user) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)))) {
            String[] ticketRecord = new String[6];
            ticketRecord[INDEX_ID_TICKET] = valueOf(idTicket);
            ticketRecord[INDEX_STATE] = stateEnum.toString(state);
            ticketRecord[INDEX_PRENOTATION_DATE] = prenotationDate.toString();
            ticketRecord[INDEX_MY_GUIDED_TOUR] = myGuidedTour;
            ticketRecord[INDEX_USER] = user;
            ticketRecord[INDEX_TOURIST_GUIDE] = myTouristGuide;
            csvWriter.writeNext(ticketRecord);
            csvWriter.flush();
        }
    }


    public List<Ticket> retrieveByIdTicket(File fd, String idTicket) throws DAOException, CsvValidationException, IOException {
        List<Ticket> ticketsList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                int posIndice = INDEX_ID_TICKET;
                boolean recordFound = record[posIndice].equals(valueOf(idTicket));
                if (recordFound) {
                    String id = record[INDEX_ID_TICKET];
                    String state = record[INDEX_STATE];
                    LocalDate prenotationDate = LocalDate.parse(record[INDEX_PRENOTATION_DATE]);
                    String myTouristGuide = record[INDEX_TOURIST_GUIDE];
                    String myGuidedTour = record[INDEX_MY_GUIDED_TOUR];
                    String user = record[INDEX_USER];
                    Ticket ticket = new Ticket(id, stateEnum.fromString(state), prenotationDate, myGuidedTour, myTouristGuide, user);
                    ticketsList.add(ticket);
                }
            }
        }
        if (ticketsList.isEmpty()) {
            throw new DAOException("No Ticket Found matching with ID: " + idTicket);
        }
        return ticketsList;
    }


    public List<Ticket> retrieveTicket(String touristGuideName) throws SQLException, TicketNotFoundException {
        return null;
    }
}