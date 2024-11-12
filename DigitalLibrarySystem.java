import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DigitalLibrarySystem extends Frame {
    private TextField titleField, authorField, filePathField, searchField;
    private TextArea displayArea, searchResultArea;
    private Map<String, BookMetadata> library;

    public DigitalLibrarySystem() {
        library = new HashMap<>();
        setTitle("Digital Library System");
        setSize(600, 500);
        setLayout(new BorderLayout());

        // Panel for input fields and buttons
        Panel topPanel = new Panel(new GridLayout(3, 1));

        // Row 1: Title and Author
        Panel row1 = new Panel(new FlowLayout());
        titleField = new TextField(10);
        authorField = new TextField(10);
        row1.add(new Label("Title:"));
        row1.add(titleField);
        row1.add(new Label("Author:"));
        row1.add(authorField);
        
        // Row 2: File Path for upload
        Panel row2 = new Panel(new FlowLayout());
        filePathField = new TextField(20);
        row2.add(new Label("File Path:"));
        row2.add(filePathField);
        
        // Row 3: Operation buttons and Search
        Panel row3 = new Panel(new FlowLayout());
        Button uploadButton = new Button("Upload");
        Button downloadButton = new Button("Download");
        Button readButton = new Button("Read Book");
        Button searchButton = new Button("Search Content");
        Button searchMetadataButton = new Button("Search Metadata");
        searchField = new TextField(10);

        uploadButton.addActionListener(e -> uploadBook());
        downloadButton.addActionListener(e -> downloadBook());
        readButton.addActionListener(e -> readBook());
        searchButton.addActionListener(e -> searchContent());
        searchMetadataButton.addActionListener(e -> searchMetadata());

        row3.add(uploadButton);
        row3.add(downloadButton);
        row3.add(readButton);
        row3.add(new Label("Search Keyword:"));
        row3.add(searchField);
        row3.add(searchButton);
        row3.add(searchMetadataButton);

        // Add rows to top panel
        topPanel.add(row1);
        topPanel.add(row2);
        topPanel.add(row3);
        add(topPanel, BorderLayout.NORTH);

        // Panel for display areas
        Panel middlePanel = new Panel(new BorderLayout());
        displayArea = new TextArea(10, 50);
        searchResultArea = new TextArea(5, 50);
        searchResultArea.setEditable(false);
        displayArea.setEditable(false);

        middlePanel.add(new Label("Search Results:"), BorderLayout.NORTH);
        middlePanel.add(searchResultArea, BorderLayout.CENTER);
        middlePanel.add(displayArea, BorderLayout.SOUTH);

        add(middlePanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void uploadBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String filePath = filePathField.getText();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             FileWriter writer = new FileWriter(title + ".txt")) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }

            library.put(title, new BookMetadata(title, author, title + ".txt"));
            displayArea.setText("Book uploaded successfully: " + title);

        } catch (IOException e) {
            displayArea.setText("Error uploading book: " + e.getMessage());
        }
    }

    private void downloadBook() {
        String title = titleField.getText();
        if (!library.containsKey(title)) {
            displayArea.setText("Book not found in library.");
            return;
        }

        String filePath = library.get(title).getFilePath();
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            displayArea.setText("Downloaded Book Content:\n" + content);

        } catch (IOException e) {
            displayArea.setText("Error downloading book: " + e.getMessage());
        }
    }

    private void readBook() {
        String title = titleField.getText();
        if (!library.containsKey(title)) {
            displayArea.setText("Book not found in library.");
            return;
        }

        String filePath = library.get(title).getFilePath();
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            displayArea.setText("Read Book Content:\n" + content);

        } catch (IOException e) {
            displayArea.setText("Error reading book: " + e.getMessage());
        }
    }

    private void searchContent() {
        String keyword = searchField.getText();
        String title = titleField.getText();

        if (!library.containsKey(title)) {
            displayArea.setText("Book not found in library.");
            return;
        }

        String filePath = library.get(title).getFilePath();
        StringBuilder result = new StringBuilder("Search Results:\n");
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(keyword)) {
                    result.append(line).append("\n");
                    found = true;
                }
            }

            if (!found) {
                searchResultArea.setText("No matches found for keyword: " + keyword);
            } else {
                searchResultArea.setText(result.toString());
            }
        } catch (IOException e) {
            displayArea.setText("Error searching content: " + e.getMessage());
        }
    }

    private void searchMetadata() {
        String keyword = searchField.getText().toLowerCase();
        StringBuilder result = new StringBuilder("Metadata Search Results:\n");

        boolean found = false;
        for (BookMetadata book : library.values()) {
            if (book.getTitle().toLowerCase().contains(keyword) || book.getAuthor().toLowerCase().contains(keyword)) {
                result.append("Title: ").append(book.getTitle()).append("\n");
                result.append("Author: ").append(book.getAuthor()).append("\n");
                result.append("File Path: ").append(book.getFilePath()).append("\n\n");
                found = true;
            }
        }

        if (!found) {
            searchResultArea.setText("No books found matching the search criteria.");
        } else {
            searchResultArea.setText(result.toString());
        }
    }

    public static void main(String[] args) {
        DigitalLibrarySystem librarySystem = new DigitalLibrarySystem();
        librarySystem.setVisible(true);
    }

    class BookMetadata {
        private String title;
        private String author;
        private String filePath;

        public BookMetadata(String title, String author, String filePath) {
            this.title = title;
            this.author = author;
            this.filePath = filePath;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getFilePath() {
            return filePath;
        }
    }
}
