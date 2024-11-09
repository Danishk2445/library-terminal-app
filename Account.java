

public class Account {
    private String username;
    private String password;
    private Library library;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.library = new Library(); // each user has their own personal library
    }

    public String getUsername() { return username; }
    public Boolean checkPassword(String password) { return this.password.equals(password); }
    public Library getLibrary() { return library; }

    
}
