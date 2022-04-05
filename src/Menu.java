import advisor.api.*;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private static int currentPage = 0;
    private static ArrayList<String> toPrint;
    static String[] option;

    static void setCurrentPage(int currPage) {
        currentPage = currPage;
    }

    public static void launch() throws IOException, InterruptedException {
        greetingMessage();
        Scanner scan = new Scanner(System.in);
        String command = "";

        while (!command.equals("exit")) {
            option = scan.nextLine().split(" ", 2);
            command = option[0];
            parseCommand(command);
        }

        System.out.println("---GOODBYE!---");

    }

    static void greetingMessage() {
        System.out.print("Welcome to my version of JetBrains Academy Music Advisor Project\n" +
                "Available commands:\n" +
                "\t- auth (to access/authorize app)" +
                "\t- featured (list of featured playlists)\n" +
                "\t- new (list of new albums)\n" +
                "\t- categories (list of available categories)\n" +
                "\t- playlists <CATEGORY NAME> (list of playlists from chosen category)\n" +
                "\t- prev (previous page)\n" +
                "\t- next (next page)\n" +
                "\t- exit\n");
    }

    private static void parseCommand(String command) throws IOException, InterruptedException {

        if (command.equals("auth")) {
            Auth.init();

            if (Auth.isAuthorized()) {
                System.out.println("---SUCCESS---");
            } else {
                System.out.println("code not received");
                System.out.println("---FAILURE---");
            }
        }

        if (!Auth.isAuthorized()) {
            System.out.println("Please, provide access for application.");
            return;
        }

        if (command.equals("featured")) {
            JsonArray featured = Client.getFeatured();
            toPrint = Printer.printFeaturedPlaylists(featured);
            currentPage = 0;
            Printer.print(toPrint, currentPage);
        }

        if (command.equals("new")) {
            JsonArray newReleases = Client.getNewReleases();
            toPrint = Printer.printNewAlbums(newReleases);
            currentPage = 0;
            Printer.print(toPrint, currentPage);
        }

        if (command.equals("categories")) {
            JsonArray categories = Client.getCategories();
            toPrint = Printer.printCategories(categories);
            currentPage = 0;
            Printer.print(toPrint, currentPage);
        }

        if (command.equals("prev")) {
            if (currentPage == 0) {
                System.out.println("No more pages");
            } else {
                currentPage--;
                Printer.print(toPrint, currentPage);
            }
        }

        if (command.equals("next")) {
            if (currentPage == Printer.getTotalPages() - 1) {
                System.out.println("No more pages");
            } else {
                currentPage++;
                Printer.print(toPrint, currentPage);
            }
        }
        if (command.equals("playlists")) {
            String category = option[1];
            JsonArray categoryPlaylists = Client.getCategoryPlaylists(category);
            toPrint = Printer.printFeaturedPlaylists(categoryPlaylists);
            currentPage = 0;
            Printer.print(toPrint, currentPage);
        }
    }
}
