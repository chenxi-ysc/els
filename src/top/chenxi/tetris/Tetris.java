package top.chenxi.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tetris extends JPanel implements Runnable{
    int blockSize = 25;
    int mapWidth = 20;
    int mapHeight = 30;
    int nowShape = 0;
    Point nowPos = new Point(8,1);
    boolean[][] nowBlock;
    boolean[][] map = new boolean[30][20];

    public Tetris(){
        this.addKeyListener(this.keylistener);
        this.setFocusable(true);
        this.init();
    }

    public void init(){
        //初始化墙
        for (int i = 0; i < mapHeight; i++) {
            map[i][0] = true;
            map[i][mapWidth-1] = true;
            //g.drawRect(0, i * blockSize, blockSize, blockSize);
            //g.drawRect((mapWidth + 1) * blockSize, i *blockSize, blockSize,blockSize);
        }
        for (int i = 0; i < mapWidth; i++) {
            map[0][i] = true;
            map[mapHeight-1][i] = true;
            //g.drawRect((1 + i) * blockSize, mapHeight * blockSize, blockSize,blockSize);
        }
        nowBlock = Shape.T[nowShape];
    }
    public void paint(Graphics g){
        super.paint(g);
        // 画map
        for(int i =0;i<mapHeight;i++){
            for(int j=0;j<mapWidth;j++){
                if(map[i][j]){
                    g.fillRect((j*blockSize)+1,(i*blockSize)+1,blockSize-2,blockSize-2);
                }
            }
        }
        for (int i = 0; i < this.nowBlock.length; i++) {
            for (int j = 0; j < this.nowBlock[i].length; j++) {
                if (this.nowBlock[i][j])
                    g.fillRect((nowPos.x + j) * blockSize+1, (nowPos.y + i) * blockSize+1,
                            blockSize-2, blockSize-2);
            }
        }

    }

    public boolean isTouch(Point p){
        for(int i = 0;i<nowBlock.length;i++){
            for(int j = 0;j<nowBlock[i].length;j++){
                if(nowBlock[i][j]){
                    if(map[p.y+i][p.x+j]){
                        return true;
                    }
                }
                else continue;
            }
        }
        return false;
    }

    public void fixBlock(){
        for(int i=0;i<nowBlock.length;i++){
            for(int j=0;j<nowBlock[i].length;j++){
                if(nowBlock[i][j]){
                    map[nowPos.y+i][nowPos.x+j] = true;
                }
            }
        }
    }

    public void run(){
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isTouch(new Point(nowPos.x,nowPos.y+1))){
                fixBlock();
                nowPos = new Point(8,1);
            }
            else{
                nowPos.y++;
                repaint();
            }
        }
    }
    KeyListener keylistener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_DOWN:
                    if (isTouch(new Point(nowPos.x,nowPos.y+1))){
                        fixBlock();
                        nowPos = new Point(8,1);
                    }
                    else {
                        nowPos.y++;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (isTouch(new Point(nowPos.x+1,nowPos.y))){
                        break;
                    }
                    else {
                        nowPos.x++;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (isTouch(new Point(nowPos.x-1,nowPos.y))){
                        break;
                    }
                    else {
                        nowPos.x--;
                    }
                    break;
                case KeyEvent.VK_UP:
                    nowShape++;
                    nowShape = nowShape %4;
                    nowBlock = Shape.T[nowShape];
                    break;
            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };
}
