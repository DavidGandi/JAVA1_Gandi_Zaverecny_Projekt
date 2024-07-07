package academy.metis.javabasics.lesson26.activity3.exercise1.part1;

public class Member {
    private final String firstName;
    private final String surname;
    private final String dateOfBirth;
    private final long memberId;

    public Member(String firstName, String surname, String dateOfBirth, long memberId) {
        this.firstName = firstName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.memberId = memberId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getSurname() {
        return surname;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public long getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return memberId + ". | " +
                firstName + " " +
                surname + " | Date of Birth: " +
                dateOfBirth;
    }
}