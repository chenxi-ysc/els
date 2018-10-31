package top.chenxi.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tetris extends JPanel implements Runnable{
    private int mapWidth = 20;
    private int mapHeight = 30;
    private Object lock = new Object();
    private boolean isOver = false;
    private boolean isPause = false;
    private boolean[][] nowBlock;
    private boolean[][] nextBlock;
    private boolean[][] map = new boolean[30][20];
    private int score = 0;
    private Font font = new Font("宋体",Font.PLAIN,20);
    private Thread thread = new Thread(this);
    private JButton pause = new JButton("暂停");
    private Point nowPos = new Point(8,1);


    public Tetris(){
        this.init();
    }

    public void start(){
        thread.start();
    }

    public void suspend(){
        isPause = true;
        pause.setText("继续");
    }

    public void resume(){
        isPause = false;
        this.requestFocus();
        pause.setText("暂停");
        synchronized (lock){
            lock.notify();
        }
    }

    public void init(){
        this.addKeyListener(this.keylistener);
        this.setFocusable(true);
        this.setLayout(null);
        //去掉按钮文字周围的焦点框
        pause.setFocusPainted(false);
        pause.setFont(font);
        pause.setBounds(700,600,100,50);
        pause.addActionListener(e -> {
            if(!isPause) {
                suspend();
            }
            else{
                resume();
            }
        });
        this.add(pause);
        //初始化墙
        for (int i = 0; i < mapHeight; i++) {
            map[i][0] = true;
            map[i][mapWidth-1] = true;
        }
        for (int i = 0; i < mapWidth; i++) {
            map[mapHeight-1][i] = true;
        }
        nowBlock = Shape.shap[newBlock()];
        nextBlock = Shape.shap[newBlock()];
    }
    public void paint(Graphics g){
        super.paint(g);
        // 画map
        int blockSize = 25;
        for(int i = 0; i<mapHeight; i++){
            for(int j=0;j<mapWidth;j++){
                if(map[i][j]){
                    g.fillRect((j* blockSize +1),(i* blockSize +1), blockSize -2, blockSize -2);
                    g.setColor(Color.white);
                    g.fillRect((j* blockSize)+4,(i* blockSize)+4, blockSize -8, blockSize -8);
                    g.setColor(new Color(30,30,30));
                    g.fillRect((j* blockSize)+6,(i* blockSize)+6, blockSize -12, blockSize -12);
                }
            }
        }
        //画当前方块
        for (int i = 0; i < this.nowBlock.length; i++) {
            for (int j = 0; j < this.nowBlock[i].length; j++) {
                if (this.nowBlock[i][j]) {
                    g.fillRect((nowPos.x + j) * blockSize + 1, (nowPos.y + i) * blockSize + 1, blockSize - 2, blockSize - 2);
                    g.setColor(Color.WHITE);
                    g.fillRect((nowPos.x + j) * blockSize + 4, (nowPos.y + i) * blockSize + 4, blockSize - 8, blockSize - 8);
                    g.setColor(new Color(30, 30, 30));
                    g.fillRect((nowPos.x + j) * blockSize + 6, (nowPos.y + i) * blockSize + 6, blockSize - 12, blockSize - 12);
                }
            }
        }
        //设置字体
        g.setFont(font);
        //画下一个方块
        g.drawString("下一个方块：", 690, 50);
        for (int i = 0; i < this.nextBlock.length; i++) {
            for (int j = 0; j < this.nextBlock[i].length; j++) {
                if (this.nextBlock[i][j]) {
                    g.fillRect(710 + j * blockSize + 1, 100 + i * blockSize + 1, blockSize - 2, blockSize - 2);
                    g.setColor(Color.WHITE);
                    g.fillRect(710 + j * blockSize + 4, 100 + i * blockSize + 4, blockSize - 8, blockSize - 8);
                    g.setColor(new Color(30, 30, 30));
                    g.fillRect(710 + j * blockSize + 6, 100 + i * blockSize + 6, blockSize - 12, blockSize - 12);
                }
            }
        }
        //画分数
        g.drawString("当前分数：" + score, 690, 500);
    }

    public int newBlock(){
        return (int)(Shape.shap.length*Math.random());
    }

    //旋转方块
    public boolean[][] rotateBlock(){
        int height = nowBlock.length;
        int width = nowBlock[0].length;
        boolean[][] resultBlock = new boolean[height][width];
        int y = height - 1, x = 0;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                resultBlock[i][j] = nowBlock[y][x];
                y--;
            }
            y = height - 1;
            x++;
        }
        return resultBlock;
    }

    //触碰检测
    public boolean isTouch(Point p,boolean[][] block){
        for(int i = 0;i<block.length;i++){
            for(int j = 0;j<block[i].length;j++){
                if(block[i][j]) {
                    if (p.y + i > mapHeight - 1 || p.x + j < 0 || p.x + j > mapWidth - 1) {
                        return true;
                    }
                    if(map[p.y+i][p.x+j]){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //固定方块
    public void fixBlock(){
        for(int i=0;i<nowBlock.length;i++){
            for(int j=0;j<nowBlock[i].length;j++){
                if(nowBlock[i][j]){
                    map[nowPos.y+i][nowPos.x+j] = true;
                }
            }
        }
        clearLines();
    }

    //消行
    public void clearLines(){
        int lines = 0;
        int start = -1;
        for (int i = 1;i<map.length-1;i++){
            int j;
            for (j=1;j < map[i].length-1;j++){
                if (!map[i][j]){
                    break;
                }
            }
            if (j==map[i].length-1){
                if(start==-1) start = i;
                lines++;
            }
        }
        //System.out.print(start);
        if(start!=-1) {
            for (int i = start; i > 1; i--) {
                map[i + lines - 1] = map[i - 1];
            }
            //System.out.print("test");
            for (int i = 1; i < lines+1; i++) {
                for(int j=1;j<mapWidth-1;j++) map[i][j] = false;
            }
        }
        score+=lines*100;
    }

    //下降方法
    public void down(){
        if(isTouch(new Point(nowPos.x,nowPos.y+1),nowBlock)){
            fixBlock();
            nowBlock = nextBlock;
            nextBlock = Shape.shap[newBlock()];
            nowPos = new Point(8,1);
            repaint();
        }
        else{
            nowPos.y++;
            repaint();
        }
    }

    //下落线程
    public void run(){
        synchronized (lock){
             while (!isOver){
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(isPause){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                down();
            }
        }
    }

    //键盘监听器
    KeyListener keylistener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_DOWN:
                    down();
                    break;
                case KeyEvent.VK_RIGHT:
                    if (isTouch(new Point(nowPos.x+1,nowPos.y),nowBlock)){
                        break;
                    }
                    else {
                        nowPos.x++;
                        break;
                    }
                case KeyEvent.VK_LEFT:
                    if (isTouch(new Point(nowPos.x-1,nowPos.y),nowBlock)){
                        break;
                    }
                    else {
                        nowPos.x--;
                        break;
                    }
                case KeyEvent.VK_UP:
//                    nowShape++;
//                    nowShape = nowShape %4;
//                    nowBlock = Shape.T[nowShape];
                    if (isTouch(new Point(nowPos.x,nowPos.y),rotateBlock())){
                        break;
                    }
                    else {
                        nowBlock = rotateBlock();
                        break;
                    }
            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };
}
