package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import menu.MenuButton;
import menu.MenuComponent;
import menu.MenuLabel;
import menu.UkuranFrame;

public final class Exit extends Dialog implements ActionListener {

    private final JLabel messageLabel;
    private JButton continueButton;
    private JButton restartButton;
    private JButton goMenuButton;
    private static final Dimension BUTTON_SIZE = new Dimension(300, 60);

    public Exit(String title) {
        super(title);

        setUndecorated(true);
        String message = "Mau Ke mana ?";
        messageLabel = new MenuLabel(message, new Font(Font.SANS_SERIF, Font.BOLD, 50), Color.WHITE);

        super.getPanel().add(messageLabel, BorderLayout.CENTER);
        addButtonPanel();

        pack();
        setLocationRelativeTo(null);
    }

    public void addButtonPanel() {
        continueButton = new MenuButton("Lanjutkan Kembali", new Color(138, 200, 114), 28, BUTTON_SIZE);
        restartButton = new MenuButton("Ulang Lagi", new Color(255, 193, 7), 28, BUTTON_SIZE);
        goMenuButton = new MenuButton("Kembali Ke Menu", new Color(132, 197, 200), 28, BUTTON_SIZE);

        continueButton.addActionListener(_ -> {
            setVisible(false);

            if (PapanPermainan.isPause()) {
                GameEngine.setPlaying(true);
                PapanPermainan.getTimer().setTimeRunning(true);
            }

            PapanPermainan papan = PapanPermainan.getInstance();
            if (papan != null && papan.getGameTimer() != null) {
                papan.getGameTimer().setTimeRunning(true);
            }
        });

        restartButton.addActionListener(_ -> {
            setVisible(false);
            UkuranFrame memory = UkuranFrame.getInstanceOf();
            memory.getContentPane().removeAll();
            memory.add(new PapanPermainan(PapanPermainan.getLevel()));

            memory.revalidate();
            memory.repaint();
        });

        goMenuButton.addActionListener(_ -> {
            setVisible(false);
            UkuranFrame memory = UkuranFrame.getInstanceOf();
            memory.getContentPane().removeAll();
            memory.add(new MenuComponent());

            memory.revalidate();
            memory.repaint();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(continueButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(goMenuButton);
        buttonPanel.setOpaque(false);
        super.getPanel().add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        PapanPermainan.getTimer().setTimeRunning(false);
        GameEngine.setPlaying(false);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
