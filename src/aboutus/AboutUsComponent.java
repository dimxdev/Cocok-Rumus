package aboutus;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AboutUsComponent extends JPanel {

    private Image backgroundImage;

    public AboutUsComponent(ActionListener goHomeListener) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        loadBackgroundImage("images/bg12.jpg");

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(50, 10, 10, 10));

        JLabel titleLabel = new JLabel("ABOUT US", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBorder(new EmptyBorder(100, 50, 20, 50));
        titleLabel.setForeground(Color.black);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(20, 120, 20, 120));

        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center; font-size: 16px;'>"
                + "Cocok Rumus adalah permainan memory yang menguji ingatan serta "
                + "memperkaya pengetahuan matematika dan sains. Pemain harus "
                + "mencocokkan pasangan kartu yang menyembunyikan rumus dan konsep penting. "
                + "Dengan dua level kesulitan, yaitu Mudah dan Menengah, pemain ditantang "
                + "untuk menemukan pasangan rumus secepat mungkin dengan waktu dan percobaan yang terbatas. Selain mengasah daya ingat, permainan ini juga "
                + "membantu memperdalam pemahaman tentang rumus yang sering digunakan dalam kehidupan sehari-hari. "
                + "Game ini dirancang untuk memberi tantangan intelektual serta memberikan pengalaman belajar yang menyenangkan. "
                + "Ayo buktikan sejauh mana ingatan dan pengetahuan matematikamu dapat bertahan! "
                + "Bergabunglah dengan komunitas pemain lainnya dan tantang teman-temanmu untuk mencapai skor tertinggi! "
                + "Jadikan game ini sebagai sarana untuk terus belajar sambil bersenang-senang. "
                + "Setiap level yang kamu selesaikan akan membuka lebih banyak tantangan baru, membuat permainan ini tak pernah membosankan!"
                + "</div></html>");

        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionLabel.setForeground(Color.black);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(descriptionLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel profilesPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        profilesPanel.setOpaque(false);
        profilesPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        profilesPanel.add(createProfilePanel("images/jarwo.png", "Jarwo Eddy W", "2350081064"));
        profilesPanel.add(createProfilePanel("images/dimas.png", "Dimas Brotowali H", "2350081081"));
        profilesPanel.add(createProfilePanel("images/alvin.png", "M Alvin Pratama", "2350081076"));

        contentPanel.add(profilesPanel);
        add(contentPanel, BorderLayout.CENTER);

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
            System.out.println("Gambar tidak ditemukan: " + path);
            return new ImageIcon();
        }
    }

    private JPanel createProfilePanel(String imagePath, String name, String nim) {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setOpaque(false);

        ImageIcon profileIcon = loadImageIcon(imagePath);
        JLabel profilePicture;
        if (profileIcon.getIconWidth() > 0) {
            profilePicture = new JLabel(profileIcon);
        } else {
            profilePicture = new JLabel("Gambar Tidak Ada", SwingConstants.CENTER);
            profilePicture.setFont(new Font("Arial", Font.ITALIC, 12));
            profilePicture.setForeground(Color.LIGHT_GRAY);
        }
        profilePicture.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.add(profilePicture);

        JLabel profileName = new JLabel(name, SwingConstants.CENTER);
        profileName.setFont(new Font("Arial", Font.BOLD, 19));
        profileName.setForeground(Color.BLACK);
        profileName.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.add(profileName);

        JLabel profileNIM = new JLabel(nim, SwingConstants.CENTER);
        profileNIM.setFont(new Font("Arial", Font.PLAIN, 16));
        profileNIM.setForeground(Color.DARK_GRAY);
        profileNIM.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.add(profileNIM);

        return profilePanel;
    }
}
