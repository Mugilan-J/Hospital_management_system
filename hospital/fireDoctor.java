package hospital;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.*;

public class fireDoctor extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField searchField;
    private JLabel totalDoctorsLabel;
    private Connection connection;

    // Modern color scheme
    private final Color PRIMARY = new Color(79, 70, 229);    // Indigo
    private final Color SURFACE = Color.WHITE;
    private final Color BACKGROUND = new Color(249, 250, 251);
    private final Color ERROR = new Color(239, 68, 68);      // Red
    private final Color SUCCESS = new Color(34, 197, 94);    // Green
    private final Color TEXT = new Color(17, 24, 39);        // Dark gray

    public fireDoctor() {
        setupDatabase();
        initializeUI();
        loadData(false);  // Initial load without success message
    }

    private void setupDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hms",
                    "root",
                    ""
            );
        } catch (Exception e) {
            showError("Database Connection Error", e.getMessage());
            System.exit(1);
        }
    }

    private void initializeUI() {
        setTitle("Hospital Management System - Doctor Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 800));

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // Table
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);

        // Footer
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setBackground(SURFACE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                createRoundedBorder(15),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        // Title section
        JLabel titleLabel = new JLabel("Doctor Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT);

        // Stats section
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
        statsPanel.setBackground(SURFACE);

        totalDoctorsLabel = createStatLabel("Total Doctors", "0");
        statsPanel.add(totalDoctorsLabel);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(statsPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout(0, 15));
        tablePanel.setBackground(BACKGROUND);

        // Search field
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(250, 40));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                createRoundedBorder(10),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        searchField.putClientProperty("JTextField.placeholderText", "Search doctors...");

        // Table setup
        table = new JTable();
        setupTable();

        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(createRoundedBorder(15));
        scrollPane.getViewport().setBackground(SURFACE);

        // Add search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(BACKGROUND);
        searchPanel.add(searchField);

        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void setupTable() {
        String[] columns = {
                "ID", "Doctor Name", "Specialization"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(model);
        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowVerticalLines(false);
        table.setBackground(SURFACE);
        table.setSelectionBackground(PRIMARY.brighter());
        table.setSelectionForeground(SURFACE);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(SURFACE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 50));

        // Column widths
        int[] columnWidths = {100, 300, 300};
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        setupSearch();
    }

    private void setupSearch() {
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { search(); }

            private void search() {
                String text = searchField.getText().toLowerCase();
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
                table.setRowSorter(sorter);

                if (text.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND);

        JButton refreshButton = createStyledButton("Refresh", PRIMARY);
        JButton fireButton = createStyledButton("Fire Doctor", ERROR);
        JButton backButton = createStyledButton("Back", PRIMARY.darker());
        JButton logoutButton = createStyledButton("Logout", ERROR);

        refreshButton.addActionListener(e -> loadData(true));
        fireButton.addActionListener(e -> fireDoctor());
        backButton.addActionListener(e -> handleBack());
        logoutButton.addActionListener(e -> handleLogout());

        buttonPanel.add(refreshButton);
        buttonPanel.add(fireButton);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        return buttonPanel;
    }

    private void loadData(boolean showSuccessMessage) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try {
            String query = "SELECT * FROM doctor_record ORDER BY ID";
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("ID"),
                            rs.getString("DoctorName"),
                            rs.getString("Specialization")
                    });
                }
            }

            updateStatistics();

            if (showSuccessMessage) {
                showSuccess("Doctor records updated successfully");
            }

        } catch (SQLException e) {
            showError("Data Loading Error", e.getMessage());
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void fireDoctor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int doctorId = (int) table.getValueAt(selectedRow, 0);
            String doctorName = (String) table.getValueAt(selectedRow, 1);

            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to remove Dr. " + doctorName + " from the system?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    String query = "DELETE FROM doctor_record WHERE ID = ?";
                    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                        pstmt.setInt(1, doctorId);
                        pstmt.executeUpdate();
                    }
                    loadData(false);
                    showSuccess("Doctor " + doctorName + " has been removed from the system.");
                } catch (SQLException e) {
                    showError("Removal Error", e.getMessage());
                }
            }
        } else {
            showError("Selection Error", "Please select a doctor to remove.");
        }
    }

    private void updateStatistics() {
        try {
            String query = "SELECT COUNT(*) FROM doctor_record";
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalDoctorsLabel.setText(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            showError("Statistics Error", e.getMessage());
        }
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBackground(color);
        button.setForeground(SURFACE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private JLabel createStatLabel(String title, String value) {
        JLabel label = new JLabel(String.format("<html><div style='text-align: center'>%s<br><b>%s</b></div></html>",
                title, value));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(TEXT);
        return label;
    }

    private void handleBack() {
        new DOCTORS().setVisible(true);
        dispose();
    }

    private void handleLogout() {
        new LoginPage().setVisible(true);
        dispose();
    }

    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private Border createRoundedBorder(int radius) {
        return BorderFactory.createCompoundBorder(
                new Border() {
                    @Override
                    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(c.getBackground());
                        g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
                        g2.dispose();
                    }

                    @Override
                    public Insets getBorderInsets(Component c) {
                        return new Insets(radius/2, radius/2, radius/2, radius/2);
                    }

                    @Override
                    public boolean isBorderOpaque() {
                        return true;
                    }
                },
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new fireDoctor().setVisible(true);
        });
    }
}