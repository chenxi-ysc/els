package top.chenxi.tetris;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame(){
        this.setSize(1000,784);
        this.setTitle("俄罗斯方块");
        this.add(new StartPanel());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
