package academy.metis.javabasics.lesson21.activity3.exercise1.part1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TitlesPage{
    private final Scanner scanner;
    private static InputValidator inputValidator;
    private final IdGenerator iDTitlesGenerator;
    public static final List<Book> books = new ArrayList<>();
    public static final List<DVD> dvds = new ArrayList<>();
    public static String BOOKS_FILE_PATH = "titlesBook.txt";
    public static String DVDS_FILE_PATH = "titlesDVD.txt";
    public static String TITLES_ID_COUNTER_FILE_PATH = "IdTitlesCounter.txt";
    public TitlesPage(Scanner scanner) {
        this.scanner = scanner;
        inputValidator = new InputValidator(scanner);
        iDTitlesGenerator = new IdGenerator(TITLES_ID_COUNTER_FILE_PATH);
        loadTitles();
    }
    public void loadTitlesFromFile(String filePath, String type)  {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (type.equals("Book")) {
                    String title = details[0].trim();
                    String authorName = details[1].trim();
                    String isbn = details[2].trim();
                    int pageCount = Integer.parseInt(details[3].trim());
                    int availableCopies = Integer.parseInt(details[4].trim());
                    long titleId = Long.parseLong(details[5].trim());
                    books.add(new Book(title, authorName, isbn, pageCount, availableCopies, titleId, "Book"));
                } else if (type.equals("DVD")) {
                    String title = details[0].trim();
                    String authorName = details[1].trim();
                    int numberOfTracks = Integer.parseInt(details[2].trim());
                    int durationInMinutes = Integer.parseInt(details[3].trim());
                    int availableCopies = Integer.parseInt(details[4].trim());
                    long titleId = Long.parseLong(details[5].trim());
                    dvds.add(new DVD(title, authorName, numberOfTracks, durationInMinutes, availableCopies, titleId, "DVD"));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadTitles()  {
        loadTitlesFromFile(BOOKS_FILE_PATH, "Book");
        loadTitlesFromFile(DVDS_FILE_PATH, "DVD");
    }
    public void showTitlesMenu() {
        int numberOfCase = 4;

        System.out.println("Titles\n1 - Show All Titles\n2 - Add Title\n3 - Remove Title\n4 - Back");
        System.out.println("Choose an option: ");

        switch (inputValidator.getValidatedChoice(numberOfCase)) {
                case 1:
                    showAllTitles();
                    break;
                case 2:
                    addTitleMenu();
                    break;
                case 3:
                    handleTitleDeletionProcess();
                    break;
                case 4:
                    LibraryApp.showMainMenu();
                    break;
            }
    }
    public void showAllTitles()  {

        System.out.println("All Titles:");
        if (books.isEmpty() && dvds.isEmpty()){
            System.out.println("No titles available");
        }else {
            listingAllTitles();
        }
        System.out.println("\nPress enter to return to the Titles menu...");
        scanner.nextLine();
        scanner.nextLine();
        showTitlesMenu();
    }
    public void listingAllTitles() {
        int titlesCount = 0;
        System.out.println("\nBooks:");
        for (Book book : books){
            System.out.printf("%d | Name: %s | Author: %s | ISBN: %s | Number of pages: %d | Available copies: %d\n",book.getTitleId(), book.getTitle(), book.getAuthorName(), book.getIsbn(), book.getPageCount(), book.getAvailableCopies());
            titlesCount++;
        }
        System.out.println("\nDVDs:");
        for (DVD dvd : dvds) {
            System.out.printf("%d | Name: %s | Author: %s | Number of chapters: %d | Length in minutes: %d | Available copies: %d\n", dvd.getTitleId(), dvd.getTitle(), dvd.getAuthorName(), dvd.getNumberOfTracks(), dvd.getDurationInMinutes(), dvd.getAvailableCopies());
            titlesCount++;
        }
        System.out.println("\nTotal number of titles: " + titlesCount);
    }
    public void addTitleMenu()  {
        int numberOfCase = 3;

        System.out.println("Add Title\n1 - Add Book\n2 - Add DVD\n3 - Back");
        System.out.println("Choose an option: ");

        switch (inputValidator.getValidatedChoice(numberOfCase)) {
            case 1:
                addBook();
                break;
            case 2:
                addDVD();
                break;
            case 3:
                showTitlesMenu();
                break;
        }
    }
    public void addBook()  {
        System.out.println("Add Book");
        System.out.print("Enter Author's name: ");
        scanner.nextLine();
        String authorName = inputValidator.getValidatedString();

        System.out.print("Enter title name: ");
        String title = inputValidator.getValidatedString();

        System.out.print("Enter available copies: ");
        int availableCopies = inputValidator.getValidatedInt();

        System.out.print("Enter ISBN number: ");
        String isbn = inputValidator.getValidatedISBN();

        System.out.print("Enter number of Pages: ");
        int pageCount = inputValidator.getValidatedInt();

        //System.out.print("Enter Title ID: ");
        long titleId = iDTitlesGenerator.generateID();

        if (saveTitle(new Book(title, authorName, isbn, pageCount, availableCopies, titleId, "Book"))){
            System.out.println("Book added successfully...");
            System.out.println("Press enter to continue...");
            this.scanner.nextLine();
            books.clear();
            dvds.clear();
            loadTitles();
            addTitleMenu();
        } else {
            System.out.println("Failed to add the book.");
            System.out.println("Press enter to continue...");
            this.scanner.nextLine();
            addTitleMenu();
        }
    }
    public void addDVD()  {
        System.out.println("Add DVD");
        System.out.print("Enter Author's name: ");
        scanner.nextLine();
        String authorName = inputValidator.getValidatedString();

        System.out.print("Enter title name: ");
        String title = inputValidator.getValidatedString();

        System.out.print("Enter available copies: ");
        int availableCopies = inputValidator.getValidatedInt();

        System.out.print("Enter Length (minutes): ");
        int durationInMinutes = inputValidator.getValidatedInt();

        System.out.print("Enter number of chapters: ");
        int numberOfTracks = inputValidator.getValidatedInt();

        //System.out.print("Enter Title ID: ");
        long titleId = iDTitlesGenerator.generateID();

        if (saveTitle(new DVD(title, authorName, numberOfTracks, durationInMinutes, availableCopies, titleId, "DVD"))){
            System.out.println("DVD added successfully...");
            System.out.println("Press enter to continue...");
            this.scanner.nextLine();
            dvds.clear();
            books.clear();
            loadTitles();
            addTitleMenu();
        } else {
            System.out.println("Failed to add the DVD.");
            System.out.println("Press enter to continue...");
            this.scanner.nextLine();
            addTitleMenu();
        }
    }
    public boolean saveTitle(Object title) {
        String filePath;
        String titleInfo;

        if (title instanceof Book){
            filePath = BOOKS_FILE_PATH;
            Book book = (Book) title;
            titleInfo = book.getTitle() + "," + book.getAuthorName() + "," + book.getIsbn() + "," + book.getPageCount() + "," + book.getAvailableCopies() + "," + book.getTitleId();
        } else if (title instanceof DVD) {
            filePath = DVDS_FILE_PATH;
            DVD dvd = (DVD) title;
            titleInfo = dvd.getTitle() + "," + dvd.getAuthorName() + "," + dvd.getNumberOfTracks() + "," + dvd.getDurationInMinutes() + "," + dvd.getAvailableCopies() + "," + dvd.getTitleId();
        } else {
            return false;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            writer.write(titleInfo);
            writer.newLine();
        }catch (IOException e){
            return false;
        }
        return true;
    }
    public void handleTitleDeletionProcess()  {

        if (books.isEmpty() && dvds.isEmpty()){
            System.out.println("No titles to remove. Press enter to return to titles...");
            this.scanner.nextLine();
            showTitlesMenu();
        } else {
            System.out.println("Remove Title Page");
            listingAllTitles();
            System.out.println();
            System.out.println("Select a title to delete: ");
            scanner.nextLine();
            Object titleToDelete = inputValidator.validationChecksTitlesId(books, dvds);
            if (titleToDelete instanceof Book) {
                if (deleteBookTitle((Book) titleToDelete)){
                    System.out.println("Title removed successfully!");
                }else {
                    System.out.println("Title not removed.");
                }
            } else {
                if (deleteDvdTitle((DVD) titleToDelete)){
                    System.out.println("Title removed successfully!");
                }else {
                    System.out.println("Title not removed.");
                }
            }
            System.out.println("Press enter to continue...");
            this.scanner.nextLine();
            showTitlesMenu();
        }
    }
    public boolean deleteBookTitle(Book title) {
        if (books.remove(title)){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE_PATH))){
                for (Book book : books) {
                    writer.write(String.format("%s,%s,%s,%d,%d,%d\n", book.getTitle(), book.getAuthorName(), book.getIsbn(), book.getPageCount(), book.getAvailableCopies(), book.getTitleId()));
                }
                return true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean deleteDvdTitle(DVD title) {
        if (dvds.remove(title)){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(DVDS_FILE_PATH))){
                StringBuilder stringBuilder = new StringBuilder();
                for (DVD dvd : dvds) {
                    stringBuilder.append(String.format("%s,%s,%d,%d,%d,%d", dvd.getTitle(), dvd.getAuthorName(), dvd.getNumberOfTracks(), dvd.getDurationInMinutes(), dvd.getAvailableCopies(), dvd.getTitleId()));
                    stringBuilder.append(System.lineSeparator());
                }
                writer.write(stringBuilder.toString());
                return true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}