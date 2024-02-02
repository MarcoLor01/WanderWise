package com.example.wanderwisep.cli_graphic_controller;

import com.example.wanderwisep.application_controller.LoginControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.exception.InvalidFormatException;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.utilities.CLIPrinter;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCLIController extends NavigatorCLIController {

    private String idSession;

    private final Logger logger = Logger.getLogger(LoginCLIController.class.getName());

    public void start() {
        boolean shouldExit = false;

        while (!shouldExit) {
            try {
                int choice = showMenu();

                switch (choice) {
                    case 1 -> {
                        login();
                        shouldExit = true;
                    }
                    case 2 -> System.exit(0);
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
        Scanner scanner = new Scanner(System.in);
        try {
            CLIPrinter.printMessage("Email: ");
            String email = scanner.nextLine().trim();
            CLIPrinter.printMessage("Password: ");
            String password = scanner.nextLine().trim();
            LoginBean loginBean = new LoginBean();
            loginBean.setEmail(email);
            loginBean.setPassword(password);
            loginBean = loginController.login(loginBean);
            idSession = loginBean.getIdSession();

            if (loginBean.getRole().equals("User")) new SearchBarCLIController().start(idSession);

            if (loginBean.getRole().equals("TouristGuide")) new GuideConfirmCLIController().start(idSession);

        } catch (SQLException | UserNotFoundException e) {
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
