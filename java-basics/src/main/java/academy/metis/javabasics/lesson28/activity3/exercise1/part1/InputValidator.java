package academy.metis.javabasics.lesson28.activity3.exercise1.part1;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getValidatedChoice(int numberOfCase) {


        while (true) {

            try {
                int inputNumber = Integer.parseInt(scanner.next());

                if (inputNumber < 1 || inputNumber > numberOfCase) {
                    System.out.println("Please enter a number in the range from 1 to " + numberOfCase);
                    //return 0;
                } else {
                    return inputNumber;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid value.");
                //return -1;
            }
        }
    }

    public String getValidatedString() {

        boolean firstAttempt = true;

        while (true) {
            if (!firstAttempt) {
                System.out.println("Please enter a valid value.");
            }
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }else {
                firstAttempt = false;
            }
        }
    }

    public int getValidatedInt() {

        boolean firstAttempt = true;

        while (true) {
            if (!firstAttempt) {
                System.out.println("Please enter a valid value.");
            }
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                firstAttempt = false;
            }
        }

    }

    public String getValidatedISBN() {

        boolean firstAttempt = true;

        while (true) {
            if (!firstAttempt) {
                System.out.println("Please enter a valid value.");
            }
            String input = scanner.nextLine();
            try {
                Long.parseLong(input);
                return input;
            } catch (NumberFormatException e) {
                firstAttempt = false;
            }
        }
    }

    public Object validationChecksTitlesId(List<Book> books, List<DVD> dvds) {

        boolean firstAttempt = true;

        while (true) {

            if (!firstAttempt) {
                System.out.println("Please enter a valid id: ");
            }
            long id;
            String input = scanner.nextLine();
            try {
                id = Long.parseLong(input);

                for (Book book : books) {
                    if (book.getTitleId() == id) {
                        return book;
                    }
                }
                for (DVD dvd : dvds) {
                    if (dvd.getTitleId() == id) {
                        return dvd;
                    }
                }
            } catch (NumberFormatException e) {
                firstAttempt = false;
            }
        }
    }

    public String validateDateOfBirthInput() {
        boolean firstAttempt = true;

        while (true) {
            if (!firstAttempt) {
                System.out.println("Please enter a valid value.");
            }
            String input = scanner.nextLine();

            Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");
            Matcher matcher = pattern.matcher(input);

            if (matcher.matches()) {
                String[] dateParts = input.split("\\.");
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);

                if (month == 2 && Year.of(year).isLeap() && day > 0 && day <= 29 && year > LocalDate.now().getYear() - 120 && year <= LocalDate.now().getYear() - 7) {
                    return input;
                } else if (month == 2 && Year.of(year).isLeap() && day > 0 && day <= 29 && year > LocalDate.now().getYear() - 120 && year <= LocalDate.now().getYear() - 7) {
                    return input;
                } else if (isMonthLong(month) && day > 0 && day <= 31 && year > LocalDate.now().getYear() - 120 && year <= LocalDate.now().getYear() - 7) {
                    return input;
                } else if (!isMonthLong(month) && month != 2 && day > 0 && day <= 30 && year > LocalDate.now().getYear() - 120 && year <= LocalDate.now().getYear() - 7) {
                    return input;
                } else {
                    firstAttempt = false;
                }
            } else {
                firstAttempt = false;
            }
        }
    }
    private boolean isMonthLong(int month){
        int[] longMonths = {1,3,5,7,8,10,12};

        for (int m : longMonths){
            if (m == month){
                return true;
            }
        }
        return false;
    }
    public <T> T validationChecksUniqueId(Map<Long, T> objectMap) {

        boolean firstAttempt = true;

        while (true){
            if (!firstAttempt){
                System.out.println("Please enter a valid id.");
            }
            long id;
            String input = scanner.nextLine();
            try {
                id = Long.parseLong(input);
                if (objectMap.get(id) != null){
                    return objectMap.get(id);
                }else {
                    firstAttempt = false;
                }

            } catch (NumberFormatException e) {
                firstAttempt = false;
            }
        }
    }
}