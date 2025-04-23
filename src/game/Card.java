package game;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import javax.swing.*;

public class Card extends JButton {

    private final String namaCard;
    private final ImageIcon gambarCard;
    private boolean clicked;
    private boolean modeNinja;
    private boolean allowClick;

    private static final HashMap<String, ImageIcon> gambarCardMap = new HashMap<>();
    private static ImageIcon gambarBelakang;
    private static int lebarCard;
    private static int tinggiCard;

    public Card(String namaCard, ImageIcon gambarCard) {
        this.namaCard = namaCard;
        this.gambarCard = gambarCard;
        this.clicked = false;
        this.modeNinja = false;
        this.allowClick = true;

        if (gambarBelakang != null) {
            setIcon(gambarBelakang);
        } else {
            System.err.println("Peringatan: Gambar belakang belum dimuat!");
        }
        
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(true);
        setContentAreaFilled(false);
        setBorderPainted(false);

        addActionListener(_ -> {
            if (allowClick && !modeNinja && !clicked) {
                setOpened(true);
                clicked = true;

                if (!GameEngine.isTimerStarted()) {
                    GameEngine.setTimerStarted(true);
                    PapanPermainan.getTimer().setTimeRunning(true);
                }
            }
        });
    }

    public void setTampilanDepanCard() {
        if (gambarBelakang != null) {
            setIcon(gambarBelakang);
        } else {
            System.err.println("Peringatan: Gambar belakang belum dimuat!");
        }
        clicked = false;
    }

    public void setOpened(boolean terbuka) {
        setIcon(terbuka ? gambarCard : gambarBelakang);
    }

    public void setAllowClick(boolean izinkan) {
        this.allowClick = izinkan;
    }

    public boolean isAllowClick() {
        return allowClick;
    }

    public void toggleModeNinja() {
        modeNinja = !modeNinja;
        setOpened(modeNinja);
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public String getNamaCard() {
        return namaCard;
    }

    public ImageIcon getImgCard() {
        return gambarCard;
    }

    public static void setBackImage(int rows, int cols) {
        String path = "/images/Brain" + (rows * cols) + ".png";
        ImageIcon backIcon = loadImage(path);

        if (backIcon != null) {
            lebarCard = backIcon.getIconWidth();
            tinggiCard = backIcon.getIconHeight();
            gambarBelakang = backIcon;
        } else {
            System.err.println("Gagal memuat gambar belakang: " + path);
        }
    }

    public static void loadCardImages() {
        if (lebarCard == 0 || tinggiCard == 0) {
            System.err.println("Gagal memuat gambar kartu: Ukuran kartu belum ditentukan!");
            return;
        }

        String[] namaCardArray = {
            "Nama(1)", "Nama(2)", "Nama(3)", "Nama(4)", "Nama(5)", "Nama(6)", "Nama(7)", "Nama(8)", "Nama(9)", "Nama(10)",
            "Nama(11)", "Nama(12)", "Nama(13)", "Nama(14)", "Nama(15)", "Nama(16)", "Nama(17)", "Nama(18)", "Nama(19)", "Nama(20)",
            "Rumus(1)", "Rumus(2)", "Rumus(3)", "Rumus(4)", "Rumus(5)", "Rumus(6)", "Rumus(7)", "Rumus(8)", "Rumus(9)", "Rumus(10)",
            "Rumus(11)", "Rumus(12)", "Rumus(13)", "Rumus(14)", "Rumus(15)", "Rumus(16)", "Rumus(17)", "Rumus(18)", "Rumus(19)", "Rumus(20)"
        };

        for (String namaCard : namaCardArray) {
            String path = "/images/" + namaCard + ".jpg";
            ImageIcon ikonGambar = loadImage(path);

            if (ikonGambar != null) {
                Image gambarDiskalakan = ikonGambar.getImage().getScaledInstance(lebarCard, tinggiCard, Image.SCALE_SMOOTH);
                gambarCardMap.put(namaCard, new ImageIcon(gambarDiskalakan));
            } else {
                System.err.println("Gagal memuat gambar kartu: " + path);
            }
        }
    }

    public static ImageIcon getCardImage(String namaCard) {
        return gambarCardMap.get(namaCard);
    }

    public static ImageIcon getBackImage() {
        return gambarBelakang;
    }

    private static ImageIcon loadImage(String path) {
        URL urlGambar = Card.class.getResource(path);

        if (urlGambar == null) {
            System.err.println("Gagal menemukan file gambar: " + path);
            return null;
        }

        return new ImageIcon(urlGambar);
    }
}
