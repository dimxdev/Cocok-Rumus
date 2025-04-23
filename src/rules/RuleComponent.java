package rules;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RuleComponent extends JPanel {

    private Image backgroundImage;

    public RuleComponent(ActionListener goHomeListener) {
        setLayout(new BorderLayout());
        loadBackgroundImage("/images/bg9.jpg");

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(50, 10, 10, 10));

        JLabel titleLabel = new JLabel("ATURAN BERMAIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(30, 100, 30, 100));

        String[] rules = {
            "Setiap kartu memiliki konsep dari matematika atau sains.",
            "Balik dua kartu sekaligus untuk mengungkapkan isi mereka.",
            "Cocokkan pasangan kartu dengan rumus atau konsep yang sama.",
            "Selesaikan permainan dalam batas waktu dan percobaan.",
            "Poin diberikan berdasarkan kecepatan dan ketepatan.",
            "Nikmati dan tingkatkan pengetahuan Anda dengan cara yang menyenangkan!"
        };

        for (int i = 0; i < rules.length; i++) {
            String ruleHtml = String.format(
                    "<html>"
                    + "<table width='700px' style='margin-left:60px; text-align:center;'>"
                    + "  <tr>"
                    + "    <td width='40px' align='center' style='vertical-align: top; font-size: 24px;'>%d.</td>"
                    + "    <td style='text-align:center; font-size: 24px;'>%s</td>"
                    + "  </tr>"
                    + "</table>"
                    + "</html>",
                    i + 1, rules[i]
            );

            JLabel ruleLabel = new JLabel(ruleHtml);
            ruleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
            ruleLabel.setForeground(Color.BLACK);

            contentPanel.add(ruleLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        add(contentPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setOpaque(false);

        JButton homeButton = new JButton(loadImageIcon("/images/home.png"));
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.addActionListener(goHomeListener);

        footerPanel.add(homeButton);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void loadBackgroundImage(String path) {
        URL imageUrl = getClass().getResource(path);
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
        URL imageUrl = getClass().getResource(path);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        } else {
            System.err.println("Gambar tidak ditemukan: " + path);
            return new ImageIcon();
        }
    }
}
