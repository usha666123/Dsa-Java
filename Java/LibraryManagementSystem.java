
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// ============================================================================
// 1. CUSTOM EXCEPTIONS
// ============================================================================
class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) { super(message); }
}

class BookUnavailableException extends Exception {
    public BookUnavailableException(String message) { super(message); }
}

// ============================================================================
// 2. MODEL CLASSES
// ============================================================================
class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String title;
    private String author;
    private String category;
    private boolean available;

    public Book(int id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("ID: %d | Title: %s | Author: %s | Category: %s | Status: %s",
                id, title, author, category, available ? "Available" : "Issued");
    }
}

class User {
    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}

// ============================================================================
// 3. SERVICE / MANAGEMENT CLASS
// ============================================================================
class LibraryManager {
    private List<Book> bookList;
    private Map<Integer, Book> bookMap; // HashMap for O(1) fast lookup
    private final String FILE_NAME = "library_data.ser";

    public LibraryManager() {
        this.bookList = new ArrayList<>();
        this.bookMap = new HashMap<>();
        loadBooksFromFile(); // Auto-load data on startup
    }

    public void addBook(Book book) {
        if (bookMap.containsKey(book.getId())) {
            System.out.println("❌ Error: A book with this ID already exists.");
            return;
        }
        bookList.add(book);
        bookMap.put(book.getId(), book);
        System.out.println("✅ Book added successfully!");
    }

    public void removeBook(int id) throws BookNotFoundException {
        if (!bookMap.containsKey(id)) {
            throw new BookNotFoundException("❌ Error: Book ID " + id + " not found.");
        }
        Book book = bookMap.get(id);
        bookList.remove(book);
        bookMap.remove(id);
        System.out.println("✅ Book removed successfully!");
    }

    public Book searchBook(int id) throws BookNotFoundException {
        if (!bookMap.containsKey(id)) {
            throw new BookNotFoundException("❌ Error: Book ID " + id + " not found.");
        }
        return bookMap.get(id);
    }

    public void issueBook(int id) throws BookNotFoundException, BookUnavailableException {
        Book book = searchBook(id);
        if (!book.isAvailable()) {
            throw new BookUnavailableException("❌ Error: Book '" + book.getTitle() + "' is already issued.");
        }
        book.setAvailable(false);
        System.out.println("✅ Book '" + book.getTitle() + "' has been successfully issued.");
    }

    public void returnBook(int id) throws BookNotFoundException {
        Book book = searchBook(id);
        if (book.isAvailable()) {
            System.out.println("⚠️ Warning: This book was already marked as available.");
            return;
        }
        book.setAvailable(true);
        System.out.println("✅ Book '" + book.getTitle() + "' has been successfully returned.");
    }

    public void displayAllBooks() {
        if (bookList.isEmpty()) {
            System.out.println("📂 The library is currently empty.");
            return;
        }
        System.out.println("\n--- Library Catalog ---");
        bookList.forEach(System.out::println);
    }

    // BONUS: Filter books using Java Streams
    public void filterByCategory(String category) {
        List<Book> filtered = bookList.stream()
                .filter(b -> b.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
        
        displayFilteredResults(filtered, "Category: " + category);
    }

    public void filterByAvailability(boolean available) {
        List<Book> filtered = bookList.stream()
                .filter(b -> b.isAvailable() == available)
                .collect(Collectors.toList());
        
        displayFilteredResults(filtered, available ? "Available Books" : "Issued Books");
    }

    private void displayFilteredResults(List<Book> results, String criteria) {
        if (results.isEmpty()) {
            System.out.println("🔍 No books found matching criteria: " + criteria);
        } else {
            System.out.println("\n--- Filtered Results (" + criteria + ") ---");
            results.forEach(System.out::println);
        }
    }

    // File Handling: Serialization
    public void saveBooksToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(bookList);
            System.out.println("💾 Library data saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("❌ Error saving data to file: " + e.getMessage());
        }
    }

    // File Handling: Deserialization
    @SuppressWarnings("unchecked")
    public void loadBooksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // No layout data to pull from on first run
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            bookList = (ArrayList<Book>) ois.readObject();
            bookMap.clear();
            for (Book book : bookList) {
                bookMap.put(book.getId(), book);
            }
            System.out.println("📂 Library data loaded from history file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ Could not load history file: " + e.getMessage());
        }
    }
}

// ============================================================================
// 4. MAIN ENTRY POINT
// ============================================================================
public class LibraryManagementSystem {
    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("======= Welcome to the Console Library System =======");

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book by ID");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Display All Books");
            System.out.println("7. Filter Books by Category (Stream Bonus)");
            System.out.println("8. Filter Books by Availability (Stream Bonus)");
            System.out.println("9. Save and Exit");
            System.out.print("Select an option (1-9): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid Choice! Please input a digit from 1 to 9.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Book ID (Integer): ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter Category: ");
                        String category = scanner.nextLine();
                        
                        manager.addBook(new Book(id, title, author, category));
                        break;

                    case 2:
                        System.out.print("Enter Book ID to remove: ");
                        int removeId = Integer.parseInt(scanner.nextLine());
                        manager.removeBook(removeId);
                        break;

                    case 3:
                        System.out.print("Enter Book ID to search: ");
                        int searchId = Integer.parseInt(scanner.nextLine());
                        Book foundBook = manager.searchBook(searchId);
                        System.out.println("🔍 Found Book -> " + foundBook);
                        break;

                    case 4:
                        System.out.print("Enter Book ID to issue: ");
                        int issueId = Integer.parseInt(scanner.nextLine());
                        manager.issueBook(issueId);
                        break;

                    case 5:
                        System.out.print("Enter Book ID to return: ");
                        int returnId = Integer.parseInt(scanner.nextLine());
                        manager.returnBook(returnId);
                        break;
                    case 6:manager.displayAllBooks();
                             break;
                    case 7:System.out.print("Enter Category name: ");
                        String catName = scanner.nextLine();    
                        manager.filterByCategory(catName);
                          break;
                    case 8:System.out.print("Show (1) Available Books or (2) Issued Books? Enter choice: ");
                        int availChoice = Integer.parseInt(scanner.nextLine());
                        manager.filterByAvailability(availChoice == 1);
                        break;
                    case 9:manager.saveBooksToFile();
                        System.out.println("👋 Goodbye!");
                        scanner.close();
                        System.exit(0);
                    default:System.out.println("❌ Out of range. Choose options 1 to 9.");
                }}
                     catch (BookNotFoundException | BookUnavailableException e) {System.out.println(e.getMessage());

                     } catch (NumberFormatException e) {System.out.println("❌ Numeric conversion error. Ensure IDs and option codes are numbers.");

                     }
                      catch (Exception e) {
                        System.out.println("❌ An unexpected tracking error occurred: " + e.getMessage());
                    }
                }
             }
            }
