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
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class TicketDAOCSV implements TicketDAO {
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
    public void createTicket(Ticket ticket) throws DAOException, SQLException, IOException, DuplicateTourException, CsvValidationException {

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
            ticketRecord[indexTouristGuide] = ticket.getMyTouristGuide();
            ticketRecord[indexMyGuidedTourId] = ticket.getMyGuidedTourId();
            ticketRecord[indexDepartureDate] = String.valueOf(ticket.getDepartureDate());
            ticketRecord[indexReturnDate] = String.valueOf(ticket.getReturnDate());
            ticketRecord[indexNameTour] = String.valueOf(ticket.getTourName());
            csvWriter.writeNext(ticketRecord);
            csvWriter.flush();
        }

    }

    private static synchronized List<Ticket> retrieveByIdTicket(File fd, String idTicket) throws IOException, CsvValidationException, TicketNotFoundException {
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
                    String touristGuide = String.valueOf(recordToTake[indexTouristGuide]);
                    String guidedTour = String.valueOf(recordToTake[indexMyGuidedTourId]);
                    LocalDate departureDate = LocalDate.parse(String.valueOf(recordToTake[indexDepartureDate]));
                    LocalDate returnDate = LocalDate.parse(String.valueOf(recordToTake[indexReturnDate]));
                    String nameTour = String.valueOf(recordToTake[indexNameTour]);
                    Ticket ticket = new Ticket(id, state, prenotationDate, user, touristGuide, guidedTour, departureDate, returnDate, nameTour);
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
    public List<Ticket> retrieveTicket(String userEmail) throws SQLException, TicketNotFoundException, CsvValidationException, IOException {
        return retrieveByUserName(this.fd, userEmail);
    }

    private static synchronized List<Ticket> retrieveByUserName(File fd, String userName) throws IOException, CsvValidationException, TicketNotFoundException {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] record;

            List<Ticket> ticketList = new ArrayList<>();

            while ((record = csvReader.readNext()) != null) {
                boolean recordFound = record[indexUser].equals(userName);
                if (recordFound) {
                    String id = String.valueOf(record[indexIdTicket]);
                    stateEnum state = stateEnum.fromString(record[indexState]);
                    LocalDate prenotationDate = LocalDate.parse(String.valueOf(record[indexPrenotationDate]));
                    String user = String.valueOf(record[indexUser]);
                    String touristGuide = String.valueOf(record[indexTouristGuide]);
                    String guidedTour = String.valueOf(record[indexMyGuidedTourId]);
                    LocalDate departureDate = LocalDate.parse(String.valueOf(record[indexDepartureDate]));
                    LocalDate returnDate = LocalDate.parse(String.valueOf(record[indexReturnDate]));
                    String nameTour = String.valueOf(record[indexNameTour]);
                    Ticket ticket = new Ticket(id, state, prenotationDate, user, touristGuide, guidedTour, departureDate, returnDate, nameTour);
                    ticketList.add(ticket);
                }
            }

            if (ticketList.isEmpty()) {
                throw new TicketNotFoundException("No Ticket Found matching with name: " + userName);
            }

            return ticketList;
        }
    }


    @Override
    public List<Ticket> retrieveTicketForGuide(String touristGuide) throws SQLException, TicketNotFoundException, CsvValidationException, IOException {
        return retrieveTicketFromTourGuideName(this.fd, touristGuide);
    }

    private static synchronized List<Ticket> retrieveTicketFromTourGuideName(File fd, String touristGuideName) throws IOException, CsvValidationException, TicketNotFoundException {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] record;

            List<Ticket> ticketList = new ArrayList<>();

            while ((record = csvReader.readNext()) != null) {
                boolean recordFound = record[indexTouristGuide].equals(touristGuideName);
                if (recordFound) {
                    String id = String.valueOf(record[indexIdTicket]);
                    stateEnum state = stateEnum.fromString(record[indexState]);
                    LocalDate prenotationDate = LocalDate.parse(String.valueOf(record[indexPrenotationDate]));
                    String user = String.valueOf(record[indexUser]);
                    String touristGuide = String.valueOf(record[indexTouristGuide]);
                    String guidedTour = String.valueOf(record[indexMyGuidedTourId]);
                    LocalDate departureDate = LocalDate.parse(String.valueOf(record[indexDepartureDate]));
                    LocalDate returnDate = LocalDate.parse(String.valueOf(record[indexReturnDate]));
                    String nameTour = String.valueOf(record[indexNameTour]);
                    Ticket ticket = new Ticket(id, state, prenotationDate, user, touristGuide, guidedTour, departureDate, returnDate, nameTour);
                    ticketList.add(ticket);
                }
            }

            if (ticketList.isEmpty()) {
                throw new TicketNotFoundException("No Ticket Found matching with name: " + touristGuideName);
            }

            return ticketList;
        }
    }


    public void retrieveTicketFromTourGuide(String touristGuide, String userEmail, String idTour, String decision) throws CsvValidationException, IOException {
        retrieveFromTourGuide(this.fd, touristGuide, userEmail, idTour, decision);
    }

    public void retrieveFromTourGuide(File fd, String touristGuide, String userEmail, String idTour, String decision) throws CsvValidationException, IOException {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                boolean recordFound = record[indexUser].equals(userEmail) &&
                        record[indexTouristGuide].equals(touristGuide) &&
                        record[indexIdTicket].equals(idTour) &&
                        record[indexState].equals("WAITING");
                if (recordFound) {
                    record[indexState] = decision;
                }
            }
        }
    }

}