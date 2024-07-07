package academy.metis.javabasics.lesson27.activity3.exercise1.part1;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Queue {
    private final IdGenerator idGenerator;
    private final Scanner scanner;
    private final InputValidator inputValidator;
    private static final Map<Long, QueueRecord> queueRecords = new HashMap<>();
    public static String QUEUE_FILE_PATH = "QueueRecords.txt";
    public static String QUEUE_RECORDS_ID_COUNTER_FILE_PATH = "IdQueueCounter.txt";

    public Queue(Scanner scanner) {
        this.scanner = scanner;
        inputValidator = new InputValidator(scanner);
        idGenerator = new IdGenerator(QUEUE_RECORDS_ID_COUNTER_FILE_PATH);
        loadQueueRecordsFromFile(QUEUE_FILE_PATH);
    }
    public void showQueueMenu(long memberId, long titleId) {
        int numberOfCase = 2;

        System.out.println("Do you want to add your inquiry to queue?\n1 - Yes\n2 - No");
        switch (inputValidator.getValidatedChoice(numberOfCase)){
            case 1 :
                addToQueue(memberId, titleId);
                break;
            case 2 :
                scanner.nextLine();
                new RentalsPage(scanner, new TitlesPage(scanner), new MembersPage(scanner), this);
                break;
        }
    }
    public void addToQueue(long memberId, long titleId) {

        QueueRecord queueRecord = new QueueRecord(idGenerator.generateID(), memberId, titleId, LocalDate.now(), false);

        if (saveTheQueueToTheFile(queueRecord)){
            queueRecords.put(queueRecord.getQueueItemId(), queueRecord);
            System.out.println("Successfully added to Queue.");
            this.scanner.nextLine();
            new RentalsPage(scanner, new TitlesPage(scanner), new MembersPage(scanner), this);

        } else {
            System.out.println("Failed to add to Queue.");
            System.out.println("Press enter to continue...");
            this.scanner.nextLine();
            RentalsPage rentalsPage = new RentalsPage(scanner, new TitlesPage(scanner), new MembersPage(scanner), this);
            rentalsPage.showRentalsMenu();
        }
    }
    public boolean saveTheQueueToTheFile(QueueRecord queueRecord) {

        String queueRecordInfo = queueRecord.getMemberId() + "," + queueRecord.getTitleId() + "," + queueRecord.getAddedToQueueDate() + "," + queueRecord.isResolved() + "," + queueRecord.getQueueItemId();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(QUEUE_FILE_PATH, true))){
            writer.write(queueRecordInfo);
            writer.newLine();
        }catch (IOException e){
            return false;
        }
        return true;
    }
    public void loadQueueRecordsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");

                    long memberId = Long.parseLong(details[0].trim());
                    long titleId = Long.parseLong(details[1].trim());
                    LocalDate addedToQueueDate = LocalDate.parse(details[2].trim());
                    boolean isResolved = Boolean.parseBoolean(details[3].trim());
                    long queueItemId = Long.parseLong(details[4].trim());


                    queueRecords.put(queueItemId, (new QueueRecord(queueItemId, memberId, titleId, addedToQueueDate, isResolved)));

            }
        }catch (/*NumberFormatException | DateTimeParseException | */IOException e){
            e.printStackTrace();
        }
    }
    public void showAllQueueRecords() {
        System.out.println("All queue records:");

        if (queueRecords.isEmpty()){
            System.out.println("No queue records available");
        }else {
            listingAllQueueRecords();
        }
        System.out.println("\nPress enter to return to the Rentals page...");
        scanner.nextLine();
        scanner.nextLine();
        RentalsPage rentalsPage = new RentalsPage(scanner, new TitlesPage(scanner), new MembersPage(scanner), this);
        rentalsPage.showRentalsMenu();
    }
    public void listingAllQueueRecords() {
        int numberOfQueues = 0;
        for (QueueRecord queueRecord : queueRecords.values()){
            numberOfQueues++;
            System.out.println(queueRecord);

        }
        System.out.println("Total number of all queue records: " + numberOfQueues);
    }

    public Map<Long, QueueRecord> getQueueRecords(){
        return queueRecords;
    }
}