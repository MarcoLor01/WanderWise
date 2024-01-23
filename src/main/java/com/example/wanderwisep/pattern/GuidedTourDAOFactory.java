package com.example.wanderwisep.pattern;

import com.example.wanderwisep.dao.GuidedTourDAO;
import com.example.wanderwisep.dao.GuidedTourDAOCSV;
import com.example.wanderwisep.dao.GuidedTourDAOJDBC;

import java.io.IOException;

public class GuidedTourDAOFactory {

    public GuidedTourDAO createGuidedTourDAO(int type) throws IllegalArgumentException {
        return switch (type) {
            case 1 -> new GuidedTourDAOJDBC();
            case 2 -> new GuidedTourDAOCSV();
            default -> throw new IllegalArgumentException("Invalid type : " + type);
        };
    }

    public GuidedTourDAO createGuidedTourDAO() {
        return new GuidedTourDAOJDBC();
    }

    public GuidedTourDAO createGuidedTourDAOJDBC() {
        return new GuidedTourDAOJDBC();
    }

    public GuidedTourDAO createGuidedTourDAOCSV() throws IOException {
        return new GuidedTourDAOCSV();
    }
}
