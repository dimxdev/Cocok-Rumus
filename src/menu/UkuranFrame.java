package menu;

import java.awt.*;
import javax.swing.*;

public class UkuranFrame extends JFrame {

    private static UkuranFrame frame;

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 1024;

    private UkuranFrame() {
        setTitle("Cocok Rumus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Perbaikan pemanggilan gambar ikon
        ImageIcon icon = loadIcon("/images/Brain.png");
        if (icon != null) {
            setIconImage(icon.getImage());
        } else {
            System.err.println("Gagal memuat ikon: images/Brain.png tidak ditemukan!");
        }

        add(new MenuComponent());

        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static UkuranFrame getInstanceOf() {
        if (frame == null) {
            frame = new UkuranFrame();
        }
        return frame;
    }

    // Metode untuk memuat ikon dengan aman
    private ImageIcon loadIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            return null;
        }
    }
}
