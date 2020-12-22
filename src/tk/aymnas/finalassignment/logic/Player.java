/*
Player.java
플레이어의 입력을 관리한다.
 */


package tk.aymnas.finalassignment.logic;

import tk.aymnas.finalassignment.ui.GameScreen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener {
    public static Tetris tetris;

    @Override
    public void keyPressed(KeyEvent e) {
        if (!tetris.isGameOver) {
            if (!tetris.isGamePaused) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        tetris.curPiece.left();
                        break;
                    case KeyEvent.VK_RIGHT:
                        tetris.curPiece.right();
                        break;
                    case KeyEvent.VK_DOWN:
                        tetris.curPiece.drop();
                        break;
                    case KeyEvent.VK_A:
                        tetris.curPiece.rotateLeft();
                        break;
                    case KeyEvent.VK_D:
                        tetris.curPiece.rotateRight();
                        break;
                    case KeyEvent.VK_SPACE:
                        tetris.curPiece.hardDrop();
                        break;
                    case KeyEvent.VK_P:
                        tetris.isGamePaused = true;
                        GameScreen.getInstance().logging("게임 일시정지");
                        break;
                }
            }
            else {
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    tetris.isGamePaused = false;
                    GameScreen.getInstance().logging("게임 시작");
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}