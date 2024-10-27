import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Available: " + isAvailable;
    }
}

class Member {
    private String name;
    private String memberId;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();
    public static final int MAX_BORROWED_BOOKS = 5;

    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) throws Exception {
        if (borrowedBooks.size() >= MAX_BORROWED_BOOKS) {
            throw new Exception("Member has already borrowed the maximum number of books.");
        }
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", ID: " + memberId + ", Borrowed Books: " + borrowedBooks.size();
    }
}

class Loan {
    private Member member;
    private Book book;
    private Date borrowDate;
    private Date returnDate;

    public Loan(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.borrowDate = new Date();
    }

    public void returnBook() {
        this.returnDate = new Date();
    }

    @Override
    public String toString() {
        return "Member: " + member.getMemberId() + ", Book: " + book.getIsbn() + ", Borrowed On: " + borrowDate +
                ", Returned On: " + (returnDate != null ? returnDate : "Not returned yet");
    }
}

public class LibraryManagementSystem {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Loan> loans = new ArrayList<>();

    public void addBook(String title, String author, String isbn) {
        try {
            Book book = new Book(title, author, isbn);
            books.add(book);
            System.out.println("Book added: " + book);
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public void registerMember(String name, String memberId) {
        try {
            Member member = new Member(name, memberId);
            members.add(member);
            System.out.println("Member registered: " + member);
        } catch (Exception e) {
            System.out.println("Error registering member: " + e.getMessage());
        }
    }

    public void borrowBook(String memberId, String isbn) {
        try {
            Member member = findMember(memberId);
            if (member == null) {
                throw new Exception("Member not found.");
            }

            Book book = findBook(isbn);
            if (book == null) {
                throw new Exception("Book not found.");
            }

            if (!book.isAvailable()) {
                throw new Exception("Book is currently unavailable.");
            }

            member.borrowBook(book);
            book.setAvailable(false);
            Loan loan = new Loan(member, book);
            loans.add(loan);
            System.out.println("Book borrowed: " + book + " by " + member);
        } catch (Exception e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        }
    }

    public void returnBook(String memberId, String isbn) {
        try {
            Member member = findMember(memberId);
            if (member == null) {
                throw new Exception("Member not found.");
            }

            Book book = findBook(isbn);
            if (book == null) {
                throw new Exception("Book not found.");
            }

            if (book.isAvailable()) {
                throw new Exception("This book is not currently borrowed.");
            }

            member.returnBook(book);
            book.setAvailable(true);
            System.out.println("Book returned: " + book + " by " + member);
        } catch (Exception e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }
    private Member findMember(String memberId){
        for (Member member : members){
            if(member.getMemberId().equals(memberId)){
                return member;
            }
        }
        return null;
    }
    private Book findBook(String isbn){
        for(Book book : books) {
            if(book.getIsbn().equals(isbn)){
                return book;
            }
        }
        return null;
    }

   

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryManagementSystem library = new LibraryManagementSystem();

        while (true) {
            System.out.println("\nLibrary Management System:");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String isbn = scanner.nextLine();
                    library.addBook(title, author, isbn);
                    break;
                case 2:
                    System.out.print("Enter member name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter member ID: ");
                    String memberId = scanner.nextLine();
                    library.registerMember(name, memberId);
                    break;
                case 3:
                    System.out.print("Enter member ID: ");
                    String borrowMemberId = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String borrowIsbn = scanner.nextLine();
                    library.borrowBook(borrowMemberId, borrowIsbn);
                    break;
                case 4:
                    System.out.print("Enter member ID: ");
                    String returnMemberId = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String returnIsbn = scanner.nextLine();
                    library.returnBook(returnMemberId, returnIsbn);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
