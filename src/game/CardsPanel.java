package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class CardsPanel extends JPanel {

    private final List<Object> cards;

    // Konstruktor untuk CardsPanel, menerima level permainan (Easy/Hard)
    public CardsPanel(Level level) {
        int rows = jumlahBaris(level);
        int cols = jumlahKolom(level);

        Card.setBackImage(rows, cols);
        Card.loadCardImages();

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 10, 20, 50));

        cards = new ArrayList<>();
        inisialisasiCards(rows * cols);
        tambahCardkePapan(rows, cols);
    }

    // Jumlah baris berdasarkan level permainan
    private int jumlahBaris(Level level) {
        return switch (level) {
            case Easy ->
                4;
            case Hard ->
                6;
            default ->
                throw new IllegalArgumentException("Level tidak cocok");
        };
    }

    // Jumlah kolom berdasarkan level permainan
    private int jumlahKolom(Level level) {
        return switch (level) {
            case Easy ->
                4;
            case Hard ->
                6;
            default ->
                throw new IllegalArgumentException("Level tidak cocok");
        };
    }

    // Inisialisasi kartu-kartu yang akan ditampilkan berdasarkan total kartu
    private void inisialisasiCards(int totalCards) {
        int pairs = totalCards / 2;
        List<String> pasanganTersedia = new ArrayList<>(GameEngine.getPasanganKartu().keySet());
        Collections.shuffle(pasanganTersedia);

        // Menambahkan pasangan kartu ke dalam daftar kartu
        for (int i = 0; i < pairs; i++) {
            String namaCard1 = pasanganTersedia.get(i);
            String namaCard2 = GameEngine.getPasanganKartu().get(namaCard1);

            // Menambahkan dua kartu pasangan ke dalam daftar
            cards.add(new Card(namaCard1, Card.getCardImage(namaCard1)));
            cards.add(new Card(namaCard2, Card.getCardImage(namaCard2)));
        }

        Collections.shuffle(cards);
    }

    // Menambahkan kartu-kartu ke papan permainan (panel) sesuai dengan grid
    private void tambahCardkePapan(int rows, int cols) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Menambahkan kartu ke panel sesuai posisi grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j;
                if (index < cards.size() && cards.get(index) instanceof Card card) {
                    // Menetapkan ukuran kartu berdasarkan gambar belakangnya
                    card.setPreferredSize(new Dimension(Card.getBackImage().getIconWidth(),
                            Card.getBackImage().getIconHeight()));

                    gbc.gridx = j; // Set kolom
                    gbc.gridy = i; // Set baris
                    add(card, gbc); // Menambahkan kartu ke panel
                }
            }
        }
    }

    // Mengganti kartu yang telah cocok dengan kartu placeholder
    public void gantiCard(Card card) {
        SwingUtilities.invokeLater(() -> {
            int index = cards.indexOf(card);
            if (index != -1) {
                JLabel placeholder = new JLabel();
                placeholder.setPreferredSize(card.getPreferredSize());
                placeholder.setOpaque(false);
                placeholder.setEnabled(false);

                // Mengambil posisi kartu di grid dan menggantinya dengan placeholder
                GridBagConstraints gbc = ((GridBagLayout) getLayout()).getConstraints(card);
                remove(card);
                cards.set(index, placeholder);
                add(placeholder, gbc);

                revalidate();
                repaint();
            }
        });
    }

    // Membuka semua kartu dalam waktu tertentu (untuk animasi atau efek)
    public void bukaCard(int durationMillis, Runnable callback) {
        ubahStatusCard(true);

        // Timer untuk menutup kartu kembali setelah durasi yang ditentukan
        Timer timer = new Timer(durationMillis, _ -> {
            ubahStatusCard(false);
            if (callback != null) {
                callback.run();

            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Mengubah status semua kartu (terbuka atau tertutup)
    private void ubahStatusCard(boolean status) {
        for (Object obj : cards) {
            if (obj instanceof Card card) {
                card.setOpened(status);
            }
        }
        revalidate();
        repaint();
    }

    // Mendapatkan daftar kartu aktif (kartu yang tidak tertutup)
    public List<Card> getActiveCards() {
        List<Card> activeCards = new ArrayList<>();
        for (Object obj : cards) {
            if (obj instanceof Card card) {
                activeCards.add(card);
            }
        }
        return activeCards;
    }
}
