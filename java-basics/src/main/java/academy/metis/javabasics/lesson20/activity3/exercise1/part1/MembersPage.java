package academy.metis.javabasics.lesson20.activity3.exercise1.part1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MembersPage {
    private final Scanner scanner;
    private final InputValidator inputValidator;
    private final IdGenerator idMembersGenerator;
    private static final Map<Long, Member> members = new HashMap<>();
    public static String MEMBERS_FILE_PATH = "members.txt";
    public static String MEMBERS_ID_COUNTER_FILE_PATH = "IdMembersCounter.txt";

    public MembersPage(Scanner scanner) throws IOException {
        this.scanner = scanner;
        inputValidator = new InputValidator(scanner);
        idMembersGenerator = new IdGenerator(MEMBERS_ID_COUNTER_FILE_PATH);
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
                addMember();
                break;
            case 3:
                showMembersMenu();
                break;
            case 4:
                LibraryApp.showMainMenu();
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
    public void addMember() throws IOException {
        System.out.println("Add member page");

        System.out.print("Enter member's first name: ");
        scanner.nextLine();
        String firstName = inputValidator.getValidatedString();

        System.out.print("Enter member's last name: ");
        String surname = inputValidator.getValidatedString();

        System.out.print("Enter member's date of birth (dd.MM.yyyy): ");
        String dateOfBirth = inputValidator.validateDateOfBirthInput();

        long memberId = idMembersGenerator.generateID();

        if (saveMember(new Member(firstName, surname, dateOfBirth, memberId))){
            System.out.println("Member created successfully...");
            System.out.println("Press enter to continue...");
            this.scanner.nextLine();
            members.clear();
            loadMembersFromFile(MEMBERS_FILE_PATH);
            showMembersMenu();
        } else {
            System.out.println("Failed to add the member.");
            System.out.println("Press enter to continue...");
            this.scanner.nextLine();
            showMembersMenu();
        }

    }

    public boolean saveMember(Member member) {

        String membersInfo = member.getFirstName() + "," + member.getSurname() + "," + member.getDateOfBirth() + "," + member.getMemberId();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE_PATH, true))){
            writer.write(membersInfo);
            writer.newLine();
        }catch (IOException e){
            return false;
        }
        return true;
    }
}