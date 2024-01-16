package com.example.wanderwisep;

import com.example.wanderwisep.cli_graphic_controller.LoginCLIController;

public class MainCLI {
    public static void main(String[] args) {
        LoginCLIController applicationController = new LoginCLIController();
        applicationController.start();
    }
}
