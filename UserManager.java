import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<Account> accounts;
    private static final String FILE_PATH = "accounts.json";
    private Gson gson = new Gson();

    public UserManager() {
        accounts = loadAccounts(); // load accounts from JSON
    }

    public Account login(String username, String password) {
        String hashedPassword = hashPassword(password);
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.checkPassword(hashedPassword))
                return account;
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    public void createAccount(String username, String password) {
        String hashedPassword = hashPassword(password);
        accounts.add(new Account(username, hashedPassword));
        saveAccounts();
    }

    private List<Account> loadAccounts() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type accountListType = new TypeToken<ArrayList<Account>>() {}.getType();
            return gson.fromJson(reader, accountListType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void saveAccounts() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(accounts, writer);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing SHA-256", e);
        }
    }
}
