package hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class addDoctor extends javax.swing.JFrame {

    private Color primaryColor = new Color(41, 128, 185);    // Nice blue
    private Color accentColor = new Color(52, 152, 219);     // Lighter blue
    private Color backgroundColor = new Color(236, 240, 241); // Light gray
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
    private Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

    public addDoctor() {
        initComponents();
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(inputFont);
        field.setPreferredSize(new Dimension(200, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primaryColor, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Initialize components
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();

        // Create styled text fields
        di = createStyledTextField();
        dn = createStyledTextField();
        ds = createStyledTextField();

        // Create styled buttons
        jButton1 = createStyledButton("ADD", primaryColor);
        jButton2 = createStyledButton("BACK", accentColor);
        jButton3 = createStyledButton("LOGOUT", new Color(231, 76, 60));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout());

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(backgroundColor);
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        jLabel1.setText("ADD DOCTOR DETAILS");
        jLabel1.setFont(titleFont);
        jLabel1.setForeground(primaryColor);
        headerPanel.add(jLabel1);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Style all labels
        JLabel[] labels = {jLabel3, jLabel2, jLabel4};
        String[] labelTexts = {"Doctor ID:", "Doctor Name:", "Doctor Specialization:"};

        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(labelTexts[i]);
            labels[i].setFont(labelFont);
            labels[i].setForeground(new Color(44, 62, 80));

            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST;
            formPanel.add(labels[i], gbc);
        }

        // Add text fields
        JTextField[] fields = {di, dn, ds};
        for (int i = 0; i < fields.length; i++) {
            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.weightx = 1.0;
            formPanel.add(fields[i], gbc);
        }

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(backgroundColor);

        // Add action listeners
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));
        jButton3.addActionListener(evt -> jButton3ActionPerformed(evt));

        buttonPanel.add(jButton1);
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton3);

        // Add all panels to main panel
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);

        // Add main panel to frame
        add(mainPanel);

        // Window settings
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");

            String sql = "insert into doctor_record values (?,?,?)";
            PreparedStatement ptstmt = conn.prepareStatement(sql);
            ptstmt.setString(1, di.getText());
            ptstmt.setString(2, dn.getText());
            ptstmt.setString(3, ds.getText());
            ptstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Doctor added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            conn.close();

            di.setText(""); dn.setText(""); ds.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        DOCTORS obj = new DOCTORS();
        obj.setVisible(true);
        dispose();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        LoginPage obj = new LoginPage();
        obj.setVisible(true);
        dispose();
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> new addDoctor().setVisible(true));
    }

    private javax.swing.JTextField di;
    private javax.swing.JTextField dn;
    private javax.swing.JTextField ds;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
}