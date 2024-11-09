public class Book {
    private String title;
    private String author;
    private int rating;
    private String status;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.rating = 0;
        this.status = "To Read";
    }
    

    // getters and setters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getRating() { return rating; }
    public String getStatus() { return status; }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 10)
            this.rating = rating;
        else
            System.out.println("Invalid Rating! Please rate between 1 and 10.");
    }

    public void setStatus(String status) {
        if (status.equals("To Read") || status.equals("Reading") || status.equals("Read"))
            this.status = status;
        else
            System.out.println("Invalid status! Use 'To Read', 'Reading', or 'Read'.");
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Rating: " + rating + ", Status: " + status;
    }
}