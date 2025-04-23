package game;

import java.awt.*;
import javax.swing.*; 
import menu.MenuButton; 

// Kelas LevelDialog, turunan dari kelas Dialog
public class LevelDialog extends Dialog {

    private Level level;
    private boolean levelDipilih; 

    // Konstruktor LevelDialog
    public LevelDialog() {
        super("Permainan Baru"); 

        // Tambahkan panel untuk memilih level
        getPanel().add(new PilihLevel(this), BorderLayout.CENTER); 

        // Tambahkan tombol untuk memulai permainan baru
        addNewGameButton();

        pack(); 
    }

    // Metode untuk menambahkan tombol permainan baru
    private void addNewGameButton() {
        MenuButton selectGame = new MenuButton("Mainkan Permainan", new Color(0x8D8A8D), 36, new Dimension(350, 100));

        // Tambahkan aksi pada tombol jika ditekan
        selectGame.addActionListener(_ -> {
            levelDipilih = true;
            setVisible(false);
        });

        // Panel untuk menampung tombol
        JPanel buttonPanel = new JPanel(); 
        buttonPanel.add(selectGame); 
        buttonPanel.setOpaque(false); 

        // Tambahkan panel tombol ke dialog di bagian bawah
        getPanel().add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getter untuk mendapatkan level
    public Level getLevel() {
        return level; 
    }

    // Setter untuk mengatur level
    public void setLevel(Level level) {
        this.level = level;
    }

    // Getter untuk mengecek apakah level telah dipilih
    public boolean isLevelDipilih() {
        return levelDipilih;
    }

    // Setter untuk mengatur apakah level telah dipilih
    public void setLevelDipilih(boolean levelDipilih) {
        this.levelDipilih = levelDipilih;
    }
}
