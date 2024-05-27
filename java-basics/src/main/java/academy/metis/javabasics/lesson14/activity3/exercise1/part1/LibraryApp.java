package academy.metis.javabasics.lesson14.activity3.exercise1.part1;

import java.util.Scanner;

public class LibraryApp  {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputValidator validator = new InputValidator(scanner);

    public static void main(String[] args) {
        System.out.println(">>>> Welcome to our Library <<<<");
        showMainMenu();
    }
    public static void showMainMenu() {

        int numberOfCase = 4;

        System.out.println("1 - Titles\n2 - Members\n3 - Rentals\n4 - Exit");
        System.out.println("Choose an option: ");

        switch (validator.getValidatedChoice(numberOfCase)) {
            case 1:
                showMainMenu();
                break;
            case 2:
                showMainMenu();
                break;
            case 3:
                showMainMenu();
                break;
            case 4:
                System.out.println("Ukoncujem program...");
                break;
            case -1:
                showMainMenu();
                break;
            default:
                showMainMenu();
                break;
        }
    }
}
