package com.example.wanderwisep.bean;

import com.example.wanderwisep.exception.InvalidFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginBeanTest {

    @Test
    void wrongField() {
        assertThrows(InvalidFormatException.class, () -> {
            LoginBean loginBean = new LoginBean();
            loginBean.checkField("", "password");
        });
    }

    @Test
    void rightField() {
        LoginBean loginBean = new LoginBean();
        assertDoesNotThrow(() -> loginBean.checkField("email", "password"));
    }
}