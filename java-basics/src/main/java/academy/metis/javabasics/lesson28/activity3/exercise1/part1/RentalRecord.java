package academy.metis.javabasics.lesson28.activity3.exercise1.part1;

import java.time.LocalDate;

public class RentalRecord {

    public static final int MAXIMUM_RENT_DURATION_IN_DAYS = 21;
    private final Long rentalRecordId;
    private final Long memberId;
    private final LocalDate rentedDate;
    private LocalDate returnDate;
    private final Long titleId;
    private int timesProlonged;
    private boolean isReturned;

    public RentalRecord(Long rentalRecordId, Long memberId, LocalDate rentedDate, LocalDate returnDate, Long titleId, int timesProlonged, boolean isReturned) {
        this.rentalRecordId = rentalRecordId;
        this.memberId = memberId;
        this.rentedDate = rentedDate;
        this.returnDate = returnDate;
        this.titleId = titleId;
        this.timesProlonged = timesProlonged;
        this.isReturned = isReturned;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public void setTimesProlonged(int timesProlonged) {
        this.timesProlonged = timesProlonged;
    }
    public boolean isReturned() {
        return isReturned;
    }
    public void setReturned(boolean returned) {
        this.isReturned = returned;
    }
    public Long getRentalRecordId() {
        return rentalRecordId;
    }
    public Long getMemberId() {
        return memberId;
    }
    public LocalDate getRentedDate() {
        return rentedDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public Long getTitleId() {
        return titleId;
    }
    public int getTimesProlonged() {
        return timesProlonged;
    }
    @Override
    public String toString() {
        return rentalRecordId +
                ". | MemberID = " + memberId +
                " | Rented date = " + rentedDate +
                " | Return Date = " + returnDate +
                " | TitleID = " + titleId +
                " | Times Prolonged = " + timesProlonged +
                " | Returned = " + ((isReturned) ? "RETURNED" : "NOT RETURNED");
    }
}