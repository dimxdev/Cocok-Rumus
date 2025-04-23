package game;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import menu.MenuLabel;

public final class PilihLevel extends JPanel {

    private final JPanel buttonPanel;
    private final ButtonGroup group;
    private final LevelDialog levelD;
    private static final Level DEFAULT_LEVEL = Level.Easy;

    public PilihLevel(LevelDialog levelD) {
        this.levelD = levelD;
        levelD.setLevel(DEFAULT_LEVEL);
        setOpaque(false);
        setLayout(new BorderLayout());

        // Label Judul Pilih Level
        MenuLabel messageLabel = new MenuLabel(
                "Pilih Levelmu : ",
                new Font(Font.SANS_SERIF, Font.BOLD, 50),
                Color.WHITE
        );
        messageLabel.setBorder(new EmptyBorder(20, 20, 40, 20));
        add(messageLabel, BorderLayout.NORTH); // Ubah dari CENTER ke NORTH agar tidak menutupi tombol

        // Panel untuk tombol pilihan level
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout()); // Pastikan tombol tersusun secara horizontal
        add(buttonPanel, BorderLayout.CENTER);

        group = new ButtonGroup();
        addRadioButton("Easy", Level.Easy);
        addRadioButton("Hard", Level.Hard);
    }

    public void addRadioButton(String name, Level level) {
        boolean selected = level == DEFAULT_LEVEL;

        JRadioButton button = new JRadioButton(name, selected);
        button.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Perbaikan path gambar
        ImageIcon dotIcon = loadIcon("/images/Dot.png");
        ImageIcon targetDotIcon = loadIcon("/images/TargetDot.png");

        if (dotIcon != null && targetDotIcon != null) {
            button.setIcon(dotIcon);
            button.setSelectedIcon(targetDotIcon);
        } else {
            System.err.println("Peringatan: Ikon " + name + " tidak ditemukan!");
        }

        button.setBorder(new EmptyBorder(0, 30, 0, 30));

        group.add(button);
        button.setOpaque(false);
        buttonPanel.add(button);

        ActionListener listener = _ -> levelD.setLevel(level);
        button.addActionListener(listener);
    }

    private ImageIcon loadIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL == null) {
            System.err.println("Gagal memuat ikon: " + path);
            return null;
        }
        return new ImageIcon(imgURL);
    }
}
