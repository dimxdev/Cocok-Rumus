package game;

// Deklarasi enum Level untuk menentukan tingkat kesulitan permainan
public enum Level {
    Easy(16),
    Hard(36);

    private final int jumlahCard;

    // Konstruktor untuk inisialisasi jumlah kartu berdasarkan level
    Level(int jumlahCard) {
        this.jumlahCard = jumlahCard;
    }

    // Metode untuk mendapatkan jumlah kartu pada level tertentu
    public int getJumlahCard() {
        return jumlahCard;
    }
}
