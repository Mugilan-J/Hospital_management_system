package hospital;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;

public class SignupPage extends javax.swing.JFrame {

    public SignupPage() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Panel to contain everything
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Gradient Background Panel
        LoginGradientPanel backgroundPanel = new LoginGradientPanel();
        backgroundPanel.setLayout(new GridBagLayout());

        // Hospital Name Label
        JLabel jLabel1 = new JLabel("Kullan HOSPITAL");
        jLabel1.setFont(new Font("Trebuchet MS", Font.BOLD, 26));
        jLabel1.setForeground(new Color(33, 150, 243));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(jLabel1, gbc);

        // Sign Up Label
        JLabel signupLabel = new JLabel("Create New Account");
        signupLabel.setFont(new Font("Arial", Font.BOLD, 20));
        signupLabel.setForeground(new Color(50, 50, 50));
        gbc.gridy = 1;
        mainPanel.add(signupLabel, gbc);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(userLabel, gbc);

        JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(250, 35));
        userField.setFont(new Font("Arial", Font.PLAIN, 16));
        userField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        userField.setBackground(new Color(245, 245, 245));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(userField, gbc);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(250, 35));
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        passField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        passField.setBackground(new Color(245, 245, 245));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passField, gbc);

        // Confirm Password
        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmPassLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(confirmPassLabel, gbc);

        JPasswordField confirmPassField = new JPasswordField();
        confirmPassField.setPreferredSize(new Dimension(250, 35));
        confirmPassField.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmPassField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        confirmPassField.setBackground(new Color(245, 245, 245));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(confirmPassField, gbc);

        // Sign Up Button
        JButton signupButton = new JButton("SIGN UP");
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBackground(new Color(33, 150, 243));
        signupButton.setOpaque(true);
        signupButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupButton.addActionListener(evt -> handleSignup(userField, passField, confirmPassField));
        signupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signupButton.setBackground(new Color(30, 136, 229));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signupButton.setBackground(new Color(33, 150, 243));
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(signupButton, gbc);

        // Clear Button
        JButton clearButton = new JButton("CLEAR");
        clearButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(255, 87, 34));
        clearButton.setOpaque(true);
        clearButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(evt -> clearFields(userField, passField, confirmPassField));
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(239, 83, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(255, 87, 34));
            }
        });

        gbc.gridy = 6;
        mainPanel.add(clearButton, gbc);

        // Already have an account? Login link
        JLabel loginLink = new JLabel("Already have an account? Login");
        loginLink.setFont(new Font("Arial", Font.PLAIN, 16));
        loginLink.setForeground(new Color(33, 150, 243));
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });

        gbc.gridy = 7;
        mainPanel.add(loginLink, gbc);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void handleSignup(JTextField userField, JPasswordField passField, JPasswordField confirmPassField) {
        String username = userField.getText().trim();
        String password = String.valueOf(passField.getPassword());
        String confirmPassword = String.valueOf(confirmPassField.getPassword());

        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hms", "root", "");

            // Check if username already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT username FROM user_login WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose another.");
                return;
            }

            // Insert new user
            String sql = "INSERT INTO user_login (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
                new LoginPage().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error during registration. Please try again.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private void clearFields(JTextField userField, JPasswordField passField, JPasswordField confirmPassField) {
        userField.setText("");
        passField.setText("");
        confirmPassField.setText("");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new SignupPage().setVisible(true);
        });
    }
}