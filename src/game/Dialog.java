package game;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public abstract class Dialog extends JDialog {

    private Image image; 
    private static final String DIALOG_BACKGROUND = "/images/dialog2.jpg"; 
    private final JPanel panel; 

    // Konstruktor untuk kelas Dialog, menerima judul dialog
    public Dialog(String title) {
        setTitle(title);
        setResizable(false);
        setModal(true); 

        // Memuat gambar latar belakang dengan path yang benar
        URL imgURL = getClass().getResource(DIALOG_BACKGROUND);
        if (imgURL != null) {
            image = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Gagal memuat gambar: " + DIALOG_BACKGROUND);
        }

        // Panel yang digunakan untuk menampilkan konten dialog
        panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                if (image != null) { 
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panel.setBorder(new EmptyBorder(30, 30, 30, 30)); 
        add(panel, BorderLayout.CENTER); 
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 400);
    }
}
