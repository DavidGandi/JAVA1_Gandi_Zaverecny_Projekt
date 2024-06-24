package academy.metis.javabasics.lesson22.activity3.exercise1.part1;

public class Book {
    private String title;
    private String authorName;
    private String isbn;
    private final int pageCount;
    private int availableCopies;
    private final long titleId;
    private final String titleType;

    public Book(String title, String authorName, String isbn, int pageCount, int availableCopies, long titleId, String titleType) {
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.pageCount = pageCount;
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
    public int getPageCount() {
        return pageCount;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
                " | ISBN: " + isbn +
                " | Number of pages: " + pageCount +
                " | Available copies: " + availableCopies;
    }
}