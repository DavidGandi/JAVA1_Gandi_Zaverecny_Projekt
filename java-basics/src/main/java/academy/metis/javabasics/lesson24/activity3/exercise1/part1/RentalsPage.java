package academy.metis.javabasics.lesson24.activity3.exercise1.part1;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RentalsPage {
    private final Scanner scanner;
    private final InputValidator inputValidator;
    private final RentalValidator rentalValidator;
    private final IdGenerator idGenerator;
    private final TitlesPage titlesPage;
    private final MembersPage membersPage;
    private static final Map<Long, RentalRecord> rentalRecords = new HashMap<>();
    public static String RENTALS_FILE_PATH = "rentalRecords.txt";
    public static String RENTALS_ID_COUNTER_FILE_PATH = "IdRentalsCounter.txt";

    public RentalsPage(Scanner scanner, TitlesPage titlesPage, MembersPage membersPage) {
        this.scanner = scanner;
        this.titlesPage = titlesPage;
        this.membersPage = membersPage;
        rentalValidator = new RentalValidator();
        idGenerator = new IdGenerator(RENTALS_ID_COUNTER_FILE_PATH);
        inputValidator = new InputValidator(scanner);
        loadRentalRecordsFromFile(RENTALS_FILE_PATH);
    }

    public void showRentalsMenu() {
        int numberOfCase = 7;

        System.out.println("Rentals page\n1 - Rent a title\n2 - Return a title\n3 - Prolong the rental\n4 - Show all rentals\n5 - Show rentals past due\n6 - Show Queue\n7 - Back");
        System.out.println("Choose an option: ");

        switch (inputValidator.getValidatedChoice(numberOfCase)) {
            case 1:
                rentTitle();
                break;
            case 2:
                returnTitle();
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

                rentalRecords.put(rentalRecordId, (new RentalRecord(rentalRecordId, memberId, rentedDate, returnDate, titleId, timesProlonged, isReturned)));
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
        System.out.println("\nPress enter to return to the Rentals menu...");
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
        System.out.println("Total number of all rentals: " + numberOfRentals);
    }

    public Map<Long, RentalRecord> getRentalRecords(){
        return rentalRecords;
    }
    public void rentTitle() {

        System.out.println("Rent title");
        System.out.println("Choose a member to rent a title:");
        membersPage.printAllMembers();
        System.out.println("Choose an option: ");
        scanner.nextLine();
        Member chosenMember = inputValidator.validationChecksUniqueId(membersPage.getMembers());
        if (chosenMember != null){
            System.out.println("Selected member: " + chosenMember.getMemberId());
            titlesPage.listingAllTitles();
            System.out.println("Choose a title to rent to:");
            Object chosenTitle = inputValidator.validationChecksTitlesId(titlesPage.getBooks(), titlesPage.getDvds());
            if (rentalValidator.isPossibleToRentTitle(chosenMember.getMemberId(), chosenTitle, rentalRecords)){
                updateAvailableCopiesInRentedTitle(chosenTitle, false);
                processRental(chosenMember, chosenTitle);
            }else {
                System.out.println("Press enter to continue...");
                scanner.nextLine();
                showRentalsMenu();
            }
        }
    }

    public void processRental(Member chosenMember, Object chosenTitle) {

        RentalRecord rentalRecord = null;

        if (chosenTitle instanceof Book book) {
            rentalRecord = new RentalRecord(idGenerator.generateID(), chosenMember.getMemberId(), LocalDate.now(), LocalDate.now().plusDays(21), book.getTitleId(), 0, false);
        }
        if (chosenTitle instanceof DVD dvd) {
            rentalRecord = new RentalRecord(idGenerator.generateID(), chosenMember.getMemberId(), LocalDate.now(), LocalDate.now().plusDays(21), dvd.getTitleId(), 0, false);
        }

        if (saveRentalToTheFile(rentalRecord)){
            System.out.println("Title rented successfully");
            System.out.println("Press enter to continue...");
            scanner.nextLine();
            rentalRecords.clear();
            loadRentalRecordsFromFile(RENTALS_FILE_PATH);
            showRentalsMenu();
        } else {
            System.out.println("Writing to the file failed...Title was not rented");
            System.out.println("Press enter to continue...");
            scanner.nextLine();
            showRentalsMenu();
        }
    }

    public boolean saveRentalToTheFile(RentalRecord rentalRecord) {
        String rentalRecordInfo = rentalRecord.getMemberId() + "," + rentalRecord.getRentedDate() + "," + rentalRecord.getReturnDate() + "," + rentalRecord.getTitleId() + "," + rentalRecord.getTimesProlonged() + "," + rentalRecord.isReturned() + "," + rentalRecord.getRentalRecordId();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RENTALS_FILE_PATH, true))){
            writer.write(rentalRecordInfo);
            writer.newLine();
        }catch (IOException e){
            return false;
        }
        return true;
    }
    public void updateAvailableCopiesInRentedTitle(Object title, boolean incrementTrueDecrementFalse) {

        if (incrementTrueDecrementFalse) {
            if (title instanceof Book book) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                titlesPage.updateTitles(title);
            }
            if (title instanceof DVD dvd) {
                dvd.setAvailableCopies(dvd.getAvailableCopies() + 1);
                titlesPage.updateTitles(title);
            }
        } else {
            if (title instanceof Book book) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                titlesPage.updateTitles(title);
            }
            if (title instanceof DVD dvd) {
                dvd.setAvailableCopies(dvd.getAvailableCopies() - 1);
                titlesPage.updateTitles(title);
            }
        }
        titlesPage.updateTitles(title);
    }
    public void returnTitle() {

        System.out.println("Return Title Page");
        System.out.println("Choose a member, who wants to return a title:");
        membersPage.printAllMembers();
        System.out.println("Choose an option:");
        scanner.nextLine();
        Member member = inputValidator.validationChecksUniqueId(membersPage.getMembers());
        Map<Long, RentalRecord> filteredMap = returnTitlesRentedBySpecificMember(member);

        System.out.println("Select a title to return:");
        showAllTitlesRentedBySpecificMember(filteredMap, member);
        System.out.println("Choose an option:");
        RentalRecord rentalRecordToUpdate = inputValidator.validationChecksUniqueId(rentalRecords);
        if (updateRentalRecord(rentalRecordToUpdate)){
            updateAvailableCopiesInRentedTitle(getSpecificTitleToReturn(rentalRecordToUpdate), true);
            System.out.println("Title returned successfully.");
        }else {
            System.out.println("Title was not returned");
        }
        System.out.println("Press enter to return to Rental page...");
        scanner.nextLine();
        showRentalsMenu();
    }

    public Object getSpecificTitleToReturn(RentalRecord rentalRecord) {

        for (Book book : titlesPage.getBooks()) {
            if (book.getTitleId() == rentalRecord.getTitleId()) {
                return book;
            }
        }
        for (DVD dvd : titlesPage.getDvds()) {
            if (dvd.getTitleId() == rentalRecord.getTitleId()) {
                return dvd;
            }
        }
        return null;
    }

    public boolean updateRentalRecord(RentalRecord rentalRecordToUpdate) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(RENTALS_FILE_PATH))){
                rentalRecordToUpdate.setReturned(true);
                rentalRecords.put(rentalRecordToUpdate.getRentalRecordId(), rentalRecordToUpdate);
                for (RentalRecord rentalRecord : rentalRecords.values()) {
                    writer.write(rentalRecord.getMemberId() + "," + rentalRecord.getRentedDate() + "," + rentalRecord.getReturnDate() + "," + rentalRecord.getTitleId() + "," + rentalRecord.getTimesProlonged() + "," + rentalRecord.isReturned() + "," + rentalRecord.getRentalRecordId());
                    writer.newLine();
                }
                return true;
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
    }

    public Map<Long, RentalRecord> returnTitlesRentedBySpecificMember(Member member) {

        return rentalRecords.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getMemberId().equals(member.getMemberId()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void showAllTitlesRentedBySpecificMember(Map<Long, RentalRecord> filteredMap, Member member) {

        for (RentalRecord rentalRecord : filteredMap.values()) {

            Long rentalRecordId = rentalRecord.getRentalRecordId();
            LocalDate rentedOn = rentalRecord.getRentedDate();
            String rentedBy = member.getFirstName() + " " + member.getSurname();
            int prolonged = rentalRecord.getTimesProlonged();
            String authorName = "";
            String titleName = "";
            String returned = "";

            if (rentalRecord.isReturned()){
                returned = "RETURNED";
            }else{
                returned = "NOT RETURNED";
            }

            for (Book book : titlesPage.getBooks()) {
                if (book.getTitleId() == rentalRecord.getTitleId()) {
                    authorName = book.getAuthorName();
                    titleName = book.getTitle();
                }
            }
            for (DVD dvd : titlesPage.getDvds()) {
                if (dvd.getTitleId() == rentalRecord.getTitleId()) {
                    authorName = dvd.getAuthorName();
                    titleName = dvd.getTitle();
                }
            }

            System.out.println(rentalRecordId + ". | " + titleName + " | " + authorName + " | Rented on: " + rentedOn + " | Rented by: " + rentedBy + " | Returned: " + returned + " | Times Prolonged: " + prolonged);
        }
    }
}