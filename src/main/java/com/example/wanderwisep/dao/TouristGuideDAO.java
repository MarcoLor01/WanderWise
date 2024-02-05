package com.example.wanderwisep.dao;

import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.example.wanderwisep.model.TouristGuide;

import java.sql.SQLException;

public interface TouristGuideDAO {
    TouristGuide retrieveTouristGuide(String touristGuideEmail) throws SQLException, TouristGuideNotFoundException;

}
