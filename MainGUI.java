import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainGUI extends JPanel {
    private Library library; // Instance of the library for book and member management
    private JTabbedPane tabbedPane; // Tabbed pane to switch between different panels
    private JComboBox<Member> memberComboBox; // Combo box for selecting members
    private JComboBox<Book> bookComboBox; // Combo box for selecting books

    public MainGUI() {
        // Initialize library, combo boxes, and components
        library = new Library();
        memberComboBox = new JComboBox<>();
        bookComboBox = new JComboBox<>();
        initComponents(); // Set up the GUI components
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Create and configure the tabbed pane with different panels
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Book Management", createBookPanel());
        tabbedPane.addTab("Member Management", createMemberPanel());
        tabbedPane.addTab("Checkout/Return", createCheckoutPanel());

        add(tabbedPane, BorderLayout.CENTER); // Add the tabbed pane to the panel
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create and configure input fields and buttons for book management
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField isbnField = new JTextField(15);
        JTextField genreField = new JTextField(15);
        JTextField yearField = new JTextField(4);
        JButton addButton = new JButton("Add Book");
        addButton.setFocusable(false);
        JButton editButton = new JButton("Edit Book");
        editButton.setFocusable(false);
        JButton deleteButton = new JButton("Delete Book");
        deleteButton.setFocusable(false);
        JButton searchButton = new JButton("Search");
        JTextField searchField = new JTextField(20);
        DefaultListModel<Book> bookListModel = new DefaultListModel<>();
        JList<Book> bookList = new JList<>(bookListModel);
        JScrollPane scrollPane = new JScrollPane(bookList);

        // Panel for book input fields
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("ISBN:"));
        inputPanel.add(isbnField);
        inputPanel.add(new JLabel("Genre:"));
        inputPanel.add(genreField);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);
        inputPanel.add(addButton);
        inputPanel.add(editButton);
        inputPanel.add(deleteButton);

        // Panel for search functionality
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add components to the main panel
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        addButton.addActionListener(e -> {
            addBook(titleField, authorField, isbnField, genreField, yearField, bookListModel);
            updateBookComboBox(); // Update combo box after adding a book
        });
        editButton.addActionListener(e -> editBook(bookList, titleField, authorField, isbnField, genreField, yearField));
        deleteButton.addActionListener(e -> {
            deleteBook(bookList, bookListModel);
            updateBookComboBox(); // Update combo box after deleting a book
        });
        searchButton.addActionListener(e -> searchBooks(searchField.getText(), bookListModel));

        return panel;
    }

    private void addBook(JTextField titleField, JTextField authorField, JTextField isbnField,
                         JTextField genreField, JTextField yearField, DefaultListModel<Book> listModel) {
        try {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String genre = genreField.getText();
            int year = Integer.parseInt(yearField.getText());

            Book newBook = new Book(title, author, isbn, genre, year);
            library.addBook(newBook);
            listModel.addElement(newBook);

            // Clear the input fields after adding the book
            titleField.setText("");
            authorField.setText("");
            isbnField.setText("");
            genreField.setText("");
            yearField.setText("");
        } catch (NumberFormatException ex) {
            // Show error message if year format is invalid
            JOptionPane.showMessageDialog(this, "Invalid year format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editBook(JList<Book> bookList, JTextField titleField, JTextField authorField,
                          JTextField isbnField, JTextField genreField, JTextField yearField) {
        Book selectedBook = bookList.getSelectedValue();
        if (selectedBook != null) {
            try {
                selectedBook.setTitle(titleField.getText());
                selectedBook.setAuthor(authorField.getText());
                selectedBook.setISBN(isbnField.getText());
                selectedBook.setGenre(genreField.getText());
                selectedBook.setPublicationYear(Integer.parseInt(yearField.getText()));

                DefaultListModel<Book> model = (DefaultListModel<Book>) bookList.getModel();
                int index = model.indexOf(selectedBook);
                if (index != -1) {
                    model.set(index, selectedBook);
                } else {
                    model.addElement(selectedBook);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid year format. Please enter a valid year.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBook(JList<Book> bookList, DefaultListModel<Book> listModel) {
        int selectedIndex = bookList.getSelectedIndex();
        if (selectedIndex != -1) {
            Book bookToRemove = listModel.getElementAt(selectedIndex);
            library.removeBook(bookToRemove);
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to delete", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchBooks(String query, DefaultListModel<Book> listModel) {
        listModel.clear();
        List<Book> searchResults = library.searchBooks(query);
        for (Book book : searchResults) {
            listModel.addElement(book);
        }
    }

    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create and configure input fields and buttons for member management
        JTextField nameField = new JTextField(20);
        JTextField idField = new JTextField(10);
        JTextField contactField = new JTextField(20);
        JButton addButton = new JButton("Add Member");
        JButton editButton = new JButton("Edit Member");
        JButton deleteButton = new JButton("Delete Member");
        JButton searchButton = new JButton("Search");
        JTextField searchField = new JTextField(20);
        DefaultListModel<Member> memberListModel = new DefaultListModel<>();
        JList<Member> memberList = new JList<>(memberListModel);
        JScrollPane scrollPane = new JScrollPane(memberList);

        // Panel for member input fields
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Member ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Contact:"));
        inputPanel.add(contactField);
        inputPanel.add(addButton);
        inputPanel.add(editButton);
        inputPanel.add(deleteButton);

        // Panel for search functionality
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add components to the main panel
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        addButton.addActionListener(e -> {
            addMember(nameField, idField, contactField, memberListModel);
            updateMemberComboBox(); // Update combo box after adding a member
        });
        editButton.addActionListener(e -> editMember(memberList, nameField, idField, contactField));
        deleteButton.addActionListener(e -> {
            deleteMember(memberList, memberListModel);
            updateMemberComboBox(); // Update combo box after deleting a member
        });
        searchButton.addActionListener(e -> searchMembers(searchField.getText(), memberListModel));

        return panel;
    }

    private void addMember(JTextField nameField, JTextField idField, JTextField contactField, DefaultListModel<Member> listModel) {
        String name = nameField.getText();
        String id = idField.getText();
        String contact = contactField.getText();

        if (!name.isEmpty() && !id.isEmpty()) {
            Member newMember = new Member(name, id, contact);
            library.addMember(newMember);
            listModel.addElement(newMember);

            // Clear the input fields after adding the member
            nameField.setText("");
            idField.setText("");
            contactField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Name and Member ID are required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editMember(JList<Member> memberList, JTextField nameField, JTextField idField, JTextField contactField) {
        Member selectedMember = memberList.getSelectedValue();
        if (selectedMember != null) {
            selectedMember.setName(nameField.getText());
            selectedMember.setId(idField.getText());
            selectedMember.setContact(contactField.getText());

            DefaultListModel<Member> model = (DefaultListModel<Member>) memberList.getModel();
            int index = model.indexOf(selectedMember);
            if (index != -1) {
                model.set(index, selectedMember);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteMember(JList<Member> memberList, DefaultListModel<Member> listModel) {
        int selectedIndex = memberList.getSelectedIndex();
        if (selectedIndex != -1) {
            Member memberToRemove = listModel.getElementAt(selectedIndex);
            library.removeMember(memberToRemove);
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to delete", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchMembers(String query, DefaultListModel<Member> listModel) {
        listModel.clear();
        List<Member> searchResults = library.searchMembers(query);
        for (Member member : searchResults) {
            listModel.addElement(member);
        }
    }

    private JPanel createCheckoutPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create and configure buttons and status area for checkout and return
        JButton checkoutButton = new JButton("Checkout");
        JButton returnButton = new JButton("Return");
        JTextArea statusArea = new JTextArea(10, 40);
        statusArea.setEditable(false);

        // Panel for input fields and buttons
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Member:"));
        inputPanel.add(memberComboBox);
        inputPanel.add(new JLabel("Book:"));
        inputPanel.add(bookComboBox);
        inputPanel.add(checkoutButton);
        inputPanel.add(returnButton);

        // Add components to the main panel
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(statusArea), BorderLayout.CENTER);

        // Update combo boxes with current data
        updateMemberComboBox();
        updateBookComboBox();

        // Add action listeners for buttons
        checkoutButton.addActionListener(e -> checkoutBook(memberComboBox, bookComboBox, statusArea));
        returnButton.addActionListener(e -> returnBook(bookComboBox, statusArea));

        // Button to check overdue books
        JButton overdueButton = new JButton("Check Overdue Books");
        overdueButton.addActionListener(e -> checkOverdueBooks(statusArea));
        panel.add(overdueButton, BorderLayout.SOUTH);

        return panel;
    }

    private void updateMemberComboBox() {
        memberComboBox.removeAllItems();
        for (Member member : library.getMembers()) {
            memberComboBox.addItem(member);
        }
    }

    private void updateBookComboBox() {
        bookComboBox.removeAllItems();
        for (Book book : library.getBooks()) {
            if (!book.isCheckedOut()) {
                bookComboBox.addItem(book);
            }
        }
    }

    private void checkoutBook(JComboBox<Member> memberComboBox, JComboBox<Book> bookComboBox, JTextArea statusArea) {
        Member selectedMember = (Member) memberComboBox.getSelectedItem();
        Book selectedBook = (Book) bookComboBox.getSelectedItem();
        
        if (selectedMember != null && selectedBook != null) {
            try {
                library.checkoutBook(selectedBook, selectedMember, 14);
                statusArea.append("Book checked out successfully: " + selectedBook + " to " + selectedMember + "\n");
                updateBookComboBox(); // Update book combo box after checkout
                updateMemberComboBox(); // Update member combo box after checkout
            } catch (IllegalStateException e) {
                statusArea.append("Error: " + e.getMessage() + "\n");
            }
        } else {
            statusArea.append("Please select both a member and a book.\n");
        }
    }

    private void returnBook(JComboBox<Book> bookComboBox, JTextArea statusArea) {
        Book selectedBook = (Book) bookComboBox.getSelectedItem();
        
        if (selectedBook != null) {
            try {
                library.returnBook(selectedBook);
                statusArea.append("Book returned successfully: " + selectedBook + "\n");
                updateBookComboBox(); // Update book combo box after return
                updateMemberComboBox(); // Update member combo box after return
            } catch (IllegalStateException e) {
                statusArea.append("Error: " + e.getMessage() + "\n");
            }
        } else {
            statusArea.append("Please select a book to return.\n");
        }
    }

    private void checkOverdueBooks(JTextArea statusArea) {
        List<Book> overdueBooks = library.getOverdueBooks();
        if (overdueBooks.isEmpty()) {
            statusArea.append("No overdue books.\n");
        } else {
            statusArea.append("Overdue books:\n");
            for (Book book : overdueBooks) {
                statusArea.append(book + " - Due date: " + book.getDueDate() + "\n");
            }
        }
    }
}
