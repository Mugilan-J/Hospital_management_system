package hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class editDoctor extends javax.swing.JFrame {

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField di;
    private javax.swing.JTextField dn;
    private javax.swing.JTextField ds;

    public editDoctor() {
        initComponents();
        customizeComponents();
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void customizeComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main frame settings
        this.setBackground(new Color(245, 245, 245));
        getContentPane().setBackground(new Color(245, 245, 245));

        // Header styling
        jLabel1.setFont(new Font("Segoe UI Light", Font.BOLD, 32));
        jLabel1.setForeground(new Color(51, 51, 51));

        // Labels styling
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Color labelColor = new Color(71, 71, 71);

        jLabel2.setFont(labelFont);
        jLabel3.setFont(labelFont);
        jLabel4.setFont(labelFont);

        jLabel2.setForeground(labelColor);
        jLabel3.setForeground(labelColor);
        jLabel4.setForeground(labelColor);

        // Text fields styling
        styleTextField(di);
        styleTextField(dn);
        styleTextField(ds);

        // Buttons styling
        styleButton(jButton1, new Color(0, 123, 255), Color.WHITE, "Update");
        styleButton(jButton2, new Color(108, 117, 125), Color.WHITE, "Back");
        styleButton(jButton3, new Color(220, 53, 69), Color.WHITE, "Logout");
        styleButton(jButton4, new Color(23, 162, 184), Color.WHITE, "Clear");

        // Add padding to the main panel
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(30, 40, 30, 40));
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        field.setPreferredSize(new Dimension(400, 45));
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor, String text) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setText(text.toUpperCase());
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        di = new javax.swing.JTextField();
        dn = new javax.swing.JTextField();
        ds = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Edit Doctor Record");
        setResizable(true);

        jLabel1.setText("EDIT DOCTOR RECORD");
        jLabel2.setText("Doctor ID");
        jLabel3.setText("Doctor Name");
        jLabel4.setText("Doctor Specialization");

        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));
        jButton3.addActionListener(evt -> jButton3ActionPerformed(evt));
        jButton4.addActionListener(evt -> jButton4ActionPerformed(evt));

        // Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Horizontal group
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(di)
                                        .addComponent(dn)
                                        .addComponent(ds))
                                .addGap(60, 60, 60))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                                .addComponent(jButton4)
                                .addGap(100, 100, 100)
                                .addComponent(jButton1)
                                .addGap(100, 100, 100)
                                .addComponent(jButton3)
                                .addGap(60, 60, 60))
                        .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addContainerGap())
        );

        // Vertical group
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jLabel1)
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(di, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(dn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(ds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton2)
                                        .addComponent(jButton4)
                                        .addComponent(jButton1)
                                        .addComponent(jButton3))
                                .addContainerGap(60, Short.MAX_VALUE))
        );
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String did = di.getText();
        String dname = dn.getText();
        String dspec = ds.getText();

        if (did.trim().isEmpty() || dname.trim().isEmpty() || dspec.trim().isEmpty()) {
            showErrorDialog("Please fill in all fields");
            return;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");

            String sql = "update `doctor_record` SET `DoctorName`=?, `Specialization`=? WHERE id=?";
            PreparedStatement ptstmt = conn.prepareStatement(sql);
            ptstmt.setString(1, dname);
            ptstmt.setString(2, dspec);
            ptstmt.setString(3, did);
            ptstmt.executeUpdate();

            showSuccessDialog("Record updated successfully");
            clearFields();

        } catch(Exception e) {
            showErrorDialog("Error updating record: " + e.getMessage());
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        clearFields();
    }

    private void clearFields() {
        di.setText("");
        dn.setText("");
        ds.setText("");
        di.requestFocus();
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editDoctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new editDoctor().setVisible(true);
        });
    }
}