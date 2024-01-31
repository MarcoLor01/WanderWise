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

    private int showMenuLogin() {
        CLIPrinter.printMessage("*** Are you a User or a Tourist Guide? ***\n");
        CLIPrinter.printMessage("1) I'm a Tourist Guide\n");
        CLIPrinter.printMessage("2) I'm a User\n");
        CLIPrinter.printMessage("3) Quit\n");

        return getMenuChoice(1, 3);
    }

    private void login() {
        boolean shouldExit = false;

        while (!shouldExit) {
            try {
                int choice = showMenuLogin();

                switch (choice) {

                    case 1 -> {
                        shouldExit = true;
                        loginGuide();
                    }
                    case 2 -> {
                        shouldExit = true;
                        loginUser();
                    }
                    case 3 -> {
                        System.exit(0);
                        shouldExit = true;
                    }
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (InvalidFormatException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }


    private void loginUser() {
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
            loginBean = loginController.loginUser(loginBean);
            idSession = loginBean.getIdSession();
            new SearchBarCLIController().start(idSession);
        } catch (SQLException | UserNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void loginGuide() {
        LoginControllerApplication loginController = new LoginControllerApplication();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            CLIPrinter.printMessage("Email: ");
            String email = reader.readLine();
            CLIPrinter.printMessage("Password: ");
            String password = reader.readLine();
            LoginBean loginBean = new LoginBean();
            loginBean.setEmail(email);
            loginBean.setPassword(password);
            loginBean = loginController.loginGuide(loginBean);
            idSession = loginBean.getIdSession();
            new GuideConfirmCLIController().start(idSession);
        } catch (IOException | SQLException | UserNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void helloMessage() {
        CLIPrinter.printMessage("\n" +
                " __          __                _           __          __ _            \n" +
                " \\ \\        / /               | |          \\ \\        / /(_)           \n" +
                "  \\ \\  /\\  / /__ _  _ __    __| |  ___  _ __\\ \\  /\\  / /  _  ___   ___ \n" +
                "   \\ \\/  \\/ // _` || '_ \\  / _` | / _ \\| '__|\\ \\/  \\/ /  | |/ __| / _ \\\n" +
                "    \\  /\\  /| (_| || | | || (_| ||  __/| |    \\  /\\  /   | |\\__ \\|  __/\n" +
                "     \\/  \\/  \\__,_||_| |_| \\__,_| \\___||_|     \\/  \\/    |_||___/ \\___|\n" +
                "                                                                       \n" +
                "                                                                       \n");
    }
}
