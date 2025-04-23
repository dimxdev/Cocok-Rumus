package menu;

import aboutus.AboutUsComponent;
import game.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import kamus.DictionaryComponent;
import rules.RuleComponent;

public class MenuComponent extends JComponent {

    private final JPanel mainPanel;
    private final Image image;
    private static final String BACKGROUND_IMAGE = "/images/bg7.jpg";
    private LevelDialog levelDialog;

    public MenuComponent() {
        setLayout(new BorderLayout());
        image = new ImageIcon(getClass().getResource(BACKGROUND_IMAGE)).getImage();

        mainPanel = new JPanel(new GridLayout(6, 1, 20, 20));
        mainPanel.setBorder(new EmptyBorder(40, 180, 80, 180));

        addTitle();
        addStartButton();
        addRulesButton();
        addDictionaryButton();
        addAboutButton();
        addExitButton();

        add(mainPanel, BorderLayout.CENTER);
        mainPanel.setOpaque(false);
    }

    private void addTitle() {
        MenuLabel gameName = new MenuLabel("Cocok Rumus", new Font("Arial", Font.BOLD, 102), Color.WHITE);
        gameName.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        mainPanel.add(gameName);
    }

    private void addStartButton() {
        MenuButton newGame = new MenuButton("Mulai", new Color(138, 200, 114), 44, null);

        newGame.addActionListener(_ -> {
            if (levelDialog == null) {
                levelDialog = new LevelDialog();
            }

            levelDialog.setLocationRelativeTo(null);
            levelDialog.setLevelDipilih(false);
            levelDialog.setVisible(true);

            if (levelDialog.isLevelDipilih()) {
                remove(mainPanel);

                if (PapanPermainan.getLevel() != null) {
                    game.Timer timer = PapanPermainan.getTimer();
                    if (timer != null) { 
                        timer.stopTimer();
                    }
                }

                PapanPermainan newBoard = new PapanPermainan(levelDialog.getLevel());
                add(newBoard);

                revalidate();
                repaint();
            }
        });

        mainPanel.add(newGame);
    }

    private void addRulesButton() {
        MenuButton rulesButton = new MenuButton("Aturan Bermain", new Color(138, 200, 214), 44, null);
        rulesButton.addActionListener(_ -> {
            remove(mainPanel);
            add(new RuleComponent(_ -> {
                removeAll();
                add(mainPanel);
                revalidate();
                repaint();
            }));
            revalidate();
            repaint();
        });
        mainPanel.add(rulesButton);
    }

    private void addDictionaryButton() {
        MenuButton dictionaryButton = new MenuButton("Kamus", new Color(200, 165, 114), 44, null);
        dictionaryButton.addActionListener(_ -> {
            remove(mainPanel);
            add(new DictionaryComponent(_ -> {
                removeAll();
                add(mainPanel);
                revalidate();
                repaint();
            }));
            revalidate();
            repaint();
        });
        mainPanel.add(dictionaryButton);
    }

    private void addAboutButton() {
        MenuButton aboutButton = new MenuButton("About Us", new Color(200, 114, 114), 44, null);
        aboutButton.addActionListener(_ -> {
            remove(mainPanel);
            add(new AboutUsComponent(_ -> {
                removeAll();
                add(mainPanel);
                revalidate();
                repaint();
            }));
            revalidate();
            repaint();
        });
        mainPanel.add(aboutButton);
    }

    private void addExitButton() {
        MenuButton exitButton = new MenuButton("Keluar dari Game", new Color(255, 69, 58), 44, null);
        exitButton.addActionListener(_ -> { 
            System.exit(0);
        });
        mainPanel.add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
