package com.example.wanderwisep.dao;

import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginUserDAOTest {

    @Test
    void loginWrongUser() {
        assertThrows(UserNotFoundException.class, () -> {
            String email = "test@test.com";
            String password = "test";
            LoginUserDAO loginUserDAO = new LoginUserDAO();
            loginUserDAO.findUser(email, password);
        });
    }

    @Test
    void loginRightUser() {
        try {
            String email = "user@user.com";
            String password = "user";
            LoginUserDAO loginUserDAO = new LoginUserDAO();
            User user = loginUserDAO.findUser(email, password);
            assertNotNull(user);
            assertEquals(email, user.getEmail(), "Email should match");
        } catch (UserNotFoundException | SQLException e) {
            fail("Exception not expected", e);
        }
    }
}
