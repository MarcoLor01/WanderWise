package com.example.wanderwisep.dao;

import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.DuplicateTicketException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketDAOCSV extends TicketDAO {
    private static final Integer INDEX_ID_TICKET = 0;
    private static final Integer INDEX_STATE = 1;
    private static final Integer INDEX_PRENOTATION_DATE = 2;
    private static final Integer INDEX_USER = 3;
    private static final Integer INDEX_MY_GUIDED_TOUR_ID = 4;
    private final Logger logger = Logger.getLogger(TicketDAOCSV.class.getName());
    private static final String CSV_FILE_NAME = "src/main/resources/com/example/wanderwisep/ticketDBlocal.csv";
    private final File fd;

    public TicketDAOCSV() throws IOException {
        this.fd = new File(CSV_FILE_NAME);
        if (!fd.exists()) {
            boolean fileCreated = fd.createNewFile();
            if (fileCreated) {
                logger.log(Level.INFO, "File created: " + CSV_FILE_NAME);
            } else {
                logger.log(Level.INFO, "File already exists: " + CSV_FILE_NAME);
            }
        }
    }


    @Override
    public void createTicket(Ticket ticket) throws SQLException, IOException, DuplicateTicketException, CsvValidationException, TourNotFoundException, TouristGuideNotFoundException, TicketNotFoundException {

        List<Ticket> ticketsList = retrieveByIdTicket(this.fd, ticket.getIdTicket());
        if (!ticketsList.isEmpty()) {
            throw new DuplicateTicketException("Tour already booked");
        }
        insertTicket(this.fd, ticket);
    }

    private static synchronized void insertTicket(File fd, Ticket ticket) throws IOException {

        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)))) { //Security Hotspot
            String[] ticketRecord = new String[5];
            ticketRecord[INDEX_ID_TICKET] = ticket.getIdTicket();
            ticketRecord[INDEX_STATE] = ticket.getState().getStateName();
            ticketRecord[INDEX_PRENOTATION_DATE] = String.valueOf(ticket.getPrenotationDate());
            ticketRecord[INDEX_USER] = ticket.getUser();
            ticketRecord[INDEX_MY_GUIDED_TOUR_ID] = ticket.getMyGuidedTour().getGuidedTourId();
            csvWriter.writeNext(ticketRecord);
            csvWriter.flush();
        }
    }

    private synchronized List<Ticket> retrieveByIdTicket(File fd, String idTicket) throws IOException, CsvValidationException, SQLException, TourNotFoundException, TouristGuideNotFoundException {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] recordToTake;
            List<Ticket> ticketList = new ArrayList<>();

            while ((recordToTake = csvReader.readNext()) != null) {
                boolean recordFound = recordToTake[INDEX_ID_TICKET].equals(String.valueOf(idTicket));
                if (recordFound) {
                    String id = String.valueOf(recordToTake[INDEX_ID_TICKET]);
                    stateEnum state = stateEnum.fromString(recordToTake[INDEX_STATE]);
                    LocalDate prenotationDate = LocalDate.parse(String.valueOf(recordToTake[INDEX_PRENOTATION_DATE]));
                    String user = String.valueOf(recordToTake[INDEX_USER]);
                    String guidedTourId = String.valueOf(recordToTake[INDEX_MY_GUIDED_TOUR_ID]);
                    GuidedTourDAOJDBC guidedTourDAOJDBC = new GuidedTourDAOJDBC();
                    GuidedTour guidedTour = guidedTourDAOJDBC.retrieveTourFromId(guidedTourId);
                    Ticket ticket = new Ticket(id, state, prenotationDate, guidedTour, user);
                    ticketList.add(ticket);
                }
            }

            return ticketList;
        }
    }


    @Override
    public List<Ticket> retrieveTicket(String userEmail) throws SQLException, TicketNotFoundException, CsvValidationException, IOException, TourNotFoundException, TouristGuideNotFoundException {
        return retrieveByUserName(this.fd, userEmail);
    }

    private synchronized List<Ticket> retrieveByUserName(File fd, String userName) throws IOException, CsvValidationException, TicketNotFoundException, SQLException, TourNotFoundException, TouristGuideNotFoundException {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] recordToTake;

            List<Ticket> ticketList = new ArrayList<>();

            while ((recordToTake = csvReader.readNext()) != null) {
                boolean recordFound = recordToTake[INDEX_USER].equals(userName);
                if (recordFound) {
                    String id = String.valueOf(recordToTake[INDEX_ID_TICKET]);
                    stateEnum state = stateEnum.fromString(recordToTake[INDEX_STATE]);
                    LocalDate prenotationDate = LocalDate.parse(String.valueOf(recordToTake[INDEX_PRENOTATION_DATE]));
                    String user = String.valueOf(recordToTake[INDEX_USER]);
                    String guidedTourId = String.valueOf(recordToTake[INDEX_MY_GUIDED_TOUR_ID]);
                    GuidedTourDAOJDBC guidedTourDAOJDBC = new GuidedTourDAOJDBC();
                    GuidedTour guidedTour = guidedTourDAOJDBC.retrieveTourFromId(guidedTourId);
                    Ticket ticket = new Ticket(id, state, prenotationDate, guidedTour, user);
                    ticketList.add(ticket);
                }
            }

            if (ticketList.isEmpty()) {
                throw new TicketNotFoundException("No Ticket Found matching with name: " + userName);
            }

            return ticketList;
        }
    }

    public void modifyTicketState(String userEmail, String idTour, String decision) throws CsvValidationException, IOException, TicketNotFoundException {
        retrieveFromTourGuide(this.fd, userEmail, idTour, decision);
    }

    public void retrieveFromTourGuide(File fd, String userEmail, String idTour, String decision) throws CsvValidationException, IOException, TicketNotFoundException {
        boolean recordUpdated = false;

        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            List<String[]> records = new ArrayList<>();
            String[] recordToTake;

            while ((recordToTake = csvReader.readNext()) != null) {
                boolean recordFound = recordToTake[INDEX_USER].equals(userEmail) &&
                        recordToTake[INDEX_MY_GUIDED_TOUR_ID].equals(idTour) &&
                        recordToTake[INDEX_STATE].equals("Waiting for confirmation");

                if (recordFound) {
                    recordToTake[INDEX_STATE] = decision;
                    recordUpdated = true;
                    records.add(recordToTake);
                }

            }
            if (recordUpdated) {
                try (CSVWriter csvWriter = new CSVWriter(new FileWriter(fd, false))) {
                    csvWriter.writeAll(records);
                }
            } else {
                throw new TicketNotFoundException("No ticket available");
            }
        }
    }
}