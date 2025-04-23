package game;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PapanPermainan extends JComponent {

    private static final File HIGH_SCORE_FILE = new File(
            System.getProperty("user.home"), "high_scores.txt"
    );

    private static PapanPermainan instance;
    private static Level level;
    private static Image backgroundImage;
    private static boolean pause;
    private static Timer staticTimer;

    private final CardsPanel cardsPanel;
    private JTable highScoreTable;
    private DefaultTableModel tableModel;
    private Timer gameTimer;
    private boolean isPaused;

    private JLabel percobaanLabel;
    private JLabel timeLabel;
    private JButton playPauseButton;

    public PapanPermainan(Level level) {
        setLayout(new BorderLayout());
        PapanPermainan.level = level;
        instance = this;

        // Mengatur gambar latar belakang
        URL bgURL = getClass().getResource("/images/bggame.jpg");
        if (bgURL != null) {
            backgroundImage = new ImageIcon(bgURL).getImage();
        } else {
            System.err.println("Gagal memuat gambar latar belakang: /images/bggame.jpg");
        }

        cardsPanel = new CardsPanel(level);
        cardsPanel.setOpaque(false);

        isPaused = false;
        pause = false;

        // Timer untuk permainan
        staticTimer = new Timer(this);
        gameTimer = staticTimer;

        // Inisialisasi komponen pada panel pengguna
        initializeUserPanelComponents();

        add(createSidePanelContainer(), BorderLayout.WEST); // Panel samping
        add(createMainPanel(), BorderLayout.CENTER); // Panel utama

        // Membuka kartu untuk beberapa waktu dan memulai timer permainan
        cardsPanel.bukaCard(10000, () -> {
            gameTimer.setTimeRunning(true);
            gameTimer.start();
        });

        // Memulai loop permainan dengan GameEngine
        new GameEngine(cardsPanel, gameTimer, this).loopGame();

        // Memuat skor tertinggi dari file
        loadHighScores();

        // Mengatur mode ninja
        setupNinjaMode();
    }

    private void initializeUserPanelComponents() {
        percobaanLabel = new JLabel("Percobaan: 0");
        percobaanLabel.setFont(new Font("Arial", Font.BOLD, 36));
        percobaanLabel.setForeground(Color.BLACK);
        percobaanLabel.setOpaque(false);
        percobaanLabel.setBorder(new EmptyBorder(0, 10, 10, 0));

        timeLabel = new JLabel("00:00");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setOpaque(false);
        timeLabel.setBorder(new EmptyBorder(0, 10, 10, 0));

        playPauseButton = createButton("PauseButton.png", _ -> togglePause());
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        mainPanel.add(createUpPanel(), BorderLayout.NORTH);
        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createUpPanel() {
        JPanel upPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        upPanel.setOpaque(false);

        percobaanLabel.setBorder(BorderFactory.createEmptyBorder(30, 20, 0, 100));
        upPanel.add(percobaanLabel);

        return upPanel;
    }

    private JPanel createSidePanelContainer() {
        JPanel sidePanelContainer = new JPanel(new BorderLayout());
        sidePanelContainer.setOpaque(false);
        sidePanelContainer.setPreferredSize(new Dimension(330, 0));

        sidePanelContainer.add(createSidePanel(), BorderLayout.CENTER);
        sidePanelContainer.add(createBottomPanel(), BorderLayout.SOUTH);

        return sidePanelContainer;
    }

    private JPanel createSidePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(330, 600));

        JLabel levelLabel = new JLabel(level.toString());
        levelLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        levelLabel.setForeground(Color.BLACK);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(levelLabel);

        timeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(timeLabel);

        JLabel highScoreLabel = new JLabel("High Score");
        highScoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        highScoreLabel.setForeground(Color.BLACK);
        highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(highScoreLabel);

        setupHighScoreTable();
        panel.add(createTablePanel());

        return panel;
    }

    private void setupHighScoreTable() {
        tableModel = new DefaultTableModel(new String[]{""}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        highScoreTable = new JTable(tableModel);
        highScoreTable.setRowHeight(30);
        highScoreTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        highScoreTable.setBackground(Color.WHITE);
        highScoreTable.setShowGrid(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreTable.setDefaultRenderer(Object.class, centerRenderer);
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(45, 60, 80), 2));
        tablePanel.setMaximumSize(new Dimension(300, 310));

        tablePanel.add(highScoreTable.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(highScoreTable, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 35, 0));

        playPauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(playPauseButton);

        bottomPanel.add(Box.createRigidArea(new Dimension(40, 0)));

        JButton exitButton = createButton("Exit.png", _ -> handleExit());
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(exitButton);
        bottomPanel.add(Box.createHorizontalGlue());

        return bottomPanel;
    }

    private JButton createButton(String iconName, ActionListener listener) {
        URL imageUrl = getClass().getResource("/images/" + iconName);
        ImageIcon icon = (imageUrl != null) ? new ImageIcon(imageUrl) : null;

        if (icon == null) {
            System.err.println("Gagal memuat ikon tombol: " + iconName);
        }

        JButton button = new JButton(icon);
        button.setBackground(Color.WHITE);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        return button;
    }

    public void togglePause() {
        isPaused = !isPaused;
        gameTimer.setTimeRunning(!isPaused);

        pause = isPaused;
        GameEngine.setPlaying(!pause);

        String buttonIcon = pause ? "PlayButton.png" : "PauseButton.png";
        URL iconURL = getClass().getResource("/images/" + buttonIcon);
        if (iconURL != null) {
            playPauseButton.setIcon(new ImageIcon(iconURL));
        } else {
            System.err.println("Gagal memuat ikon tombol: " + buttonIcon);
        }

        if (cardsPanel != null) {
            for (Card card : cardsPanel.getActiveCards()) {
                card.setOpened(false);
                card.setAllowClick(!pause && !card.isClicked());
            }
        }
    }

    private void handleExit() {
        gameTimer.setTimeRunning(false);
        GameEngine.setPlaying(false);
        new Exit("Exit").setVisible(true);
    }

    private void loadHighScores() {
        if (HIGH_SCORE_FILE.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        tableModel.addRow(new Object[]{Integer.valueOf(line.trim())});
                    } catch (NumberFormatException ignored) {
                    }
                }
                sortScores(); // Urutkan skor setelah memuat
            } catch (IOException ignored) {
            }
        }
    }

    private void saveScoreToFile(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE, true))) {
            writer.write(String.valueOf(score));
            writer.newLine();
        } catch (IOException ignored) {
        }
    }

    public void addHighScore(int score) {
        tableModel.addRow(new Object[]{score});
        sortScores();
        saveScoreToFile(score);
    }

    private void sortScores() {
        java.util.List<Integer> scores = new java.util.ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                scores.add((Integer) tableModel.getValueAt(i, 0));
            } catch (NumberFormatException ignored) {
            }
        }
        scores.sort(java.util.Collections.reverseOrder()); // Urutkan menurun
        tableModel.setRowCount(0);
        for (int i = 0; i < Math.min(scores.size(), 10); i++) {
            tableModel.addRow(new Object[]{scores.get(i)});
        }
    }

    private void setupNinjaMode() {
        cardsPanel.getActionMap().put("ninja", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cardsPanel.getActiveCards().forEach(Card::toggleModeNinja);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

    public static PapanPermainan getInstance() {
        return instance;
    }

    public Timer getGameTimer() {
        return gameTimer;
    }

    public static Timer getTimer() {
        return staticTimer;
    }

    public static boolean isPause() {
        return pause;
    }

    public static Level getLevel() {
        return level;
    }

    public void setTimeLabelText(String text) {
        if (timeLabel != null) {
            timeLabel.setText(text);
        }
    }

    public void setPercobaan(String text) {
        if (percobaanLabel != null) {
            percobaanLabel.setText("Percobaan: " + text);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 800);
    }
}
