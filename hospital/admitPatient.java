package hospital;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class admitPatient extends javax.swing.JFrame implements Runnable {

    int hour, seconds, minutes;
    private Color primaryColor = new Color(41, 128, 185);    // Nice blue
    private Color accentColor = new Color(52, 152, 219);     // Lighter blue
    private Color backgroundColor = new Color(236, 240, 241); // Light gray
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
    private Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

    public admitPatient() {
        initComponents();
        showDate();
        Thread t = new Thread(this);
        t.start();
    }

    void showDate() {
        Date d = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
        pad.setText(sd.format(d));
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
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();

        // Create styled text fields
        pi = createStyledTextField();
        pn = createStyledTextField();
        pd = createStyledTextField();
        pad = createStyledTextField();
        pat = createStyledTextField();

        // Create styled buttons
        jButton1 = createStyledButton("ADMIT", primaryColor);
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
        jLabel1.setText("ADMIT PATIENT");
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
        JLabel[] labels = {jLabel2, jLabel3, jLabel4, jLabel5, jLabel6};
        String[] labelTexts = {"Patient ID:", "Patient Name:", "Patient Disease:", "Admit Date:", "Admit Time:"};

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
        JTextField[] fields = {pi, pn, pd, pad, pat};
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
        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");

            String sql = "insert into patient_record values (?,?,?,?,?)";
            PreparedStatement ptstmt = conn.prepareStatement(sql);
            ptstmt.setString(1, pi.getText());
            ptstmt.setString(2, pn.getText());
            ptstmt.setString(3, pd.getText());
            ptstmt.setString(4, pad.getText());
            ptstmt.setString(5, pat.getText());
            ptstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Patient admitted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            conn.close();
            pi.setText(""); pn.setText(""); pd.setText(""); pat.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        PATIENT obj = new PATIENT();
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

        java.awt.EventQueue.invokeLater(() -> new admitPatient().setVisible(true));
    }

    @Override
    public void run() {
        while (true) {
            Calendar cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minutes = cal.get(Calendar.MINUTE);
            seconds = cal.get(Calendar.SECOND);

            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm:ss aa");
            Date dat = cal.getTime();
            String time24 = sdf24.format(dat);
            pat.setText(time24);
        }
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField pad;
    private javax.swing.JTextField pat;
    private javax.swing.JTextField pd;
    private javax.swing.JTextField pi;
    private javax.swing.JTextField pn;
}