package top.chenxi.tetris;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    private JButton button = new JButton();
    ImageIcon icon = new ImageIcon("src/play.png");
    public StartPanel(){
        this.setSize(1000,800);
        button.setIcon(icon);
        this.setLayout(null);
        button.addActionListener(e ->{
                remove(button);
                Tetris tetris = new Tetris();
                tetris.setBounds(0,0,1000,800);
                add(tetris);
                repaint();
                tetris.requestFocus();
                //tetris.start();
        });
        button.setSize(icon.getIconWidth(),icon.getIconHeight());
        button.setBounds((this.getWidth()-button.getWidth())/2, (this.getHeight()-button.getHeight())/2+180,button.getWidth(),button.getHeight());
        button.setContentAreaFilled(false);
        //除去边框
        //button.setBorder(null);
        this.add(button);
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon icon = new ImageIcon("src/start.jpg");
        Image img = icon.getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), icon.getImageObserver());
    }
}
