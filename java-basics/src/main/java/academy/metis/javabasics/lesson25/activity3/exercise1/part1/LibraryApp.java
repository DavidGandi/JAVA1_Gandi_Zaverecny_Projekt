package academy.metis.javabasics.lesson25.activity3.exercise1.part1;

import java.util.Scanner;

public class LibraryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputValidator validator = new InputValidator(scanner);

    public static void main(String[] args)  {
        System.out.println(">>>> Welcome to our Library <<<<");
        showMainMenu();
    }
    public static void showMainMenu() {

        int numberOfCase = 4;

        System.out.println("Main menu:\n1 - Titles\n2 - Members\n3 - Rentals\n4 - Exit");
        System.out.println("Choose an option: ");

        switch (validator.getValidatedChoice(numberOfCase)) {
            case 1:
                TitlesPage titlesPage = new TitlesPage(scanner);
                titlesPage.showTitlesMenu();
                break;
            case 2:
                MembersPage membersPage = new MembersPage(scanner);
                membersPage.showMembersMenu();
                break;
            case 3:
                RentalsPage rentalsPage = new RentalsPage(scanner, new TitlesPage(scanner), new MembersPage(scanner), new Queue(scanner));
                rentalsPage.showRentalsMenu();
                break;
            case 4:
                break;
        }
    }
}
