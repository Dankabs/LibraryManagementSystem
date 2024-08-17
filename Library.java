import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books; // List to hold all books in the library
    private List<Member> members; // List to hold all members of the library

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    // Book methods
    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    // Search for books by title, author, ISBN, or genre
    public List<Book> searchBooks(String query) {
        return books.stream()
            .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                            book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                            book.getISBN().toLowerCase().contains(query.toLowerCase()) ||
                            book.getGenre().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    // Get a copy of the list of books
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    // Member methods
    public void addMember(Member member) {
        members.add(member);
    }

    public void removeMember(Member member) {
        members.remove(member);
    }

    // Search for members by name or ID
    public List<Member> searchMembers(String query) {
        return members.stream()
            .filter(member -> member.getName().toLowerCase().contains(query.toLowerCase()) ||
                              member.getId().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    // Get a copy of the list of members
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    // Checkout a book to a member
    public void checkoutBook(Book book, Member member, int loanDays) {
        if (!book.isCheckedOut()) {
            LocalDate checkoutDate = LocalDate.now();
            LocalDate dueDate = checkoutDate.plusDays(loanDays);
            book.checkOut(member, checkoutDate, dueDate);
        } else {
            throw new IllegalStateException("Book is already checked out.");
        }
    }

    // Return a checked-out book
    public void returnBook(Book book) {
        if (book.isCheckedOut()) {
            book.returnBook();
        } else {
            throw new IllegalStateException("Book is not checked out.");
        }
    }

    // Get a list of overdue books
    public List<Book> getOverdueBooks() {
        return books.stream()
            .filter(Book::isOverdue)
            .collect(Collectors.toList());
    }

    // Get a list of books checked out by a specific member
    public List<Book> getBooksCheckedOutByMember(Member member) {
        return books.stream()
            .filter(book -> book.isCheckedOut() && book.getCheckedOutTo().equals(member))
            .collect(Collectors.toList());
    }
}
