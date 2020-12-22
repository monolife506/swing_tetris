/*
Tetris.java
테트리스 게임을 실제로 진행하는 스레드를 생성하는 클래스
게임의 실제 진행을 관리한다.
 */

package tk.aymnas.finalassignment.logic;

import tk.aymnas.finalassignment.file.SaveFile;
import tk.aymnas.finalassignment.ui.GameScreen;
import tk.aymnas.finalassignment.ui.MenuBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Tetris {
    public static final int GRID_ROW = 20;
    public static final int GRID_COLUMN = 10;
    public int[][] grid;

    public int pieceCycle = 7;
    public ArrayList<Integer> pieceArr;
    public Piece curPiece;
    public Piece nextPiece;

    public int score = 0;
    public int level = 1;
    public int dropInterval = 250;
    public int deletedLines = 0;
    public boolean isGamePaused = true;
    public boolean isGameOver = false;

    private Thread gameThread;

    public Tetris() {
        // grid 배열 초기화
        grid = new int[GRID_ROW + 5][GRID_COLUMN + 2];
        for (int i = 0; i < GRID_ROW + 4; i++) {
            grid[i][0] = -1;
            grid[i][GRID_COLUMN + 1] = -1;
            for (int j = 1; j <= GRID_COLUMN; j++) grid[i][j] = 0;
        }
        for (int i = 0; i < GRID_COLUMN + 2; i++) grid[GRID_ROW + 4][i] = -1;

        // Piece 배열 초기화
        Piece.curGrid = grid;
        pieceArr = new ArrayList<>();
        for (int i = 0; i < 7; i++) pieceArr.add(i);

        // 현재와 다음 Piece 초기화
        curPiece = getPiece();
        nextPiece = getPiece();
        curPiece.drawShape();
        GameScreen.getInstance().render(this);

        // 다른 클래스에 게임의 정보 전송
        Player.tetris = this;
        SaveFile.tetris = this;
        MenuBar.tetris = this;

        // 스레드
        gameThread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(dropInterval);
                    if (!isGamePaused) update();
                }
            } catch (InterruptedException e) {
                // 빈 블록
            }
        });
        gameThread.start();
    }

    // 순서대로 Piece를 얻음
    private Piece getPiece() {
        if (pieceCycle == 7) {
            pieceCycle = 0;
            Collections.shuffle(pieceArr);
        }
        return new Piece(pieceArr.get(pieceCycle++));
    }

    // 갱신
    private void update() {
        if (!isGameOver && !curPiece.drop()) {
            score += 10;
            curPiece.fixed();
            dropCheck();
            curPiece = nextPiece;
            nextPiece = getPiece();
        }
        GameScreen.getInstance().render(this);
    }

    // 블럭이 떨어졌을 때 실행되는 함수
    private void dropCheck() {
        // 꽉 찬 라인 확인
        Queue<Integer> fullLines = new LinkedList<>();
        for (int i = GRID_ROW + 3; i > 3; i--) {
            for (int j = 1; j <= GRID_COLUMN; j++) {
                if (grid[i][j] == 0) {
                    if (!fullLines.isEmpty()) {
                        int firstEmpty = fullLines.poll();
                        for (int k = 1; k <= GRID_COLUMN; k++)
                            grid[firstEmpty][k] = grid[i][k];
                        fullLines.add(i);
                    }
                    break;
                } else if (j == GRID_COLUMN) {
                    fullLines.add(i);
                }
            }
        }

        // 점수 추가
        int lineCnt = fullLines.size();
        switch (lineCnt) {
            case 1:
                score += 100;
                GameScreen.getInstance().logging("싱글: 100점 추가");
                break;
            case 2:
                score += 300;
                GameScreen.getInstance().logging("더블: 300점 추가");
                break;
            case 3:
                score += 600;
                GameScreen.getInstance().logging("트리플: 600점 추가");
                break;
            case 4:
                score += 1000;
                GameScreen.getInstance().logging("테트리스: 1000점 추가");
                break;
        };

        // 레벨 업데이트
        deletedLines += lineCnt;
        if (deletedLines >= 5) {
            String str = "레벨 " + (++level);
            GameScreen.getInstance().logging(str);
            deletedLines = 0;
            switch (level) {
                case 1:
                    dropInterval = 250;
                    break;
                case 2:
                    dropInterval = 200;
                    break;
                case 3:
                    dropInterval = 150;
                    break;
                case 4:
                    dropInterval = 125;
                    break;
                case 5:
                    dropInterval = 100;
                    break;
                default:
                    dropInterval = 80;
                    break;
            }
        }

        // 게임 오버 확인 (화면 바깥에 블럭이 고정된 경우)
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= GRID_COLUMN; j++) {
                if (grid[i][j] < 0 && !isGameOver) {
                    gameThread.interrupt();
                    isGameOver = true;
                    GameScreen.getInstance().logging("게임 오버");
                    GameScreen.getInstance().render(this);
                }
            }
        }
    }
}


