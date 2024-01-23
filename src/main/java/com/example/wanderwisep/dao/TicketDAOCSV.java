package com.example.wanderwisep.dao;

import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.*;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.pattern.GuidedTourDAOFactory;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class TicketDAOCSV extends TicketDAO {
    private static Integer indexIdTicket = 0;
    private static Integer indexState = 1;
    private static Integer indexPrenotationDate = 2;
    private static Integer indexUser = 3;
    private static Integer indexTouristGuide = 4;
    private static Integer indexMyGuidedTourId = 5;
    private static Integer indexDepartureDate = 6;
    private static Integer indexReturnDate = 7;
    private static Integer indexNameTour = 8;
    Logger logger = Logger.getLogger(TicketDAOCSV.class.getName());
    private static final String CSV_FILE_NAME = "src/main/resources/com/example/wanderwisep/ticketDBlocal.csv";
    private File fd;

    public TicketDAOCSV() throws IOException {
        this.fd = new File(CSV_FILE_NAME);
        if (!fd.exists()) {
            boolean fileCreated = fd.createNewFile();
            if (fileCreated) {
                logger.log(INFO, "File created: " + CSV_FILE_NAME);
            } else {
                logger.log(INFO, "File already exists: " + CSV_FILE_NAME);
            }
        }
    }


    @Override
    public void createTicket(Ticket ticket) throws DAOException, SQLException, IOException, DuplicateTourException, CsvValidationException, TourException, TouristGuideNotFoundException {

        try {
            List<Ticket> ticketsList = retrieveByIdTicket(this.fd, ticket.getIdTicket());
            if (!ticketsList.isEmpty()) {
                throw new DuplicateTourException("Tour already booked");
            }
        } catch (TicketNotFoundException e) {
            logger.log(INFO, e.getMessage());
        }
        insertTicket(this.fd, ticket);
    }

    private static synchronized void insertTicket(File fd, Ticket ticket) throws IOException {

        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)))) { //Security Hotspot
            String[] ticketRecord = new String[9];
            ticketRecord[indexIdTicket] = ticket.getIdTicket();
            ticketRecord[indexState] = ticket.getState().getStateName();
            ticketRecord[indexPrenotationDate] = String.valueOf(ticket.getPrenotationDate());
            ticketRecord[indexUser] = ticket.getUser();
            ticketRecord[indexMyGuidedTourId] = ticket.getMyGuidedTour().getGuidedTourId();
            csvWriter.writeNext(ticketRecord);
            csvWriter.flush();
        }

    }

    private static synchronized List<Ticket> retrieveByIdTicket(File fd, String idTicket) throws IOException, CsvValidationException, TicketNotFoundException, SQLException, TourException, TouristGuideNotFoundException {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] recordToTake;
            List<Ticket> ticketList = new ArrayList<>();

            while ((recordToTake = csvReader.readNext()) != null) {
                boolean recordFound = recordToTake[indexIdTicket].equals(String.valueOf(idTicket));
                if (recordFound) {
                    String id = String.valueOf(recordToTake[indexIdTicket]);
                    stateEnum state = stateEnum.fromString(recordToTake[indexState]);
                    LocalDate prenotationDate = LocalDate.parse(String.valueOf(recordToTake[indexPrenotationDate]));
                    String user = String.valueOf(recordToTake[indexUser]);
                    String guidedTourId = String.valueOf(recordToTake[indexMyGuidedTourId]);
                    GuidedTourDAOFactory guidedTourDAOFactory = new GuidedTourDAOFactory();
                    GuidedTour guidedTour = guidedTourDAOFactory.createGuidedTourDAO(1).retrieveTourFromId(guidedTourId);
                    Ticket ticket = new Ticket(id, state, prenotationDate, guidedTour, user);
                    ticketList.add(ticket);
                }
            }

            if (ticketList.isEmpty()) {
                throw new TicketNotFoundException("No Ticket founded with ticket id: " + idTicket);
            }

            return ticketList;
        }
    }


    @Override
    public List<Ticket> retrieveTicket(String userEmail) throws SQLException, TicketNotFoundException, CsvValidationException, IOException, TourException, TouristGuideNotFoundException {
        return retrieveByUserName(this.fd, userEmail);
    }

    private static synchronized List<Ticket> retrieveByUserName(File fd, String userName) throws IOException, CsvValidationException, TicketNotFoundException, SQLException, TourException, TouristGuideNotFoundException {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] recordToTake;

            List<Ticket> ticketList = new ArrayList<>();

            while ((recordToTake = csvReader.readNext()) != null) {
                boolean recordFound = recordToTake[indexUser].equals(userName);
                if (recordFound) {
                    String id = String.valueOf(recordToTake[indexIdTicket]);
                    stateEnum state = stateEnum.fromString(recordToTake[indexState]);
                    LocalDate prenotationDate = LocalDate.parse(String.valueOf(recordToTake[indexPrenotationDate]));
                    String user = String.valueOf(recordToTake[indexUser]);
                    String guidedTourId = String.valueOf(recordToTake[indexMyGuidedTourId]);
                    GuidedTourDAOFactory guidedTourDAOFactory = new GuidedTourDAOFactory();
                    GuidedTour guidedTour = guidedTourDAOFactory.createGuidedTourDAO(1).retrieveTourFromId(guidedTourId);
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

    private static synchronized List<Ticket> retrieveTicketFromTourGuideName(File fd, String touristGuideName) throws IOException, CsvValidationException, TicketNotFoundException, SQLException, TourException, TouristGuideNotFoundException {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] recordToTake;

            List<Ticket> ticketList = new ArrayList<>();

            while ((recordToTake = csvReader.readNext()) != null) {
                boolean recordFound = recordToTake[indexTouristGuide].equals(touristGuideName) && recordToTake[indexState].equals("Waiting for confirmation");
                if (recordFound) {
                    String id = String.valueOf(recordToTake[indexIdTicket]);
                    stateEnum state = stateEnum.fromString(recordToTake[indexState]);
                    LocalDate prenotationDate = LocalDate.parse(String.valueOf(recordToTake[indexPrenotationDate]));
                    String user = String.valueOf(recordToTake[indexUser]);
                    String guidedTourId = String.valueOf(recordToTake[indexMyGuidedTourId]);
                    GuidedTourDAOFactory guidedTourDAOFactory = new GuidedTourDAOFactory();
                    GuidedTour guidedTour = guidedTourDAOFactory.createGuidedTourDAO(1).retrieveTourFromId(guidedTourId);
                    Ticket ticket = new Ticket(id, state, prenotationDate, guidedTour, user);
                    ticketList.add(ticket);
                }
            }

            if (ticketList.isEmpty()) {
                throw new TicketNotFoundException("No Ticket Found matching with name: " + touristGuideName);
            }

            return ticketList;
        }
    }


    public void retrieveTicketFromTourGuide(String userEmail, String idTour, String decision) throws CsvValidationException, IOException {
        retrieveFromTourGuide(this.fd, userEmail, idTour, decision);
    }

    public void retrieveFromTourGuide(File fd, String userEmail, String idTour, String decision) throws CsvValidationException, IOException {
        boolean recordUpdated = false;

        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            List<String[]> records = new ArrayList<>();
            String[] recordToTake;

            while ((recordToTake = csvReader.readNext()) != null) {
                boolean recordFound = recordToTake[indexUser].equals(userEmail) &&
                        recordToTake[indexMyGuidedTourId].equals(idTour) &&
                        recordToTake[indexState].equals("Waiting for confirmation");

                if (recordFound) {
                    recordToTake[indexState] = decision;
                    recordUpdated = true;
                    records.add(recordToTake);
                }

            }
            if (recordUpdated) {
                try (CSVWriter csvWriter = new CSVWriter(new FileWriter(fd, false))) {
                    csvWriter.writeAll(records);
                }
            }
        }
    }
}