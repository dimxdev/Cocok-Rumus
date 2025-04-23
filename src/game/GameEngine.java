package game;

import java.util.*;
import javax.swing.*;
import menu.MenuComponent;

public class GameEngine {

    private final CardsPanel cardsPanel;
    private final Timer gameTimer;
    private static final Map<String, String> pasanganKartu = new HashMap<>();
    private int berhasilCocok;
    private boolean gameRunning;
    private static boolean playing;
    private static int percobaan;
    private static boolean timerStarted;

    static {
        PencocokanCard(); // Inisialisasi pasangan kartu saat kelas di-load
    }

    // Konstruktor untuk inisialisasi GameEngine
    public GameEngine(CardsPanel cardsPanel, Timer gameTimer, PapanPermainan papanPermainan) {
        this.cardsPanel = cardsPanel;
        this.gameTimer = gameTimer;
        resetGame(); // Reset kondisi permainan saat objek dibuat
    }

    // Membuat pasangan kartu secara acak
    private static void PencocokanCard() {
        pasanganKartu.clear();
        List<Integer> indeks = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            indeks.add(i);
        }
        Collections.shuffle(indeks);
        indeks.forEach(i -> pasanganKartu.put("Nama(" + i + ")", "Rumus(" + i + ")"));
    }

    // Memulai loop utama permainan
    public void loopGame() {
        gameRunning = true;
        new Thread(() -> {
            while (gameRunning) {
                synchronized (cardsPanel) {
                    if (!cardsPanel.getActiveCards().isEmpty()) {
                        PemilihanCard(cardsPanel.getActiveCards()); 
                    } else {
                        endGame(); 
                        return;
                    }
                }
            }
        }).start();
    }

    // Memproses pemilihan kartu yang aktif
    private void PemilihanCard(List<Card> activeCards) {
        int[] indeksDipilih = getClickedCards(activeCards);
        if (indeksDipilih.length == 2) {
            disableCards(activeCards, indeksDipilih); 
            prosesPencocokan(activeCards, indeksDipilih); 
            enableCards(activeCards); 
        }
    }

    // Mendapatkan indeks kartu yang diklik
    private int[] getClickedCards(List<Card> cards) {
        int[] indeksDipilih = {-1, -1};
        int clicked = 0;
        for (int i = 0; i < cards.size() && clicked < 2; i++) {
            if (cards.get(i).isClicked() && cards.get(i).isAllowClick()) {
                indeksDipilih[clicked++] = i;
            }
        }
        return clicked == 2 ? indeksDipilih : new int[0];
    }

    // Memproses pencocokan kartu berdasarkan indeks
    private void prosesPencocokan(List<Card> cards, int[] indeks) {
        if (indeks.length != 2) {
            return;
        }

        int first = indeks[0], second = indeks[1];

        synchronized (cardsPanel) {
            cards.get(first).setOpened(true); 
            cards.get(second).setOpened(true);
            pause(500); 
            percobaan++; 

            SwingUtilities.invokeLater(() -> {
                PapanPermainan.getInstance().setPercobaan(String.valueOf(percobaan));
            });

            if (isMatch(cards, first, second)) {
                cardCocok(cards, first, second);
            } else {
                resetCards(cards, first, second); 
            }

            cards.get(first).setClicked(false);
            cards.get(second).setClicked(false);
        }
    }

    // Menangani logika jika dua kartu cocok
    private void cardCocok(List<Card> cards, int first, int second) {
        SwingUtilities.invokeLater(() -> {
            cardsPanel.gantiCard(cards.get(first)); 
            cardsPanel.gantiCard(cards.get(second)); 
        });
        berhasilCocok++; 
        if (berhasilCocok == pasanganKartu.size()) { 
            stopTimer(); 
            gameRunning = false; 
        }
    }

    // Memeriksa apakah dua kartu cocok
    private boolean isMatch(List<Card> cards, int first, int second) {
        if (first >= cards.size() || second >= cards.size()) {
            return false;
        }

        String card1Name = cards.get(first).getNamaCard();
        String card2Name = cards.get(second).getNamaCard();

        return pasanganKartu.getOrDefault(card1Name, "").equals(card2Name)
                || pasanganKartu.getOrDefault(card2Name, "").equals(card1Name);
    }

    // Mereset kartu jika tidak cocok
    private void resetCards(List<Card> cards, int first, int second) {
        cards.get(first).setOpened(false); 
        cards.get(second).setOpened(false); 
    }

    // Menonaktifkan klik pada kartu selain yang dipilih
    private void disableCards(List<Card> cards, int[] indeksDipilih) {
        for (int i = 0; i < cards.size(); i++) {
            if (i != indeksDipilih[0] && i != indeksDipilih[1]) {
                cards.get(i).setAllowClick(false);
            }
        }
    }

    // Mengaktifkan kembali klik pada kartu
    private void enableCards(List<Card> cards) {
        cards.forEach(card -> {
            if (!card.isClicked()) {
                card.setAllowClick(true);
            }
        });
    }

    // Mengakhiri permainan
    private void endGame() {
        gameRunning = false;
        stopTimer(); // Hentikan timer
        SwingUtilities.invokeLater(this::showWinDialog);
    }

    // Menghentikan timer permainan
    private void stopTimer() {
        gameTimer.stopTimer();
        SwingUtilities.invokeLater(() -> {
            PapanPermainan.getInstance()
                    .setTimeLabelText(gameTimer.formatTime(gameTimer.getTime())); 
        });
    }

    // Menampilkan dialog kemenangan
    private void showWinDialog() {
        String formattedTime = gameTimer.formatTime(gameTimer.getTime());
        int score = PerhitunganScore(gameTimer.getTime(), percobaan, PapanPermainan.getLevel());
 
        SwingUtilities.invokeLater(() -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(cardsPanel);
            WinDialog winDialog = new WinDialog(parentFrame, new MenuComponent(), score, formattedTime);
            winDialog.setVisible(true);
        });
    }

    // Menghitung skor akhir berdasarkan waktu, percobaan, dan level
    private int PerhitunganScore(int time, int percobaan, Level level) {
        int baseScore = switch (level) {
            case Easy ->
                10;
            case Hard ->
                35;
            default ->
                0;
        };
        return level.getJumlahCard() * baseScore - (time * 2) - (percobaan * 4);
    }

    // Set dan get untuk berbagai status permainan
    public static void setTimerStarted(boolean started) {
        timerStarted = started;
    }

    public static boolean isTimerStarted() {
        return timerStarted;
    }

    public static Map<String, String> getPasanganKartu() {
        return pasanganKartu;
    }

    public static void setPlaying(boolean playingStatus) {
        playing = playingStatus;
    }

    public static boolean isPlaying() {
        return playing;
    }

    public static int getPercobaan() {
        return percobaan;
    }

    public Timer getTimer() {
        return gameTimer;
    }

    // Mereset kondisi permainan
    private void resetGame() {
        berhasilCocok = 0;
        gameRunning = true;
        timerStarted = false;
        playing = true;
        percobaan = 0;
        PencocokanCard(); // Reshuffle pasangan kartu
        SwingUtilities.invokeLater(() -> {
            PapanPermainan.getInstance().setPercobaan("0");
            PapanPermainan.getInstance().setTimeLabelText("00:00");
        });
    }

    // Memberikan jeda (pause) sementara
    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
