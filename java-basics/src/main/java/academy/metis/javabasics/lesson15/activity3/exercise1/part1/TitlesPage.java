package academy.metis.javabasics.lesson15.activity3.exercise1.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TitlesPage{
    private final Scanner scanner;
    private static InputValidator inputValidator;
    public static final List<Book> books = new ArrayList<>();
    public static final List<DVD> dvds = new ArrayList<>();
    public static String BOOKS_FILE_PATH = "titlesBook.txt";
    public static String DVDS_FILE_PATH = "titlesDVD.txt";
    public TitlesPage(Scanner scanner) throws IOException {
        this.scanner = scanner;
        inputValidator = new InputValidator(scanner);
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
                    showTitlesMenu();
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
        this.scanner.nextLine();
        this.scanner.nextLine();
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
}