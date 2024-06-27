package academy.metis.javabasics.lesson23.activity3.exercise1.part1;

import java.util.Map;

public class RentalValidator {
    private static final int MAX_NUMBER_OF_RENTED_TITLES = 2;

    public boolean isPossibleToRentTitle(long memberId, Object chosenTitle, Map<Long, RentalRecord> rentalRecords) {

        if (chosenTitle instanceof Book book){
            return !rentedTwoTitlesOrRentedChosenTitleAlready(memberId, book.getTitleId(), rentalRecords) && isTheTitleAvailable(book.getAvailableCopies());
        }
        if (chosenTitle instanceof DVD dvd){
            return !rentedTwoTitlesOrRentedChosenTitleAlready(memberId, dvd.getTitleId(), rentalRecords) && isTheTitleAvailable(dvd.getAvailableCopies());
        }
        return false;
    }

    public boolean memberAlreadyRentedThisTitle(RentalRecord rentalRecord, long titleId) {

        return rentalRecord.getTitleId() == titleId && !rentalRecord.isReturned();
    }

    public boolean rentedTwoTitlesOrRentedChosenTitleAlready(long memberId, long titleId, Map<Long, RentalRecord> rentalRecords) {

        int activeRentals = 0;

        for (RentalRecord rentalRecord : rentalRecords.values()){
            if (rentalRecord.getMemberId() == memberId){
                if (!memberAlreadyRentedThisTitle(rentalRecord, titleId)){
                    System.out.println("You can not rent this title because you have already rented this title.");
                    return true;
                }
                if (!rentalRecord.isReturned()){
                    activeRentals++;
                }
            }
        }
        if (activeRentals >= MAX_NUMBER_OF_RENTED_TITLES){
            System.out.println("You can not rent this title because you have already rented two titles.");
            return true;
        }
        return false;
    }

    public boolean isTheTitleAvailable(int availableCopies) {

        if (availableCopies > 0){
            return true;
        } else{
            System.out.println("Title is not currently available.");
            return false;
        }
    }
}