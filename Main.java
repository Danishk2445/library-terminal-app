// to compile
// javac -cp "libs/*" *.java && java -cp "libs/*:." Main

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        GreatBooksLibrary greatBooksLibrary = new GreatBooksLibrary();
        Account currentUser = null;
        System.out.println("""
    MyLibraryList
    _______
   /      /,
  /      //
 /______//
(______(/
        """);
        while (currentUser == null) {
            System.out.println("1. Login");
            System.out.println("2. Register");;
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            System.out.println("Username: ");
            String username = scanner.nextLine();
            System.out.println("Password: ");;
            String password = scanner.nextLine();

            if (choice == 1) {
                currentUser = userManager.login(username, password);
            } else if (choice == 2) {
                userManager.createAccount(username, password);
                System.out.println("Account created! Please log in.");
            }
        }

        Library userLibrary =  currentUser.getLibrary();

        boolean running = true;
        while (running) {
            System.out.println("\nLibrary App - Choose an option:");
            System.out.println("1. Add a book");
            System.out.println("2. List all books");
            System.out.println("3. Rate a book");
            System.out.println("4. Update book status");
            System.out.println("5. List books by status");
            System.out.println("6. View Great Books Library");
            System.out.println("7. Add a book from Great Books Library");
            System.out.println("8. Search a book");
            System.out.println("9. Search a book in great books library");
            System.out.println("10. Download a Book");
            System.out.println("11. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch(choice) {
                case 1:
                    System.out.println("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.println("Enter book author: ");
                    String author = scanner.nextLine();
                    userLibrary.addBook(title, author);
                    break;
                case 2:
                    userLibrary.listBooks();
                    break;
                case 3:
                    System.out.println("Enter book index to rate: ");
                    int rateIndex = scanner.nextInt() - 1;
                    System.out.println("Enter rating (1-10): ");
                    int rating = scanner.nextInt();
                    userLibrary.rateBooks(rateIndex, rating);
                    break;
                case 4:
                    System.out.println("Enter book index to update status: ");
                    int statusIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // consume newline
                    System.out.println("Enter status (To Read/Reading/Read): ");
                    String status = scanner.nextLine();
                    userLibrary.updateStatus(statusIndex, status);
                    break;
                case 5:
                    System.out.println("Enter status to list (To Read/Reading/Read): ");
                    String listStatus = scanner.nextLine();
                    userLibrary.listByStatus(listStatus);
                    break;
                case 6:
                    greatBooksLibrary.listRecommendedBooks();
                    break;
                case 7:
                    greatBooksLibrary.listRecommendedBooks();
                    System.out.println("Enter book index to add to your library");
                    int addIndex = scanner.nextInt() - 1;
                    Book recommendedBook = greatBooksLibrary.getBook(addIndex);
                    if (recommendedBook != null) {
                        userLibrary.addBook(recommendedBook.getTitle(), recommendedBook.getAuthor());
                    } else {
                        System.out.println("Invalid book selection.");
                    }
                    break;
                case 8:
                    System.out.println("Enter a search query (title or author): ");
                    String query = scanner.nextLine();
                    userLibrary.searchBooks(query);
                    break;
                case 9:
                    System.out.println("Enter a search query (title or author): ");
                    String greatLibraryQuery = scanner.nextLine();
                    greatBooksLibrary.searchRecommendedBooks(greatLibraryQuery);
                    break;
                case 10:
                    downloadBook(scanner);
                    break;
                case 11:
                    running = false;
                    System.out.println("Exiting the app.");
                    break;
                default:
                    System.out.println("Invalid choice. Choose (1-6).");
            }
        }
        
        userManager.saveAccounts();
        scanner.close();
    }

    private static void downloadBook(Scanner scanner) {
        System.out.println("Enter book title for search");
        String title = scanner.nextLine();
        System.out.println("Choose category:");
        System.out.println("1. Fiction");
        System.out.println("2. Non-Fiction");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();
        String baseUrl;
        if (categoryChoice == 1) {
            baseUrl = "https://libgen.is/fiction/?q=";
        } else if (categoryChoice == 2) {
            baseUrl = "https://libgen.is/search.php?req=";
        } else {
            System.out.println("Invalid Choice. Please select 1 for Fiction or 2 for Non-Fiction");
            return;
        }

        String formattedTitle = title.replace(" ", "+");
        String searchUrl = baseUrl + formattedTitle;
        try {
            Desktop.getDesktop().browse(new URI(searchUrl));   
            System.out.println("Opening search base: " + searchUrl);
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open browser: " + e.getMessage());
        }
    }
}
