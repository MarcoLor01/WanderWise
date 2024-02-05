package com.example.wanderwisep.cli_graphic_controller;

import com.example.wanderwisep.application_controller.LoginControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.exception.InvalidFormatException;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.utilities.CLIPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCLIController extends NavigatorCLIController {

    private final Logger logger = Logger.getLogger(LoginCLIController.class.getName());

    public void start() {
        boolean shouldExit = false;

        while (!shouldExit) {
            try {
                int choice = showMenu();

                switch (choice) {
                    case 1 -> {
                        shouldExit = true;
                        login();
                    }
                    case 2 -> {
                        return;
                    }
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (InvalidFormatException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }


    public int showMenu() {
        helloMessage();
        CLIPrinter.printMessage("*** What should I do for you? ***\n");
        CLIPrinter.printMessage("1) Login\n");
        CLIPrinter.printMessage("2) Quit\n");

        return getMenuChoice(1, 2);
    }

    private void login() {
        LoginControllerApplication loginController = new LoginControllerApplication();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            CLIPrinter.printMessage("Email: ");
            String email = reader.readLine();
            CLIPrinter.printMessage("password: ");
            String password = reader.readLine();
            LoginBean loginBean = new LoginBean();
            loginBean.setEmail(email);
            loginBean.setPassword(password);
            loginBean = loginController.login(loginBean);
            String idSession = loginBean.getIdSession();

            if (loginBean.getRole().equals("User")) new SearchBarCLIController().start(idSession);

            if (loginBean.getRole().equals("TouristGuide")) new GuideConfirmCLIController().start(idSession);

        } catch (SQLException | IOException | UserNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void helloMessage() {
        CLIPrinter.printMessage("""

                 __          __                _           __          __ _           \s
                 \\ \\        / /               | |          \\ \\        / /(_)          \s
                  \\ \\  /\\  / /__ _  _ __    __| |  ___  _ __\\ \\  /\\  / /  _  ___   ___\s
                   \\ \\/  \\/ // _` || '_ \\  / _` | / _ \\| '__|\\ \\/  \\/ /  | |/ __| / _ \\
                    \\  /\\  /| (_| || | | || (_| ||  __/| |    \\  /\\  /   | |\\__ \\|  __/
                     \\/  \\/  \\__,_||_| |_| \\__,_| \\___||_|     \\/  \\/    |_||___/ \\___|
                                                                                      \s
                                                                                      \s
                """);
    }
}
