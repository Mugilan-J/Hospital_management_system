package hospital;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.ActionListener;

public class PATIENT extends javax.swing.JFrame {
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;

    public PATIENT() {
        // Set size before initialization
        setPreferredSize(new Dimension(700, 600));

        // Initialize all components
        initComponents();
        customizeComponents();

        // Pack the frame first
        pack();

        // Disable resizing for consistent appearance
        setResizable(false);

        // Center on screen after packing
        setLocationRelativeTo(null);

        // Ensure the window is actually visible
        setVisible(true);
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Patient Module");

        // Add action listeners
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));
        jButton3.addActionListener(evt -> jButton3ActionPerformed(evt));
        jButton4.addActionListener(evt -> jButton4ActionPerformed(evt));
        jButton5.addActionListener(evt -> jButton5ActionPerformed(evt));
        jButton6.addActionListener(evt -> jButton6ActionPerformed(evt));
    }

    private void customizeComponents() {
        // Create main layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Set background gradient
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(238, 242, 255), w, h, new Color(224, 231, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Style the title
        jLabel1.setForeground(Color.BLACK);
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 32));
        jLabel1.setText("PATIENTS RECORD");
        jLabel1.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 1, 0, 20));
        buttonsPanel.setOpaque(false);

        // Create and style main buttons
        setupMainButton(jButton1, "ADMIT NEW PATIENT", buttonsPanel);
        setupMainButton(jButton2, "EDIT PATIENT DETAILS", buttonsPanel);
        setupMainButton(jButton3, "DISCHARGE PATIENT", buttonsPanel);
        setupMainButton(jButton4, "VIEW PATIENT DETAILS", buttonsPanel);

        // Navigation panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        navPanel.setOpaque(false);

        // Style navigation buttons
        setupNavButton(jButton5, "BACK", navPanel);
        setupNavButton(jButton6, "LOGOUT", navPanel);

        // Add components to main panel
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(jLabel1);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(buttonsPanel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(navPanel);

        // Set frame properties
        setContentPane(mainPanel);
    }

    private void setupMainButton(JButton originalButton, String text, JPanel parent) {
        RoundedButton button = createStyledButton(text, true);
        copyActionListeners(originalButton, button);
        parent.add(wrapInPanel(button));
    }

    private void setupNavButton(JButton originalButton, String text, JPanel parent) {
        RoundedButton button = createStyledButton(text, false);
        copyActionListeners(originalButton, button);
        parent.add(button);
    }

    private RoundedButton createStyledButton(String text, boolean isMainButton) {
        RoundedButton button = new RoundedButton(text);
        if (isMainButton) {
            button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            // Different colors for each main button
            switch (text) {
                case "ADMIT NEW PATIENT":
                    button.setBackground(new Color(75, 192, 192)); // Teal
                    break;
                case "EDIT PATIENT DETAILS":
                    button.setBackground(new Color(153, 102, 255)); // Purple
                    break;
                case "DISCHARGE PATIENT":
                    button.setBackground(new Color(255, 159, 64)); // Orange
                    break;
                default:
                    button.setBackground(new Color(54, 162, 235)); // Blue
            }
            button.setPreferredSize(new Dimension(300, 50));
        } else {
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            button.setBackground(text.equals("BACK") ?
                    new Color(75, 192, 192) :  // Teal for BACK
                    new Color(255, 99, 132));  // Pink for LOGOUT
            button.setPreferredSize(new Dimension(120, 40));
        }
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        setupHoverEffect(button);
        return button;
    }

    private void copyActionListeners(JButton source, JButton target) {
        for (ActionListener al : source.getActionListeners()) {
            target.addActionListener(al);
        }
    }

    private JPanel wrapInPanel(JComponent component) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(component);
        return wrapper;
    }

    private void setupHoverEffect(RoundedButton button) {
        Color normalColor = button.getBackground();
        Color hoverColor = new Color(
                Math.min(255, (int)(normalColor.getRed() * 1.1)),
                Math.min(255, (int)(normalColor.getGreen() * 1.1)),
                Math.min(255, (int)(normalColor.getBlue() * 1.1))
        );

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
    }

    private class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Set button color based on state
            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g2.setColor(getBackground().brighter());
            } else {
                g2.setColor(getBackground());
            }

            int width = getWidth();
            int height = getHeight();
            int radius = height;

            // Create fully rounded rectangle shape
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                    0, 0, width - 1, height - 1, radius, radius
            );

            // Fill the main shape
            g2.fill(roundedRectangle);

            // Add gradient overlay for 3D effect
            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 50),
                    0, height, new Color(0, 0, 0, 50)
            );
            g2.setPaint(gp);
            g2.fill(roundedRectangle);

            // Draw text with shadow
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(getText(), g2);
            int x = (width - (int) r.getWidth()) / 2;
            int y = (height - (int) r.getHeight()) / 2 + fm.getAscent();

            // Draw text shadow
            g2.setColor(new Color(0, 0, 0, 50));
            g2.drawString(getText(), x + 1, y + 1);

            // Draw main text
            g2.setColor(getForeground());
            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        admitPatient obj = new admitPatient();
        obj.setVisible(true);
        dispose();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        editPatient obj = new editPatient();
        obj.setVisible(true);
        dispose();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        dischargePatient obj = new dischargePatient();
        obj.setVisible(true);
        dispose();
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        viewrecordsPatient obj = new viewrecordsPatient();
        obj.setVisible(true);
        dispose();
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        welcome obj = new welcome();
        obj.setVisible(true);
        dispose();
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        LoginPage obj = new LoginPage();
        obj.setVisible(true);
        dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PATIENT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(() -> {
            new PATIENT();
        });
    }
}