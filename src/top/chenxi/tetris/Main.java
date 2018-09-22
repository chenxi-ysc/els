package top.chenxi.tetris;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame("els");
        frame.setSize(519,825);
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        JMenu game = new JMenu("游戏");
        Font font = new Font("宋体",Font.BOLD,20);
        game.setFont(font);
        JMenuItem newgame = new JMenuItem("新游戏");
        newgame.setFont(font);
        JMenuItem pause = new JMenuItem("暂停");
        pause.setFont(font);
        JMenuItem cont = new JMenuItem("继续");
        cont.setFont(font);
        JMenuItem exit = new JMenuItem("退出");
        exit.setFont(font);
        game.add(newgame);
        game.add(pause);
        game.add(cont);
        game.add(exit);
        menu.add(game);
        Tetris tetris = new Tetris();
        frame.add(tetris);
        Thread thread = new Thread(tetris);
        thread.start();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
