package com.example.wanderwisep.cli_graphic_controller;

import com.example.wanderwisep.utilities.CLIPrinter;

import java.util.Scanner;

public class NavigatorCLIController {

    protected int getMenuChoice(int min, int max) {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            CLIPrinter.printMessage("Please enter your choice: ");
            choice = input.nextInt();
            if (choice >= min && choice <= max) {
                break;
            }
            CLIPrinter.printMessage("Invalid option\n");
        }
        return choice;
    }
}
