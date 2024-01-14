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
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketDAOCSV implements TicketDAO {
    private static Integer INDEX_ID_TICKET = 0;
    private static Integer INDEX_STATE = 1;
    private static Integer INDEX_PRENOTATION_DATE = 2;
    private static Integer INDEX_USER = 3;
    private static Integer INDEX_TOURIST_GUIDE = 4;
    private static Integer INDEX_MY_GUIDED_TOUR_ID = 5;
    private static Integer INDEX_DEPARTURE_DATE = 6;
    private static Integer INDEX_RETURN_DATE = 7;
    private static Integer INDEX_NAME_TOUR = 8;
    Logger logger = Logger.getLogger(TicketDAOCSV.class.getName());
    private static final String CSV_FILE_NAME = "src/main/resources/com/example/wanderwisep/ticketDBlocal.csv";
    private File fd;

    public TicketDAOCSV() throws IOException {
        this.fd = new File(CSV_FILE_NAME);

        if (!fd.exists()) {
            fd.createNewFile();
        }
    }

    @Override
    public void createTicket(Ticket ticket) throws DAOException, SQLException, IOException, DuplicateTourException, CsvValidationException {

        try {
            List<Ticket> ticketsList = retrieveByIdTicket(this.fd, ticket.getIdTicket());
            if (ticketsList.size() > 0) {
                throw new DuplicateTourException("Tour already booked");
            }
        } catch (TicketNotFoundException e) {
            logger.log(Level.INFO, e.getMessage());
        }
        insertTicket(this.fd, ticket);
    }

    private static synchronized void insertTicket(File fd, Ticket ticket) throws IOException {

        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)))) {
            String[] ticketRecord = new String[9];
            ticketRecord[INDEX_ID_TICKET] = ticket.getIdTicket();
            ticketRecord[INDEX_STATE] = ticket.getState().getStateName();
            ticketRecord[INDEX_PRENOTATION_DATE] = String.valueOf(ticket.getPrenotationDate());
            ticketRecord[INDEX_USER] = ticket.getUser();
            ticketRecord[INDEX_TOURIST_GUIDE] = ticket.getMyTouristGuide();
            ticketRecord[INDEX_MY_GUIDED_TOUR_ID] = ticket.getMyGuidedTourId();
            ticketRecord[INDEX_DEPARTURE_DATE] = String.valueOf(ticket.getDepartureDate());
            ticketRecord[INDEX_RETURN_DATE] = String.valueOf(ticket.getReturnDate());
            ticketRecord[INDEX_NAME_TOUR] = String.valueOf(ticket.getTourName());
            csvWriter.writeNext(ticketRecord);
            csvWriter.flush();
        }

    }

    private static synchronized List<Ticket> retrieveByIdTicket(File fd, String idTicket) throws IOException, CsvValidationException, TicketNotFoundException {
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] record;
        List<Ticket> ticketList = new ArrayList<>();

        while ((record = csvReader.readNext()) != null) {
            boolean recordFound = record[INDEX_ID_TICKET].equals(String.valueOf(idTicket));
            if (recordFound) {
                String id = String.valueOf(record[INDEX_ID_TICKET]);
                stateEnum state = stateEnum.fromString(record[INDEX_STATE]);
                LocalDate prenotationDate = LocalDate.parse(String.valueOf(record[INDEX_PRENOTATION_DATE]));
                String user = String.valueOf(record[INDEX_USER]);
                String touristGuide = String.valueOf(record[INDEX_TOURIST_GUIDE]);
                String guidedTour = String.valueOf(record[INDEX_MY_GUIDED_TOUR_ID]);
                LocalDate departureDate = LocalDate.parse(String.valueOf(record[INDEX_DEPARTURE_DATE]));
                LocalDate returnDate = LocalDate.parse(String.valueOf(record[INDEX_RETURN_DATE]));
                String nameTour = String.valueOf(record[INDEX_NAME_TOUR]);
                Ticket ticket = new Ticket(id, state, prenotationDate, user, touristGuide, guidedTour, departureDate, returnDate, nameTour);
                ticketList.add(ticket);
            }
        }
        csvReader.close();

        if (ticketList.isEmpty()) {
            throw new TicketNotFoundException("No Ticket founded with ticket id: " + idTicket);
        }
        return ticketList;
    }

    @Override
    public List<Ticket> retrieveTicket(String userEmail) throws SQLException, TicketNotFoundException, CsvValidationException, IOException {
        return retrieveByUserName(this.fd, userEmail);
    }

    public List<Ticket> retrieveByUserName(File fd, String userName) throws IOException, CsvValidationException, TicketNotFoundException {
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] record;

        List<Ticket> ticketList = new ArrayList<>();

        while ((record = csvReader.readNext()) != null) {
            boolean recordFound = record[INDEX_USER].equals(userName);
            if (recordFound) {
                String id = String.valueOf(record[INDEX_ID_TICKET]);
                stateEnum state = stateEnum.fromString(record[INDEX_STATE]);
                LocalDate prenotationDate = LocalDate.parse(String.valueOf(record[INDEX_PRENOTATION_DATE]));
                String user = String.valueOf(record[INDEX_USER]);
                String touristGuide = String.valueOf(record[INDEX_TOURIST_GUIDE]);
                String guidedTour = String.valueOf(record[INDEX_MY_GUIDED_TOUR_ID]);
                LocalDate departureDate = LocalDate.parse(String.valueOf(record[INDEX_DEPARTURE_DATE]));
                LocalDate returnDate = LocalDate.parse(String.valueOf(record[INDEX_RETURN_DATE]));
                String nameTour = String.valueOf(record[INDEX_NAME_TOUR]);
                Ticket ticket = new Ticket(id, state, prenotationDate, user, touristGuide, guidedTour, departureDate, returnDate, nameTour);
                ticketList.add(ticket);
            }
        }

        csvReader.close();

        if (ticketList.isEmpty()) {
            throw new TicketNotFoundException("No Ticket Found matching with name: " + userName);
        }

        return ticketList;
    }

    @Override
    public List<Ticket> retrieveTicketForGuide(String touristGuide) throws SQLException, TicketNotFoundException, CsvValidationException, IOException {
        return retrieveTicketFromTourGuideName(this.fd, touristGuide);
    }

    public List<Ticket> retrieveTicketFromTourGuideName(File fd, String touristGuideName) throws IOException, CsvValidationException, TicketNotFoundException {
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] record;

        List<Ticket> ticketList = new ArrayList<>();

        while ((record = csvReader.readNext()) != null) {
            boolean recordFound = record[INDEX_TOURIST_GUIDE].equals(touristGuideName);
            if (recordFound) {
                String id = String.valueOf(record[INDEX_ID_TICKET]);
                stateEnum state = stateEnum.fromString(record[INDEX_STATE]);
                LocalDate prenotationDate = LocalDate.parse(String.valueOf(record[INDEX_PRENOTATION_DATE]));
                String user = String.valueOf(record[INDEX_USER]);
                String touristGuide = String.valueOf(record[INDEX_TOURIST_GUIDE]);
                String guidedTour = String.valueOf(record[INDEX_MY_GUIDED_TOUR_ID]);
                LocalDate departureDate = LocalDate.parse(String.valueOf(record[INDEX_DEPARTURE_DATE]));
                LocalDate returnDate = LocalDate.parse(String.valueOf(record[INDEX_RETURN_DATE]));
                String nameTour = String.valueOf(record[INDEX_NAME_TOUR]);
                Ticket ticket = new Ticket(id, state, prenotationDate, user, touristGuide, guidedTour, departureDate, returnDate, nameTour);
                ticketList.add(ticket);
            }
        }

        csvReader.close();

        if (ticketList.isEmpty()) {
            throw new TicketNotFoundException("No Ticket Found matching with name: " + touristGuideName);
        }

        return ticketList;
    }

    public void retrieveTicketFromTourGuide(String touristGuide, String userEmail, String idTour, String decision) throws CsvValidationException, IOException {
        retrieveFromTourGuide(this.fd, touristGuide, userEmail, idTour, decision);
    }

    public void retrieveFromTourGuide(File fd, String touristGuide, String userEmail, String idTour, String decision) throws CsvValidationException, IOException {
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] record;

        while ((record = csvReader.readNext()) != null) {
            boolean recordFound = record[INDEX_USER].equals(userEmail) & record[INDEX_TOURIST_GUIDE].equals(touristGuide) & record[INDEX_ID_TICKET].equals(idTour) & record[INDEX_STATE].equals("WAITING");
            if (recordFound) {
                record[INDEX_STATE] = decision;
            }
        }
    }
}