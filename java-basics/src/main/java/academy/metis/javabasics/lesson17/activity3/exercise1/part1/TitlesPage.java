package academy.metis.javabasics.lesson17.activity3.exercise1.part1;

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
    public TitlesPage(Scanner scanner) throws IOException {
        this.scanner = scanner;
        inputValidator = new InputValidator(scanner);
        iDTitlesGenerator = new IdGenerator(TITLES_ID_COUNTER_FILE_PATH);
        loadTitles();
    }
    public void loadTitlesFromFile(String filePath, String type) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
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
    }
    public void loadTitles() throws IOException {
        loadTitlesFromFile(BOOKS_FILE_PATH, "Book");
        loadTitlesFromFile(DVDS_FILE_PATH, "DVD");
    }
    public void showTitlesMenu() throws IOException {
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
                    showTitlesMenu();
                    break;
                case 4:
                    LibraryApp.showMainMenu();
                    break;
                case -1:
                    showTitlesMenu();
                    break;
                default:
                    showTitlesMenu();
                    break;
            }
    }
    public void showAllTitles() throws IOException {

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
    public void addTitleMenu() throws IOException {
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
            case -1:
                addTitleMenu();
                break;
            default:
                addTitleMenu();
                break;
        }
    }
    public void addBook() throws IOException {
        System.out.println("Add Book");
        System.out.print("Enter Author's name: ");
        scanner.nextLine();
        String authorName = inputValidator.getValitatedString();

        System.out.print("Enter title name: ");
        String title = inputValidator.getValitatedString();

        System.out.print("Enter available copies: ");
        int availableCopies = inputValidator.getValitatedInt();

        System.out.print("Enter ISBN number: ");
        String isbn = inputValidator.getValitatedISBN();

        System.out.print("Enter number of Pages: ");
        int pageCount = inputValidator.getValitatedInt();

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
    public void addDVD() throws IOException {
        System.out.println("Add DVD");
        System.out.print("Enter Author's name: ");
        scanner.nextLine();
        String authorName = inputValidator.getValitatedString();

        System.out.print("Enter title name: ");
        String title = inputValidator.getValitatedString();

        System.out.print("Enter available copies: ");
        int availableCopies = inputValidator.getValitatedInt();

        System.out.print("Enter Length (minutes): ");
        int durationInMinutes = inputValidator.getValitatedInt();

        System.out.print("Enter number of chapters: ");
        int numberOfTracks = inputValidator.getValitatedInt();

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
            writer.newLine();
            writer.write(titleInfo);
        }catch (IOException e){
            return false;
        }
        return true;
    }
}