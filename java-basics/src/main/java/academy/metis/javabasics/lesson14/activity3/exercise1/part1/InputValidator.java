package academy.metis.javabasics.lesson14.activity3.exercise1.part1;

import java.util.Scanner;

public class InputValidator {
    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }
    public int getValidatedChoice(int numberOfCase) {

        try {
            int inputNumber = Integer.parseInt(scanner.next());

            if (inputNumber < 1 || inputNumber > numberOfCase) {
                System.out.println("Please enter a number in the range from 1 to " + numberOfCase);
                return 0;
            } else {
                return inputNumber;
            }
        }catch (NumberFormatException e){
            System.out.println("Please enter a valid value.");
            return -1;
        }
    }
}