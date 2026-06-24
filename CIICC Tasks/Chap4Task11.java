class Book {
    String title;
    String author;
    int yearPublished;
    double price;

    public Book(String title, String author, int yearPublished, double price) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Title: \"" + title + "\"\n" +
               "Author: \"" + author + "\"\n" +
               "Year Published: " + yearPublished + "\n" +
               "Price: $" + price;
    }
}

public class Chap4Task11 {
    public static void main(String[] args) {

        System.out.println( "\033[1m"+ "BEST SELLING CYBERSECURITY BOOKS OF THE YEAR\n" + "\033[0m");

        Book book1 = new Book("The Art of Invisibility", "Kevin Mitnick", 2021, 39.99);
        Book book2 = new Book("Cybersecurity for Dummies", "Joseph Steinberg", 2020, 29.99);
        Book book3 = new Book("Cult of the Dead Cow", "Joseph Menn", 2019, 49.99);

        System.out.println( "\033[1m"+ "Book 1:" + "\033[0m");
        System.out.println(book1.toString());
        System.out.println(); // Blank line for spacing

        System.out.println( "\033[1m"+ "Book 2:" + "\033[0m");
        System.out.println(book2.toString());
        System.out.println();

        System.out.println( "\033[1m"+ "Book 3:" + "\033[0m");
        System.out.println(book3.toString());
    }
}