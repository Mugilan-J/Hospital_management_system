package hospital;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class welcome extends javax.swing.JFrame {

    public welcome() {
        initComponents();
        setLocationRelativeTo(null); // Center the window
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setTitle("Welcome - North City Hospital");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        setResizable(false);

        // Gradient panel
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new GridBagLayout());
        add(gradientPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to MJ Hospital");
        titleLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        gradientPanel.add(titleLabel, gbc);

        // Doctor's Record Button
        JButton doctorsButton = createButton("Doctor's Record");
        doctorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                doctorsButtonActionPerformed(evt);
            }
        });
        gbc.gridy = 1;
        gradientPanel.add(doctorsButton, gbc);

        // Patient's Record Button
        JButton patientsButton = createButton("Patient's Record");
        patientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                patientsButtonActionPerformed(evt);
            }
        });
        gbc.gridy = 2;
        gradientPanel.add(patientsButton, gbc);

        // Logout Button
        JButton logoutButton = createButton("LOGOUT");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        gbc.gridy = 3;
        gradientPanel.add(logoutButton, gbc);

        pack();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(33, 150, 243));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void doctorsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        DOCTORS doctorsFrame = new DOCTORS();
        doctorsFrame.setVisible(true);
        dispose();
    }

    private void patientsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        PATIENT patientsFrame = new PATIENT();
        patientsFrame.setVisible(true);
        dispose();
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        LoginPage loginFrame = new LoginPage();
        loginFrame.setVisible(true);
        dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new welcome().setVisible(true));
    }
}


class GradientPanel extends JPanel {
    private BufferedImage backgroundImage;

    public GradientPanel() {
        try {
            // Load the image from the specified path
            backgroundImage = ImageIO.read(new File("img/bg_hms.jpeg")); // Change this path to your image path
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the background image
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        // Create a vertical gradient from transparent to a color
        GradientPaint gp = new GradientPaint(0, 0, new Color(0, 0, 0, 0), 0, getHeight(), new Color(0, 0, 0, 150)); // Adjust the alpha for transparency
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
