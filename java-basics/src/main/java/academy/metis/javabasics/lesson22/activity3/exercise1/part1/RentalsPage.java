package academy.metis.javabasics.lesson22.activity3.exercise1.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RentalsPage {
    private final Scanner scanner;
    private final InputValidator inputValidator;
    private static final Map<Long, RentalRecord> rentalRecords = new HashMap<>();
    public static String RENTALS_FILE_PATH = "rentalRecords.txt";

    public RentalsPage(Scanner scanner) {
        this.scanner = scanner;
        inputValidator = new InputValidator(scanner);
        loadRentalRecordsFromFile(RENTALS_FILE_PATH);
    }

    public void showRentalsMenu() {
        int numberOfCase = 7;

        System.out.println("Rentals page\n1 - Rent a title\n2 - Return a title\n3 - Prolong the rental\n4 - Show all rentals\n5 - Show rentals past due\n6 - Show Queue\n7 - Back");
        System.out.println("Choose an option: ");

        switch (inputValidator.getValidatedChoice(numberOfCase)) {
            case 1:
                showRentalsMenu();
                break;
            case 2:
                showRentalsMenu();
                break;
            case 3:
                showRentalsMenu();
                break;
            case 4:
                showAllRentals();
                break;
            case 5:
                showRentalsMenu();
                break;
            case 6:
                showRentalsMenu();
                break;
            case 7:
                LibraryApp.showMainMenu();
                break;
        }
    }

    public static void loadRentalRecordsFromFile(String filePath) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                Long memberId = Long.parseLong(details[0].trim());
                LocalDate rentedDate = LocalDate.parse(details[1].trim());
                LocalDate returnDate = LocalDate.parse(details[2].trim());
                Long titleId = Long.parseLong(details[3].trim());
                int timesProlonged = Integer.parseInt(details[4].trim());
                boolean isReturned = Boolean.parseBoolean(details[5].trim());
                Long rentalRecordId = Long.parseLong(details[6].trim());

                rentalRecords.put(memberId, (new RentalRecord(rentalRecordId, memberId, rentedDate, returnDate, titleId, timesProlonged, isReturned)));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showAllRentals() {

        System.out.println("All Rentals:");

        if (rentalRecords.isEmpty()){
            System.out.println("No rentals available");
        }else {
            listingAllRentals();
        }
        System.out.println("\nPress enter to return to the Titles menu...");
        scanner.nextLine();
        scanner.nextLine();
        showRentalsMenu();
    }

    public void listingAllRentals() {
        int numberOfRentals = 0;
        for (RentalRecord rentalRecord : rentalRecords.values()){
            numberOfRentals++;
            System.out.println(rentalRecord);

        }
        System.out.println("Total number of all members: " + numberOfRentals);
    }

    public Map<Long, RentalRecord> getRentalRecords(){
        return rentalRecords;
    }
}