package academy.metis.javabasics.lesson26.activity3.exercise1.part1;

public class DVD {
    private String title;
    private String authorName;
    private int numberOfTracks;
    private final int durationInMinutes;
    private int availableCopies;
    private final long titleId;
    private final String titleType;
    public DVD(String title, String authorName, int numberOfTracks, int durationInMinutes, int availableCopies, long titleId, String titleType) {
        this.title = title;
        this.authorName = authorName;
        this.numberOfTracks = numberOfTracks;
        this.durationInMinutes = durationInMinutes;
        this.availableCopies = availableCopies;
        this.titleId = titleId;
        this.titleType = titleType;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getDurationInMinutes() {
        return durationInMinutes;
    }
    public int getNumberOfTracks() {
        return numberOfTracks;
    }
    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }
    public int getAvailableCopies() {
        return availableCopies;
    }
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
    public String getTitleType() {
        return titleType;
    }
    public long getTitleId() {
        return titleId;
    }
    @Override
    public String toString() {
        return titleId +
                " | Name: " + title +
                " | Author: " + authorName +
                " | Number of chapters: " + numberOfTracks +
                " | Length in minutes: " + durationInMinutes +
                " | Available copies: " + availableCopies;
    }
}