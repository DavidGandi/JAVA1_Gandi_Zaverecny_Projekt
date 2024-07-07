package academy.metis.javabasics.lesson27.activity3.exercise1.part1;

import java.util.Map;

public class RentalValidator {
    private static final int MAX_NUMBER_OF_RENTED_TITLES = 2;
    private static final int MAX_PROLONGATIONS = 2;

    public boolean isPossibleToRentTitle(long memberId, Object chosenTitle, Map<Long, RentalRecord> rentalRecords, Queue queue) {

        if (chosenTitle instanceof Book book) {

            if (!rentedTwoTitlesOrRentedChosenTitleAlready(memberId, book.getTitleId(), rentalRecords)) {
                if (!isTheTitleAvailable(book.getAvailableCopies())) {
                    queue.showQueueMenu(memberId, ((Book) chosenTitle).getTitleId());
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
        if (chosenTitle instanceof DVD dvd) {

            if (!rentedTwoTitlesOrRentedChosenTitleAlready(memberId, dvd.getTitleId(), rentalRecords)) {
                if (!isTheTitleAvailable(dvd.getAvailableCopies())) {
                    queue.showQueueMenu(memberId, ((DVD) chosenTitle).getTitleId());
                } else {
                    return true;
                }
            } else {
                return false;
            }
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
                if (memberAlreadyRentedThisTitle(rentalRecord, titleId)){
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
    public boolean validateMaxProlongations(int currentProlongations) {
        return currentProlongations < MAX_PROLONGATIONS;
    }

    public boolean validateIfTheTitleForProlongationIsNotInQueue(Map<Long, QueueRecord> queueRecordMap, long titleId) {
        for (QueueRecord queueRecord : queueRecordMap.values()){
            if (queueRecord.getTitleId() == titleId){
                return false;
            }
        }
        return true;
    }
}