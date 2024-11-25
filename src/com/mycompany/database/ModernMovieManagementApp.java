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
    private CinemaManager cinemaManager;
    private FeedbackDatabase feedbackDatabase;
    private SeatsDatabase seatsDatabase;

    private JTable movieTable, transactionTable, accountTable, cinemaTable, feedbackTable, seatsTable;
    private JTabbedPane tabbedPane;

    public ModernMovieManagementApp() {
        initializeDatabase();
        setupFrame();
        createUI();
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

    private void initializeDatabase() {
        movieDatabase = new MovieDatabase();
        transactionHistory = new TransactionHistory();
        accountManager = new AccountManager();
        cinemaManager = new CinemaManager();
        feedbackDatabase = new FeedbackDatabase();
        seatsDatabase = new SeatsDatabase();
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
        tabbedPane.addTab("Cinema Management", createCinemaManagementPanel());
        tabbedPane.addTab("Feedback Management", createFeedbackManagementPanel());
        tabbedPane.addTab("Seats Management", createSeatsManagementPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTabbedPane createStyledTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(REGULAR_FONT);
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setForeground(SECONDARY_COLOR);
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            switch (selectedIndex) {
                case 1:
                    refreshTransactionTable();
                    break;
                case 2:
                    refreshAccountTable();
                    break;
                case 3:
                    refreshCinemaTable();
                    break;
                case 4:
                    refreshFeedbackTable();
                    break;
                case 5:
                    refreshSeatsTable();
                    break;
            }
        });
        return tabbedPane;
    }

    private JPanel createMovieManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        movieTable = createStyledTable(new String[]{"ID", "Title", "Genre", "Duration", "Release Date", "Status"});
        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

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
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createMovieInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areMovieFieldsValid(inputPanel)) {
                try {
                    JTextField idField = (JTextField) inputPanel.getComponent(1);
                    JTextField titleField = (JTextField) inputPanel.getComponent(3);
                    JTextField genreField = (JTextField) inputPanel.getComponent(5);
                    JTextField durationField = (JTextField) inputPanel.getComponent(7);
                    JTextField releaseDateField = (JTextField) inputPanel.getComponent(9);
                    JTextField statusField = (JTextField) inputPanel.getComponent(11);

                    movieDatabase.addMovie(
                            idField.getText(),
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
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createMovieInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JTextField idField = (JTextField) inputPanel.getComponent(1);
        JTextField titleField = (JTextField) inputPanel.getComponent(3);
        JTextField genreField = (JTextField) inputPanel.getComponent(5);
        JTextField durationField = (JTextField) inputPanel.getComponent(7);
        JTextField releaseDateField = (JTextField) inputPanel.getComponent(9);
        JTextField statusField = (JTextField) inputPanel.getComponent(11);

        idField.setText((String) movieTable.getValueAt(selectedRow, 0));
        titleField.setText((String) movieTable.getValueAt(selectedRow, 1));
        genreField.setText((String) movieTable.getValueAt(selectedRow, 2));
        durationField.setText(String.valueOf(movieTable.getValueAt(selectedRow, 3)));
        releaseDateField.setText((String) movieTable.getValueAt(selectedRow, 4));
        statusField.setText((String) movieTable.getValueAt(selectedRow, 5));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areMovieFieldsValid(inputPanel)) {
                try {
                    movieDatabase.updateMovie(
                            idField.getText(),
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

        String movieId = (String) movieTable.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the movie with ID: " + movieId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                movieDatabase.deleteMovie(movieId);
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

        String[] labels = {"ID:", "Title:", "Genre:", "Duration:", "Release Date:", "Status:"};
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

        accountTable = createStyledTable(new String[]{"Username", "Password", "Email", "Role"});
        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

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
        dialog.setSize(400, 250);
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
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createAccountInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JTextField usernameField = (JTextField) inputPanel.getComponent(1);
        JPasswordField passwordField = (JPasswordField) inputPanel.getComponent(3);
        JTextField emailField = (JTextField) inputPanel.getComponent(5);

        usernameField.setText((String) accountTable.getValueAt(selectedRow, 0));
        passwordField.setText((String) accountTable.getValueAt(selectedRow, 1));
        emailField.setText((String) accountTable.getValueAt(selectedRow, 2));

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

        String[] labels = {"Username:", "Password:", "Email:", "Role:"};
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField emailField = new JTextField(20);
        JTextField roleField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel usernameLabel = new JLabel(labels[0]);
        usernameLabel.setFont(REGULAR_FONT);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel(labels[1]);
        passwordLabel.setFont(REGULAR_FONT);
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel(labels[2]);
        emailLabel.setFont(REGULAR_FONT);
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel roleLabel = new JLabel(labels[3]);
        roleLabel.setFont(REGULAR_FONT);
        panel.add(roleLabel, gbc);

        gbc.gridx = 1;
        panel.add(roleField, gbc);

        return panel;
    }

    private JPanel createTransactionHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        transactionTable = createStyledTable(new String[]{"Transaction ID", "Username", "Amount", "Timestamp", "Seats", "Movie Title", "Movie Date", "Is Paid"});
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCinemaManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        cinemaTable = createStyledTable(new String[]{"Cinema Name", "Show Hours"});
        JScrollPane scrollPane = new JScrollPane(cinemaTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        createCinemaContextMenu();

        JPanel buttonPanel = createCinemaButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshCinemaTable();
        return panel;
    }

    private void createCinemaContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edit Cinema");
        editItem.addActionListener(e -> showEditCinemaDialog());
        popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete Cinema");
        deleteItem.addActionListener(e -> showDeleteCinemaDialog());
        popupMenu.add(deleteItem);

        cinemaTable.setComponentPopupMenu(popupMenu);

        cinemaTable.addMouseListener(new MouseAdapter() {
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

    private JPanel createCinemaButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = createStyledButton("Add Cinema", e -> showAddCinemaDialog());
        buttonPanel.add(addButton);

        return buttonPanel;
    }

    private void showAddCinemaDialog() {
        JDialog dialog = new JDialog(this, "Add Cinema", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createCinemaInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areCinemaFieldsValid(inputPanel)) {
                try {
                    JTextField nameField = (JTextField) inputPanel.getComponent(1);
                    JTextField showHoursField = (JTextField) inputPanel.getComponent(3);

                    cinemaManager.addCinema(
                            nameField.getText(),
                            List.of(showHoursField.getText().split(","))
                    );
                    refreshCinemaTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error adding cinema: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showEditCinemaDialog() {
        int selectedRow = cinemaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a cinema to edit.", "No cinema selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Edit Cinema", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createCinemaInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JTextField nameField = (JTextField) inputPanel.getComponent(1);
        JTextField showHoursField = (JTextField) inputPanel.getComponent(3);

        nameField.setText((String) cinemaTable.getValueAt(selectedRow, 0));
        showHoursField.setText(String.join(", ", (List<String>) cinemaTable.getValueAt(selectedRow, 1)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areCinemaFieldsValid(inputPanel)) {
                try {
                    cinemaManager.deleteCinema(nameField.getText());
                    cinemaManager.addCinema(
                            nameField.getText(),
                            List.of(showHoursField.getText().split(","))
                    );
                    refreshCinemaTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error updating cinema: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showDeleteCinemaDialog() {
        int selectedRow = cinemaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a cinema to delete.", "No cinema selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cinemaName = (String) cinemaTable.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the cinema: " + cinemaName + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                cinemaManager.deleteCinema(cinemaName);
                refreshCinemaTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting cinema: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createCinemaInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"Cinema Name:", "Show Hours:"};
        JTextField nameField = new JTextField(20);
        JTextField showHoursField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel(labels[0]);
        nameLabel.setFont(REGULAR_FONT);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel showHoursLabel = new JLabel(labels[1]);
        showHoursLabel.setFont(REGULAR_FONT);
        panel.add(showHoursLabel, gbc);

        gbc.gridx = 1;
        panel.add(showHoursField, gbc);

        return panel;
    }

    private JPanel createFeedbackManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        feedbackTable = createStyledTable(new String[]{"Movie", "User", "Feedback", "Status"});
        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        createFeedbackContextMenu();

        JPanel buttonPanel = createFeedbackButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshFeedbackTable();
        return panel;
    }

    private void createFeedbackContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edit Feedback");
        editItem.addActionListener(e -> showEditFeedbackDialog());
        popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete Feedback");
        deleteItem.addActionListener(e -> showDeleteFeedbackDialog());
        popupMenu.add(deleteItem);

        feedbackTable.setComponentPopupMenu(popupMenu);

        feedbackTable.addMouseListener(new MouseAdapter() {
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

    private JPanel createFeedbackButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = createStyledButton("Add Feedback", e -> showAddFeedbackDialog());
        buttonPanel.add(addButton);

        return buttonPanel;
    }

    private void showAddFeedbackDialog() {
        JDialog dialog = new JDialog(this, "Add Feedback", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createFeedbackInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areFeedbackFieldsValid(inputPanel)) {
                try {
                    JTextField movieField = (JTextField) inputPanel.getComponent(1);
                    JTextField userField = (JTextField) inputPanel.getComponent(3);
                    JTextArea feedbackField = (JTextArea) inputPanel.getComponent(5);
                    JTextField statusField = (JTextField) inputPanel.getComponent(7);

                    feedbackDatabase.addFeedback(
                            movieField.getText(),
                            userField.getText(),
                            feedbackField.getText(),
                            statusField.getText()
                    );
                    refreshFeedbackTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error adding feedback: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showEditFeedbackDialog() {
        int selectedRow = feedbackTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a feedback to edit.", "No feedback selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Edit Feedback", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createFeedbackInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JTextField movieField = (JTextField) inputPanel.getComponent(1);
        JTextField userField = (JTextField) inputPanel.getComponent(3);
        JTextArea feedbackField = (JTextArea) inputPanel.getComponent(5);
        JTextField statusField = (JTextField) inputPanel.getComponent(7);

        movieField.setText((String) feedbackTable.getValueAt(selectedRow, 0));
        userField.setText((String) feedbackTable.getValueAt(selectedRow, 1));
        feedbackField.setText((String) feedbackTable.getValueAt(selectedRow, 2));
        statusField.setText((String) feedbackTable.getValueAt(selectedRow, 3));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areFeedbackFieldsValid(inputPanel)) {
                try {
                    feedbackDatabase.addFeedback(
                            movieField.getText(),
                            userField.getText(),
                            feedbackField.getText(),
                            statusField.getText()
                    );
                    refreshFeedbackTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error updating feedback: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showDeleteFeedbackDialog() {
        int selectedRow = feedbackTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a feedback to delete.", "No feedback selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String movie = (String) feedbackTable.getValueAt(selectedRow, 0);
        String user = (String) feedbackTable.getValueAt(selectedRow, 1);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the feedback for movie: " + movie + " by user: " + user + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                // Assuming feedbackDatabase.deleteFeedback(movie, user);
                refreshFeedbackTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting feedback: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createFeedbackInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"Movie:", "User:", "Feedback:", "Status:"};
        JTextField movieField = new JTextField(20);
        JTextField userField = new JTextField(20);
        JTextArea feedbackField = new JTextArea(5, 20);
        feedbackField.setLineWrap(true);
        feedbackField.setWrapStyleWord(true);
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackField);
        JTextField statusField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel movieLabel = new JLabel(labels[0]);
        movieLabel.setFont(REGULAR_FONT);
        panel.add(movieLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(movieField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel userLabel = new JLabel(labels[1]);
        userLabel.setFont(REGULAR_FONT);
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel feedbackLabel = new JLabel(labels[2]);
        feedbackLabel.setFont(REGULAR_FONT);
        panel.add(feedbackLabel, gbc);

        gbc.gridx = 1;
        panel.add(feedbackScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel statusLabel = new JLabel(labels[3]);
        statusLabel.setFont(REGULAR_FONT);
        panel.add(statusLabel, gbc);

        gbc.gridx = 1;
        panel.add(statusField, gbc);

        return panel;
    }

    private JPanel createSeatsManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        seatsTable = createStyledTable(new String[]{"Cinema Name", "Seat", "Status"});
        JScrollPane scrollPane = new JScrollPane(seatsTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        createSeatsContextMenu();

        JPanel buttonPanel = createSeatsButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshSeatsTable();
        return panel;
    }

    private void createSeatsContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem updateItem = new JMenuItem("Update Seat Status");
        updateItem.addActionListener(e -> showUpdateSeatStatusDialog());
        popupMenu.add(updateItem);

        seatsTable.setComponentPopupMenu(popupMenu);

        seatsTable.addMouseListener(new MouseAdapter() {
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

    private JPanel createSeatsButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = createStyledButton("Add Seats", e -> showAddSeatsDialog());
        buttonPanel.add(addButton);

        return buttonPanel;
    }

    private void showAddSeatsDialog() {
        JDialog dialog = new JDialog(this, "Add Seats", true);
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createSeatsInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areSeatsFieldsValid(inputPanel)) {
                try {
                    JTextField nameField = (JTextField) inputPanel.getComponent(1);

                    seatsDatabase.addSeats(
                            nameField.getText()
                    );
                    refreshSeatsTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error adding seats: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showUpdateSeatStatusDialog() {
        int selectedRow = seatsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a seat to update.", "No seat selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Update Seat Status", true);
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = createUpdateSeatStatusInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JTextField nameField = (JTextField) inputPanel.getComponent(1);
        JTextField seatField = (JTextField) inputPanel.getComponent(3);

        nameField.setText((String) seatsTable.getValueAt(selectedRow, 0));
        seatField.setText((String) seatsTable.getValueAt(selectedRow, 1));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areUpdateSeatStatusFieldsValid(inputPanel)) {
                try {
                    seatsDatabase.updateSeatStatus(
                            nameField.getText(),
                            seatField.getText()
                    );
                    refreshSeatsTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error updating seat status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JPanel createSeatsInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"Cinema Name:"};
        JTextField nameField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel(labels[0]);
        nameLabel.setFont(REGULAR_FONT);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameField, gbc);

        return panel;
    }

    private JPanel createUpdateSeatStatusInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"Cinema Name:", "Seat:"};
        JTextField nameField = new JTextField(20);
        JTextField seatField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel(labels[0]);
        nameLabel.setFont(REGULAR_FONT);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel seatLabel = new JLabel(labels[1]);
        seatLabel.setFont(REGULAR_FONT);
        panel.add(seatLabel, gbc);

        gbc.gridx = 1;
        panel.add(seatField, gbc);

        return panel;
    }

    private void refreshMovieTable() {
        DefaultTableModel model = (DefaultTableModel) movieTable.getModel();
        model.setRowCount(0);
        List<Movie> movies = movieDatabase.getAllMovies();
        for (Movie movie : movies) {
            model.addRow(new Object[]{
                    movie.getId(),
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
        int id = 1;
        for (Transaction transaction : transactionHistory.getAllTransactions()) {
            model.addRow(new Object[]{
                    id++,
                    transaction.getUsername(),
                    transaction.getAmount(),
                    transaction.getTimestamp(),
                    transaction.getSeats(),
                    transaction.getMovieTitle(),
                    transaction.getMovieDate(),
                    transaction.getIsPaid()
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
                    account.getString("password"),
                    account.getString("email"),
                    account.getString("role")
            });
        }
    }

    private void refreshCinemaTable() {
        DefaultTableModel model = (DefaultTableModel) cinemaTable.getModel();
        model.setRowCount(0);
        List<Cinema> cinemas = cinemaManager.getAllCinemas();
        for (Cinema cinema : cinemas) {
            model.addRow(new Object[]{
                    cinema.getName(),
                    String.join(", ", cinema.getShowHours())
            });
        }
    }

    private void refreshFeedbackTable() {
        DefaultTableModel model = (DefaultTableModel) feedbackTable.getModel();
        model.setRowCount(0);
        // Assuming feedbackDatabase.getAllFeedbacks() returns all feedbacks
        List<Feedback> feedbacks = feedbackDatabase.getFeedbacks("");
        for (Feedback feedback : feedbacks) {
            model.addRow(new Object[]{
                    feedback.getMovie(),
                    feedback.getUser(),
                    feedback.getFeedback(),
                    feedback.getStatus()
            });
        }
    }

    private void refreshSeatsTable() {
        DefaultTableModel model = (DefaultTableModel) seatsTable.getModel();
        model.setRowCount(0);
        // Assuming seatsDatabase.getAllSeats() returns all seats
        List<Seats> seats = seatsDatabase.getSeats("");
        for (Seats seat : seats) {
            model.addRow(new Object[]{
                    seat.getCinemaName(),
                    seat.getSeat(),
                    seat.getStatus()
            });
        }
    }

    private boolean areMovieFieldsValid(JPanel panel) {
        JTextField idField = (JTextField) panel.getComponent(1);
        JTextField titleField = (JTextField) panel.getComponent(3);
        JTextField genreField = (JTextField) panel.getComponent(5);
        JTextField durationField = (JTextField) panel.getComponent(7);
        JTextField releaseDateField = (JTextField) panel.getComponent(9);
        JTextField statusField = (JTextField) panel.getComponent(11);

        if (idField.getText().isEmpty() || titleField.getText().isEmpty() ||
                genreField.getText().isEmpty() || durationField.getText().isEmpty() ||
                releaseDateField.getText().isEmpty() || statusField.getText().isEmpty()) {
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
        JTextField roleField = (JTextField) panel.getComponent(7);

        if (usernameField.getText().isEmpty() || passwordField.getPassword().length == 0 ||
                emailField.getText().isEmpty() || roleField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean areCinemaFieldsValid(JPanel panel) {
        JTextField nameField = (JTextField) panel.getComponent(1);
        JTextField showHoursField = (JTextField) panel.getComponent(3);

        if (nameField.getText().isEmpty() || showHoursField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean areFeedbackFieldsValid(JPanel panel) {
        JTextField movieField = (JTextField) panel.getComponent(1);
        JTextField userField = (JTextField) panel.getComponent(3);
        JTextArea feedbackField = (JTextArea) panel.getComponent(5);
        JTextField statusField = (JTextField) panel.getComponent(7);

        if (movieField.getText().isEmpty() || userField.getText().isEmpty() ||
                feedbackField.getText().isEmpty() || statusField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean areSeatsFieldsValid(JPanel panel) {
        JTextField nameField = (JTextField) panel.getComponent(1);

        if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean areUpdateSeatStatusFieldsValid(JPanel panel) {
        JTextField nameField = (JTextField) panel.getComponent(1);
        JTextField seatField = (JTextField) panel.getComponent(3);

        if (nameField.getText().isEmpty() || seatField.getText().isEmpty()) {
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
