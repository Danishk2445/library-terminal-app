import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Book added successfully!");
    }

    public void listBooks() {
        if (books.isEmpty()) 
            System.out.println("No books in library");
        else {
            for (int i = 0; i < books.size(); i++)
                System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    public void rateBooks(int index, int rating) {
        if (index >= 0 && index < books.size()) {
            books.get(index).setRating(rating);
            System.out.println("Rating updated!");
        } else
            System.out.println("Invalid book index.");
    }

    public void updateStatus(int index, String status) {
        if (index >= 0 && index < books.size()) {
            books.get(index).setStatus(status);
            System.out.println("Status updated!");
        } else
            System.out.println("Invalid book index");
    }

    public void listByStatus(String status) {
        System.out.println("Books with status: " + status);
        for (Book book : books) {
            if (book.getStatus().equals(status))
                System.out.println(book);
        }
    }

    public void searchBooks(String query) {
        System.out.println("Search results for \"" + query + "\":");
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                    System.out.println(book);
                    found = true;
                }
        }
        if (!found) {
            System.out.println("No books found matching \"" + query + "\".");
        }
    }

}
