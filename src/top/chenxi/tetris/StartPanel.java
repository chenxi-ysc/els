package top.chenxi.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    private JButton button = new JButton();
    ImageIcon icon = new ImageIcon("src/play.png");
    public StartPanel(){
        this.setSize(1000,800);
        button.setIcon(icon);
        this.setLayout(null);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(button);
                Tetris tetris = new Tetris();
                tetris.setBounds(0,0,1000,800);
                add(tetris);
                repaint();
                tetris.requestFocus();
                Thread thread = new Thread(tetris);
                thread.start();
            }
        });
        button.setSize(icon.getIconWidth(),icon.getIconHeight());
        button.setBounds((this.getWidth()-button.getWidth())/2, (this.getHeight()-button.getHeight())/2+180,button.getWidth(),button.getHeight());
        button.setContentAreaFilled(false);
        //button.setBorder(null);//除去边框
        this.add(button);
    }
    protected void paintComponent(Graphics g){
        ImageIcon icon = new ImageIcon("src/start.jpg");
        Image img = icon.getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), icon.getImageObserver());
    }
}
