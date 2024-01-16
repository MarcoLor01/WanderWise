package com.example.wanderwisep;

import com.example.wanderwisep.cli_graphic_controller.LoginCLIGraphicController;

public class MainCLI {
    public static void main(String[] args) {
        LoginCLIGraphicController applicationController = new LoginCLIGraphicController();
        applicationController.start();
    }
}
