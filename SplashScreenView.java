package metropos.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class SplashScreenView extends JFrame {
    private JProgressBar progressBar;

    public SplashScreenView() {
        setUndecorated(true);
        setSize(500, 300);
        setLocationRelativeTo(null);
        init(); 
    }

    public void init() {
        setLayout(new BorderLayout());

       
        ImageIcon logoIcon = new ImageIcon("Metro-POS-System-main\\metropos\\images\\SplashScreenLogo.png");
        int logoWidth = logoIcon.getIconWidth();
        int logoHeight = logoIcon.getIconHeight();
        int scaledWidth = 200;
        int scaledHeight = (int) ((double) logoHeight / logoWidth * scaledWidth);
        Image logoImage = logoIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BorderLayout());
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        logoPanel.setPreferredSize(new Dimension(500, 200));

        
        JPanel progressPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(240, 240, 240));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        progressPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setBackground(Color.LIGHT_GRAY);
        progressBar.setForeground(new Color(108, 167, 255));
        progressBar.setPreferredSize(new Dimension(0, 10));
        progressBar.setUI(new RoundedProgressBarUI());

        progressPanel.add(progressBar, BorderLayout.CENTER);

       
        add(logoPanel, BorderLayout.CENTER);
        add(progressPanel, BorderLayout.SOUTH);

        setVisible(true);
        loadApplication();  
    }

    private void loadApplication() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int progress = 0; progress <= 100; progress++) {
                    Thread.sleep(50);
                    publish(progress);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                for (Integer progress : chunks) {
                    progressBar.setValue(progress);
                }
            }

            @Override
            protected void done() {
                dispose(); 
                new LoginView(); 
            }
        };
        worker.execute();
    }

    private static class RoundedProgressBarUI extends javax.swing.plaf.basic.BasicProgressBarUI {
        @Override
        protected void paintDeterminate(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = progressBar.getWidth();
            int height = progressBar.getHeight();
            int barWidth = (int) (width * progressBar.getPercentComplete());

            g2.setColor(progressBar.getBackground());
            g2.fillRoundRect(0, 0, width, height, height, height);

            g2.setColor(progressBar.getForeground());
            g2.fillRoundRect(0, 0, barWidth, height, 0, 0);
        }
    }
}
