package academy.metis.javabasics.lesson19.activity3.exercise1.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MembersPage {
    private final Scanner scanner;
    private final InputValidator inputValidator;
    private static final Map<Long, Member> members = new HashMap<>();
    public static String MEMBERS_FILE_PATH = "members.txt";

    public MembersPage(Scanner scanner) throws IOException {
        this.scanner = scanner;
        inputValidator = new InputValidator(scanner);
        loadMembersFromFile(MEMBERS_FILE_PATH);
    }
    public static void loadMembersFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] details = line.split(",");
            String firstName = details[0].trim();
            String surname = details[1].trim();
            String dateOfBirth = details[2].trim();
            long memberId = Long.parseLong(details[3].trim());

            members.put(memberId, (new Member(firstName, surname, dateOfBirth, memberId)));
        }
    }
    public Map<Long, Member> getMembers() {
        return members;
    }
    public void showMembersMenu() throws IOException {
        int numberOfCase = 4;

        System.out.println("Members\n1 - Show All Members\n2 - Add Member\n3 - Remove Member\n4 - Back");
        System.out.println("Choose an option: ");

        switch (inputValidator.getValidatedChoice(numberOfCase)) {
            case 1:
                showAllMembers();
                break;
            case 2:
                showMembersMenu();
                break;
            case 3:
                showMembersMenu();
                break;
            case 4:
                LibraryApp.showMainMenu();
                break;
            case -1:
                showMembersMenu();
                break;
            default:
                showMembersMenu();
                break;
        }
    }
    public void showAllMembers() throws IOException {

        System.out.println("All Members:");

        if (members.isEmpty()){
            System.out.println("No members available");
        }else {
            printAllMembers();
        }
        System.out.println("\nPress enter to return to the Titles menu...");
        scanner.nextLine();
        scanner.nextLine();
        showMembersMenu();
    }
    public void printAllMembers() {
        int numberOfMembers = 0;
        for (Member member : members.values()){
            numberOfMembers++;
            System.out.println(member);

        }
        System.out.println("Total number of all members: " + numberOfMembers);
    }
}