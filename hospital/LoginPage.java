package hospital;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;

public class LoginPage extends javax.swing.JFrame {

    public LoginPage() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Panel to contain everything
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255)); // Flat white background
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding around content
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);  // Spacing between elements

        // Gradient Background Panel
        LoginGradientPanel backgroundPanel = new LoginGradientPanel();
        backgroundPanel.setLayout(new GridBagLayout());

        // Hospital Name Label
        JLabel jLabel1 = new JLabel("HOSPITAL");
        jLabel1.setFont(new Font("Trebuchet MS", Font.BOLD, 26));
        jLabel1.setForeground(new Color(33, 150, 243));  // Light blue color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(jLabel1, gbc);

        // Username Label
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userLabel.setForeground(new Color(50, 50, 50));  // Darker grey for readability
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(userLabel, gbc);

        // Username Field
        JTextField user = new JTextField();
        user.setPreferredSize(new Dimension(250, 35));
        user.setFont(new Font("Arial", Font.PLAIN, 16));
        user.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  // Rounded border
        user.setBackground(new Color(245, 245, 245));  // Light grey background for the text field
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(user, gbc);

        // Password Label
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passLabel.setForeground(new Color(50, 50, 50));  // Darker grey for readability
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(passLabel, gbc);

        // Password Field
        JPasswordField pass = new JPasswordField();
        pass.setPreferredSize(new Dimension(250, 35));
        pass.setFont(new Font("Arial", Font.PLAIN, 16));
        pass.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pass.setBackground(new Color(245, 245, 245));  // Light grey background for the password field
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(pass, gbc);

        // Login Button with hover effect
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(33, 150, 243));  // Light blue
        loginButton.setOpaque(true);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(evt -> jButton1ActionPerformed(evt, user, pass));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(30, 136, 229));  // Darker blue on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(33, 150, 243));  // Original blue
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        // Clear Button
        JButton clearButton = new JButton("CLEAR");
        clearButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(255, 87, 34));  // Vibrant red for visibility
        clearButton.setOpaque(true);
        clearButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(evt -> jButton2ActionPerformed(user, pass));
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(239, 83, 80));  // Darker red on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(255, 87, 34));  // Original red
            }
        });

        gbc.gridy = 4;
        mainPanel.add(clearButton, gbc);

        // Stylish Forgot Password Label
        JLabel forgotPasswordLabel = new JLabel("Forgot Password?");
        forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        forgotPasswordLabel.setForeground(new Color(33, 150, 243));  // Light blue
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(33, 150, 243))); // Underline effect
        forgotPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Trigger "Forgot Password" event
                forgotPasswordActionPerformed(evt);
            }
        });

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(forgotPasswordLabel, gbc);

        add(mainPanel);
        pack();  // Adjust to fit components
        setLocationRelativeTo(null);  // Center the frame
        setVisible(true);  // Make frame visible
    }

    private void forgotPasswordActionPerformed(java.awt.event.MouseEvent evt) {
        // Ask for the username
        String username = JOptionPane.showInputDialog(this, "Enter your username:");
        if (username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid username.");
            return;
        }

        // Check if the username exists in the database
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hms", "root", "");
            String sql = "SELECT * FROM user_login WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // If the username exists, prompt the user to enter a new password
                String newPassword = JOptionPane.showInputDialog(this, "Enter a new password:");
                if (newPassword != null && !newPassword.isEmpty()) {
                    // Update the password in the database
                    String updateSql = "UPDATE user_login SET password = ? WHERE username = ?";
                    PreparedStatement updatePstmt = conn.prepareStatement(updateSql);
                    updatePstmt.setString(1, newPassword);
                    updatePstmt.setString(2, username);
                    int rowsAffected = updatePstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Password updated successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error updating the password.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Password cannot be empty.");
                }
            } else {
                // If the username does not exist, show an error message
                JOptionPane.showMessageDialog(this, "Username not found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while establishing connection: " + e.getMessage());
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt, JTextField user, JPasswordField pass) {
        String un = user.getText();
        String p = String.valueOf(pass.getPassword());

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hms", "root", "");
            Statement st = conn.createStatement();
            String sql = "select * from user_login";
            ResultSet rs = st.executeQuery(sql);
            boolean validUser = false;

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");

                if (un.equals(username) && p.equals(password)) {
                    validUser = true;
                    new welcome().setVisible(true);  // Assuming Welcome is another JFrame
                    dispose();  // Close login page
                    break;
                }
            }
            if (!validUser) {
                JOptionPane.showMessageDialog(this, "Wrong username or password.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while establishing connection.");
        }
    }

    private void jButton2ActionPerformed(JTextField user, JPasswordField pass) {
        user.setText("");
        pass.setText("");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
}

class LoginGradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Create a vertical gradient from light blue to white
        GradientPaint gp = new GradientPaint(0, 0, new Color(33, 150, 243), 0, height, new Color(255, 255, 255));
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
    }
}
