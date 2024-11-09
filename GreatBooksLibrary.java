import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GreatBooksLibrary {
    private List<Book> recommendedBooks;

    public GreatBooksLibrary() {
        recommendedBooks = new ArrayList<>();
        loadRecommendedBooks();
    }

    private void loadRecommendedBooks() {
        Gson gson = new Gson();
        try {
            String json = new String(Files.readAllBytes(Paths.get("recommended_books.json")));
            Type bookListType = new TypeToken<ArrayList<Book>>() {}.getType();
            recommendedBooks = gson.fromJson(json, bookListType);
            System.out.println("Recommended books loaded successfully.");
        } catch(IOException e) {
            System.out.println("Error loading recommended books: " + e.getMessage());
        }
    }

    public void listRecommendedBooks() {
        System.out.println("Great Books Library:");
        for (int i = 0; i < recommendedBooks.size(); i++) {
            System.out.println(i + 1 + ". " + recommendedBooks.get(i));
        }
    }

    public Book getBook(int index) {
        if (index >= 0 && index < recommendedBooks.size()) {
            return recommendedBooks.get(index);
        }
        return null;
    }

    public void searchRecommendedBooks(String query) {
        System.out.println("Search results for \"" + query + "\":");
        boolean found = false;
        for (Book book : recommendedBooks) {
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
