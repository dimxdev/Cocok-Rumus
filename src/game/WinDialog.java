package game;

import java.awt.*;
import javax.swing.*;
import menu.MenuComponent;

public class WinDialog extends Dialog {

    public WinDialog(JFrame parentFrame, MenuComponent menuComponent, int score, String formattedTime) {
        super("Permainan Selesai");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        setPreferredSize(new Dimension(500, 200));
        setLocationRelativeTo(parentFrame);

        JPanel mainPanel = getPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = createLabel("Permainan Berhasil!", SwingConstants.CENTER, Color.WHITE, Font.BOLD, 28);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel scoreTimePanel = new JPanel();
        scoreTimePanel.setLayout(new BoxLayout(scoreTimePanel, BoxLayout.Y_AXIS));
        scoreTimePanel.setOpaque(false);
        scoreTimePanel.add(createInfoPanel("Skor: ", String.valueOf(score)));
        scoreTimePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        scoreTimePanel.add(createInfoPanel("Waktu: ", formattedTime));
        mainPanel.add(scoreTimePanel, BorderLayout.CENTER);

        JButton saveButton = createButton("Simpan Skor", _ -> {
            PapanPermainan.getInstance().addHighScore(score);
            dispose();
            parentFrame.getContentPane().removeAll();
            parentFrame.add(menuComponent);
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(saveButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(parentFrame);
    }

    private JLabel createLabel(String text, int alignment, Color color, int fontStyle, int fontSize) {
        JLabel label = new JLabel(text, alignment);
        label.setFont(new Font("Arial", fontStyle, fontSize));
        label.setForeground(color);
        label.setOpaque(false);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JPanel createInfoPanel(String labelText, String valueText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        JLabel label = createLabel(labelText, SwingConstants.CENTER, Color.WHITE, Font.BOLD, 20);
        JLabel valueLabel = createLabel(valueText, SwingConstants.CENTER, Color.WHITE, Font.BOLD, 20);

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(valueLabel);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action);
        return button;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 250);
    }
}
