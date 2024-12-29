package hospital;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import javax.swing.border.EmptyBorder;

// Custom GradientPanel class for background
class DoctorGradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Create gradient colors
        Color color1 = new Color(238, 242, 255); // Light indigo
        Color color2 = new Color(224, 231, 255); // Slightly darker indigo
        int w = getWidth();
        int h = getHeight();

        // Create gradient paint
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}

public class DOCTORS extends javax.swing.JFrame {

    public DOCTORS() {
        initComponents();
        // Set frame properties
        setTitle("Hospital Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Create main gradient panel
        DoctorGradientPanel mainPanel = new DoctorGradientPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Header Panel (transparent background to show gradient)
        JPanel headerPanel = new JPanel() {
            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        jLabel1 = new JLabel("Doctors Management");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 32));
        jLabel1.setForeground(new Color(17, 24, 39));  // Dark gray
        headerPanel.add(jLabel1);

        // Buttons Panel (transparent background)
        JPanel buttonPanel = new JPanel() {
            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 15));
        buttonPanel.setBorder(new EmptyBorder(30, 60, 30, 60));

        // Create and style buttons with glass-like effect
        jButton1 = createStyledButton("Add Doctor", new Color(79, 70, 229));
        jButton2 = createStyledButton("Edit Doctor Details", new Color(79, 70, 229));
        jButton3 = createStyledButton("Remove Doctor", new Color(220, 38, 38));
        jButton4 = createStyledButton("View Doctor Details", new Color(79, 70, 229));

        buttonPanel.add(jButton1);
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton3);
        buttonPanel.add(jButton4);

        // Navigation Panel (transparent background)
        JPanel navPanel = new JPanel() {
            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        navPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        navPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        jButton5 = createNavigationButton("← Back");
        jButton6 = createNavigationButton("Logout");

        navPanel.add(jButton5);
        navPanel.add(jButton6);

        // Add all panels to main panel
        mainPanel.add(headerPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(navPanel);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        jButton1.addActionListener(evt -> {
            addDoctor obj = new addDoctor();
            obj.setVisible(true);
            dispose();
        });

        jButton2.addActionListener(evt -> {
            editDoctor obj = new editDoctor();
            obj.setVisible(true);
            dispose();
        });

        jButton3.addActionListener(evt -> {
            fireDoctor obj = new fireDoctor();
            obj.setVisible(true);
            dispose();
        });

        jButton4.addActionListener(evt -> {
            viewdetailDoc obj = new viewdetailDoc();
            obj.setVisible(true);
            dispose();
        });

        jButton5.addActionListener(evt -> {
            welcome obj = new welcome();
            obj.setVisible(true);
            dispose();
        });

        jButton6.addActionListener(evt -> {
            LoginPage obj = new LoginPage();
            obj.setVisible(true);
            dispose();
        });
    }

    private JButton createStyledButton(String text, Color mainColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create slightly transparent version of the button color
                Color baseColor = getBackground();
                Color transparentColor = new Color(baseColor.getRed(), baseColor.getGreen(),
                        baseColor.getBlue(), 230);

                g2.setColor(transparentColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));

                // Draw text
                FontMetrics fm = g2.getFontMetrics();
                Rectangle2D r = fm.getStringBounds(getText(), g2);
                int x = (getWidth() - (int) r.getWidth()) / 2;
                int y = (getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();

                g2.setColor(getForeground());
                g2.setFont(getFont());
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(mainColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(300, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(mainColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(mainColor);
            }
        });

        return button;
    }

    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create semi-transparent white background
                g2.setColor(new Color(255, 255, 255, 180));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));

                // Draw text
                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                Rectangle2D r = fm.getStringBounds(getText(), g2);
                int x = (getWidth() - (int) r.getWidth()) / 2;
                int y = (getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };

        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(new Color(55, 65, 81));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(79, 70, 229));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(55, 65, 81));
            }
        });

        return button;
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
            java.util.logging.Logger.getLogger(DOCTORS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new DOCTORS().setVisible(true);
        });
    }

    private JLabel jLabel1;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
}