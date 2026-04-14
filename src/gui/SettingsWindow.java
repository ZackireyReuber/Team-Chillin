package gui;

import java.awt.*;
import javax.swing.*;
public class SettingsWindow extends JDialog {

    private boolean applied = false;

    private Color lightColor;

    private Color darkColor;

    private int squareSize;

    private JPanel previewPanel;

    /**
     * @param parent 
     * @param lightColor 
     * @param darkColor 
     * @param squareSize 
     */
    public SettingsWindow(JFrame parent, Color lightColor, Color darkColor, int squareSize) {
        super(parent, "Board Settings", true);
        this.lightColor = lightColor;
        this.darkColor = darkColor;
        this.squareSize = squareSize;

        setLayout(new BorderLayout(10, 10));
        setSize(400, 380);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel controls = new JPanel(new GridLayout(0, 1, 6, 6));
        controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton lightBtn = new JButton("Light Square Color");
        lightBtn.setBackground(lightColor);
        lightBtn.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(this, "Choose Light Color", this.lightColor);
            if (chosen != null) { this.lightColor = chosen; lightBtn.setBackground(chosen); refreshPreview(); }
        });
        controls.add(new JLabel("Light Square Color:"));
        controls.add(lightBtn);
        JButton darkBtn = new JButton("Dark Square Color");
        darkBtn.setBackground(darkColor);
        darkBtn.setForeground(Color.WHITE);
        darkBtn.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(this, "Choose Dark Color", this.darkColor);
            if (chosen != null) { this.darkColor = chosen; darkBtn.setBackground(chosen); refreshPreview(); }
        });
        controls.add(new JLabel("Dark Square Color:"));
        controls.add(darkBtn);
        controls.add(new JLabel("Board Square Size:"));
        String[] sizes = {"Small (60px)", "Medium (80px)", "Large (100px)"};
        JComboBox<String> sizeBox = new JComboBox<>(sizes);
        sizeBox.setSelectedIndex(squareSize == 60 ? 0 : squareSize == 100 ? 2 : 1);
        sizeBox.addActionListener(e -> {
            int[] vals = {60, 80, 100};
            this.squareSize = vals[sizeBox.getSelectedIndex()];
        });
        controls.add(sizeBox);
        controls.add(new JLabel("Quick Presets:"));
        JPanel presets = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton classic = new JButton("Classic");
        classic.addActionListener(e -> {
            this.lightColor = new Color(240, 217, 181);
            this.darkColor = new Color(181, 136, 99);
            lightBtn.setBackground(this.lightColor);
            darkBtn.setBackground(this.darkColor);
            refreshPreview();
        });
        JButton modern = new JButton("Modern");
        modern.addActionListener(e -> {
            this.lightColor = new Color(220, 220, 220);
            this.darkColor = new Color(80, 80, 80);
            lightBtn.setBackground(this.lightColor);
            darkBtn.setBackground(this.darkColor);
            darkBtn.setForeground(Color.WHITE);
            refreshPreview();
        });
        JButton green = new JButton("Green");
        green.addActionListener(e -> {
            this.lightColor = new Color(238, 238, 210);
            this.darkColor = new Color(118, 150, 86);
            lightBtn.setBackground(this.lightColor);
            darkBtn.setBackground(this.darkColor);
            refreshPreview();
        });
        presets.add(classic); presets.add(modern); presets.add(green);
        controls.add(presets);

        add(controls, BorderLayout.WEST);
        previewPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int s = 20;
                for (int r = 0; r < 4; r++)
                    for (int c = 0; c < 4; c++) {
                        g.setColor((r + c) % 2 == 0 ? SettingsWindow.this.lightColor : SettingsWindow.this.darkColor);
                        g.fillRect(c * s, r * s, s, s);
                    }
            }
        };
        previewPanel.setPreferredSize(new Dimension(80, 80));
        previewPanel.setBorder(BorderFactory.createTitledBorder("Preview"));
        add(previewPanel, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton apply = new JButton("Apply");
        apply.setBackground(new Color(60, 140, 60));
        apply.setForeground(Color.WHITE);
        apply.addActionListener(e -> { applied = true; dispose(); });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> dispose());
        btnPanel.add(cancel);
        btnPanel.add(apply);
        add(btnPanel, BorderLayout.SOUTH);
    }
    private void refreshPreview() { previewPanel.repaint(); }
    public boolean isApplied() { return applied; }
    public Color getLightColor() { return lightColor; }
    public Color getDarkColor() { return darkColor; }

    /** @return the selected square size in pixels */
    public int getSquareSize() { return squareSize; }
}
