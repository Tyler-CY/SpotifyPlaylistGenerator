package com.example.spottywebapp.cli;

import com.example.spottywebapp.Controllers.LoginController;
import com.example.spottywebapp.Controllers.UserReadWriter;
import com.example.spottywebapp.entities.UserList;

import java.io.IOException;
import java.util.Scanner;

public class LoginUI {

    /**
     * Login UI. Reads the user's input for username and password.
     *
     * @param controller a LoginController
     */
    public String runLogin(LoginController controller){
        System.out.println("Username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        LoginController.LoginControllerResult result = controller.runLogin(username, password);
        switch (result){
            case SUCCESS:
                    System.out.println("Logged in.");
                    return username;
            case FAILURE:
                System.out.println("Failed to login!");
                break;
            case NO_SUCH_USER:
                System.out.println("No such user... Try again.");

        }
    }
}