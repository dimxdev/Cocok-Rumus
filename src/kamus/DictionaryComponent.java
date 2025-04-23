package kamus;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DictionaryComponent extends JPanel {

    private Image backgroundImage;

    public DictionaryComponent(ActionListener goHomeListener) {
        setLayout(new BorderLayout());
        loadBackgroundImage("images/bg11.jpg");

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(50, 10, 10, 10));

        JLabel titleLabel = new JLabel("KAMUS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.black);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(30, 100, 30, 100));

        String[] formulas = {
            "1. Luas Permukaan Kubus = 6 × (s × s)",
            "2. Keliling Lingkaran = 2 × π × r",
            "3. Luas Permukaan Tabung = 2 × π × r × (r + t)",
            "4. Keliling Segitiga Sama Sisi = 3 × s",
            "5. Luas Persegi = s × s",
            "6. Keliling Persegi = 4 × s",
            "7. Luas Segitiga = ½ × a × t",
            "8. Keliling Segitiga = s × 3",
            "9. Volume Kubus = s × s × s",
            "10. Volume Balok = p × l × t",
            "11. Persamaan Kuadrat = ax² + bx + c = 0",
            "12. Aritmetika (Sn) = n/2 × (a1 + an)",
            "13. Geometri (Sn) = a1 × (1 - r^n) / (1 - r)",
            "14. Hukum Pythagoras = a² + b² = c²",
            "15. Rasio Perbandingan = a : b = c : d",
            "16. Volume Bola = 4/3 × π × r³",
            "17. Luas Permukaan Bola = 4 × π × r²",
            "18. Deret Fibonacci = Fn = Fn-1 + Fn-2",
            "19. Kombinasi = C(n, k) = n! / (k! × (n-k)!)",
            "20. Permutasi = P(n, k) = n! / (n-k)!"
        };

        for (String formula : formulas) {
            JLabel formulaLabel = new JLabel(formula, SwingConstants.LEFT);
            formulaLabel.setFont(new Font("Arial", Font.PLAIN, 24));
            formulaLabel.setForeground(Color.black);
            formulaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            formulaLabel.setBorder(BorderFactory.createEmptyBorder(0, 120, 0, 0));

            contentPanel.add(formulaLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setWheelScrollingEnabled(true);

        add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setOpaque(false);

        JButton homeButton = new JButton(loadImageIcon("images/home.png"));
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.addActionListener(goHomeListener);

        footerPanel.add(homeButton);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void loadBackgroundImage(String path) {
        URL imageUrl = getClass().getClassLoader().getResource(path);
        if (imageUrl != null) {
            backgroundImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.err.println("Gambar tidak ditemukan: " + path);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private ImageIcon loadImageIcon(String path) {
        URL imageUrl = getClass().getClassLoader().getResource(path);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        } else {
            System.err.println("Gambar tidak ditemukan: " + path);
            return new ImageIcon();
        }
    }
}
