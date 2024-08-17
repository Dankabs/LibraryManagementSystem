import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private String title; // Title of the book
    private String author; // Author of the book
    private String isbn; // ISBN of the book (unique identifier)
    private String genre; // Genre of the book
    private int publicationYear; // Publication year of the book
    private boolean isCheckedOut; // Status of whether the book is checked out
    private Member checkedOutTo; // Member who has checked out the book
    private LocalDate checkoutDate; // Date when the book was checked out
    private LocalDate dueDate; // Due date for the book return

    public Book(String title, String author, String isbn, String genre, int publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.isCheckedOut = false; // Initially, the book is not checked out
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return isbn;
    }

    public String getGenre() {
        return genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public Member getCheckedOutTo() {
        return checkedOutTo;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    // Methods for checking out and returning the book
    public void checkOut(Member member, LocalDate checkoutDate, LocalDate dueDate) {
        this.isCheckedOut = true;
        this.checkedOutTo = member;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public void returnBook() {
        this.isCheckedOut = false;
        this.checkedOutTo = null;
        this.checkoutDate = null;
        this.dueDate = null;
    }

    // Check if the book is overdue
    public boolean isOverdue() {
        return isCheckedOut && LocalDate.now().isAfter(dueDate);
    }

    // String representation of the book
    @Override
    public String toString() {
        return title + " by " + author;
    }

    // Equality based on ISBN
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
