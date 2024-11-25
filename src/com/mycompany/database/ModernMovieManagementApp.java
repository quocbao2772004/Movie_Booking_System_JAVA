package com.mycompany.database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import org.bson.Document;

public class ModernMovieManagementApp extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SECONDARY_COLOR = new Color(44, 62, 80);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private MovieDatabase movieDatabase;
    private TransactionHistory transactionHistory;
    private AccountManager accountManager;

    private JTable movieTable, transactionTable, accountTable;
    private JTabbedPane tabbedPane;

    public ModernMovieManagementApp() {
        initializeDatabase();
        setupFrame();
        createUI();
    }

    private void initializeDatabase() {
        movieDatabase = new MovieDatabase();
        transactionHistory = new TransactionHistory();
        accountManager = new AccountManager();
    }

    private void setupFrame() {
        setTitle("Cinema Management System");
        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Cinema Management", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(SECONDARY_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        tabbedPane = createStyledTabbedPane();
        tabbedPane.addTab("Movie Management", createMovieManagementPanel());
        tabbedPane.addTab("Transaction History", createTransactionHistoryPanel());
        tabbedPane.addTab("Account Management", createAccountManagementPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTabbedPane createStyledTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(REGULAR_FONT);
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setForeground(SECONDARY_COLOR);
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 1) {
                refreshTransactionTable();
            } else if (tabbedPane.getSelectedIndex() == 2) {
                refreshAccountTable();
            }
        });
        return tabbedPane;
    }

    private JPanel createMovieManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        movieTable = createStyledTable(new String[]{"Title", "Genre", "Duration", "Release Date", "Status"});
        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add right-click context menu for movieTable
        createMovieContextMenu();

        JPanel buttonPanel = createMovieButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshMovieTable();
        return panel;
    }

    private void createMovieContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edit Movie");
        editItem.addActionListener(e -> showEditMovieDialog());
        popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete Movie");
        deleteItem.addActionListener(e -> showDeleteMovieDialog());
        popupMenu.add(deleteItem);

        movieTable.setComponentPopupMenu(popupMenu);

        movieTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private JPanel createMovieButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = createStyledButton("Add Movie", e -> showAddMovieDialog());
        buttonPanel.add(addButton);

        return buttonPanel;
    }

    private void showAddMovieDialog() {
        JDialog dialog = new JDialog(this, "Add Movie", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createMovieInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areMovieFieldsValid(inputPanel)) {
                try {
                    JTextField titleField = (JTextField) inputPanel.getComponent(1);
                    JTextField genreField = (JTextField) inputPanel.getComponent(3);
                    JTextField durationField = (JTextField) inputPanel.getComponent(5);
                    JTextField releaseDateField = (JTextField) inputPanel.getComponent(7);
                    JTextField statusField = (JTextField) inputPanel.getComponent(9);

                    movieDatabase.addMovie(
                            "1", // Assuming ID is auto-generated or managed elsewhere
                            titleField.getText(),
                            List.of(), // Cinemas
                            List.of(), // Show Dates
                            genreField.getText(),
                            "", // Image Path
                            "", // Director
                            "", // Description
                            Integer.parseInt(durationField.getText()),
                            releaseDateField.getText(),
                            "" // Main Actors
                    );
                    refreshMovieTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error adding movie: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showEditMovieDialog() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie to edit.", "No movie selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Edit Movie", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createMovieInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JTextField titleField = (JTextField) inputPanel.getComponent(1);
        JTextField genreField = (JTextField) inputPanel.getComponent(3);
        JTextField durationField = (JTextField) inputPanel.getComponent(5);
        JTextField releaseDateField = (JTextField) inputPanel.getComponent(7);
        JTextField statusField = (JTextField) inputPanel.getComponent(9);

        titleField.setText((String) movieTable.getValueAt(selectedRow, 0));
        genreField.setText((String) movieTable.getValueAt(selectedRow, 1));
        durationField.setText(String.valueOf(movieTable.getValueAt(selectedRow, 2)));
        releaseDateField.setText((String) movieTable.getValueAt(selectedRow, 3));
        statusField.setText((String) movieTable.getValueAt(selectedRow, 4));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areMovieFieldsValid(inputPanel)) {
                try {
                    movieDatabase.updateMovie(
                            "1", // Assuming ID is auto-generated or managed elsewhere
                            titleField.getText(),
                            List.of(), // Cinemas
                            List.of(), // Show Dates
                            genreField.getText(),
                            "", // Image Path
                            "", // Director
                            "", // Description
                            Integer.parseInt(durationField.getText()),
                            releaseDateField.getText(),
                            "" // Main Actors
                    );
                    refreshMovieTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error updating movie: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showDeleteMovieDialog() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie to delete.", "No movie selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String movieTitle = (String) movieTable.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the movie: " + movieTitle + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                movieDatabase.deleteMovie("1"); // Assuming ID is auto-generated or managed elsewhere
                refreshMovieTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting movie: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createMovieInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"Title:", "Genre:", "Duration:", "Release Date:", "Status:"};
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST;
            JLabel label = new JLabel(labels[i]);
            label.setFont(REGULAR_FONT);
            panel.add(label, gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            fields[i] = new JTextField(20);
            fields[i].setFont(REGULAR_FONT);
            panel.add(fields[i], gbc);
        }

        return panel;
    }

    private JPanel createAccountManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        accountTable = createStyledTable(new String[]{"Username", "Email"});
        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add right-click context menu for accountTable
        createAccountContextMenu();

        JPanel buttonPanel = createAccountButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshAccountTable();
        return panel;
    }

    private void createAccountContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edit Account");
        editItem.addActionListener(e -> showEditAccountDialog());
        popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete Account");
        deleteItem.addActionListener(e -> showDeleteAccountDialog());
        popupMenu.add(deleteItem);

        accountTable.setComponentPopupMenu(popupMenu);

        accountTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private JPanel createAccountButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = createStyledButton("Add Account", e -> showAddAccountDialog());
        buttonPanel.add(addButton);

        return buttonPanel;
    }

    private void showAddAccountDialog() {
        JDialog dialog = new JDialog(this, "Add Account", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createAccountInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areAccountFieldsValid(inputPanel)) {
                try {
                    JTextField usernameField = (JTextField) inputPanel.getComponent(1);
                    JPasswordField passwordField = (JPasswordField) inputPanel.getComponent(3);
                    JTextField emailField = (JTextField) inputPanel.getComponent(5);

                    accountManager.createAccount(
                            usernameField.getText(),
                            new String(passwordField.getPassword()),
                            emailField.getText()
                    );
                    refreshAccountTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error adding account: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showEditAccountDialog() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an account to edit.", "No account selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Edit Account", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createAccountInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JTextField usernameField = (JTextField) inputPanel.getComponent(1);
        JPasswordField passwordField = (JPasswordField) inputPanel.getComponent(3);
        JTextField emailField = (JTextField) inputPanel.getComponent(5);

        usernameField.setText((String) accountTable.getValueAt(selectedRow, 0));
        emailField.setText(accountManager.getEmail(usernameField.getText()));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areAccountFieldsValid(inputPanel)) {
                try {
                    accountManager.deleteAccount(usernameField.getText());
                    accountManager.createAccount(
                            usernameField.getText(),
                            new String(passwordField.getPassword()),
                            emailField.getText()
                    );
                    refreshAccountTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error updating account: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showDeleteAccountDialog() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an account to delete.", "No account selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = (String) accountTable.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the account: " + username + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                accountManager.deleteAccount(username);
                refreshAccountTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting account: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createAccountInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"Username:", "Password:", "Email:"};
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField emailField = new JTextField(20);

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST;
            JLabel label = new JLabel(labels[i]);
            label.setFont(REGULAR_FONT);
            panel.add(label, gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            if (i == 0) {
                panel.add(usernameField, gbc);
            } else if (i == 1) {
                panel.add(passwordField, gbc);
            } else {
                panel.add(emailField, gbc);
            }
        }

        return panel;
    }

    private JPanel createStyledButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton[] buttons = {
                createStyledButton("Add Movie", e -> showAddMovieDialog())
        };

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        return buttonPanel;
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(REGULAR_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(listener);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private JTable createStyledTable(String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        table.setFont(REGULAR_FONT);
        table.setRowHeight(30);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(200, 220, 255));
        table.getTableHeader().setBackground(SECONDARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
        table.getTableHeader().setOpaque(false);
        table.setShowGrid(false);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));

        return table;
    }

    private JPanel createTransactionHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        transactionTable = createStyledTable(new String[]{"Transaction ID", "Movie Title", "Username", "Date", "Amount"});
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void refreshMovieTable() {
        DefaultTableModel model = (DefaultTableModel) movieTable.getModel();
        model.setRowCount(0);
        List<Movie> movies = movieDatabase.getAllMovies();
        for (Movie movie : movies) {
            model.addRow(new Object[]{
                    movie.getTitle(),
                    movie.getGenre(),
                    movie.getDuration(),
                    movie.getReleaseDate(),
                    movie.getStatus()
            });
        }
    }

    private void refreshTransactionTable() {
        DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
        model.setRowCount(0);
        for (Document transaction : transactionHistory.getAllTransactions()) {
            model.addRow(new Object[]{
                    transaction.getString("transactionId"),
                    transaction.getString("movieTitle"),
                    transaction.getString("username"),
                    transaction.getString("date"),
                    transaction.getInteger("amount")
            });
        }
    }

    private void refreshAccountTable() {
        DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
        model.setRowCount(0);
        List<Document> accounts = accountManager.getAllAccounts();
        for (Document account : accounts) {
            model.addRow(new Object[]{
                    account.getString("username"),
                    account.getString("email")
            });
        }
    }

    private boolean areMovieFieldsValid(JPanel panel) {
        JTextField titleField = (JTextField) panel.getComponent(1);
        JTextField genreField = (JTextField) panel.getComponent(3);
        JTextField durationField = (JTextField) panel.getComponent(5);
        JTextField releaseDateField = (JTextField) panel.getComponent(7);
        JTextField statusField = (JTextField) panel.getComponent(9);

        if (titleField.getText().isEmpty() || genreField.getText().isEmpty() ||
                durationField.getText().isEmpty() || releaseDateField.getText().isEmpty() ||
                statusField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(durationField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Duration must be a valid number.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean areAccountFieldsValid(JPanel panel) {
        JTextField usernameField = (JTextField) panel.getComponent(1);
        JPasswordField passwordField = (JPasswordField) panel.getComponent(3);
        JTextField emailField = (JTextField) panel.getComponent(5);

        if (usernameField.getText().isEmpty() || passwordField.getPassword().length == 0 || emailField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModernMovieManagementApp app = new ModernMovieManagementApp();
            app.setVisible(true);
        });
    }
}
