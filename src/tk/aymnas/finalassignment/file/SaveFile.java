/*
SaveFile.java
현재 Tetris 게임의 상태를 저장하고 불러올 수 있게 해주는 클래스
 */

package tk.aymnas.finalassignment.file;

import tk.aymnas.finalassignment.logic.Piece;
import tk.aymnas.finalassignment.logic.Tetris;
import tk.aymnas.finalassignment.ui.GameScreen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveFile {
    public static Tetris tetris; // 참조할 Tetris 객체
    private final static String filename = "savefile"; // 세이브/로드할 파일의 이름

    public static void save() {
        File file = null;
        FileWriter fw = null;
        tetris.isGamePaused = true;

        if (tetris.isGameOver) {
            GameScreen.getInstance().logging("게임 종료 이후에는 저장할 수 없습니다.");
            return;
        }

        try {
            file = new File(".\\" + filename);
            if (!file.exists()) file.createNewFile();
            if (!file.isFile()) throw new IOException();

            fw = new FileWriter(file, false);

            // grid 정보 저장
            for (int i = 0; i < Tetris.GRID_ROW + 5; i++) {
                for (int j = 0; j < Tetris.GRID_COLUMN + 2; j++) {
                    fw.write(Integer.toString(tetris.grid[i][j]));
                    fw.write(' ');
                }
            }

            // 블럭의 순서 정보 저장
            fw.write(Integer.toString(tetris.pieceCycle));
            fw.write(' ');
            for (int i = 0; i < 7; i++) {
                fw.write(Integer.toString(tetris.pieceArr.get(i)));
                fw.write(' ');
            }

            // 현재 블럭의 정보 저장
            fw.write(Integer.toString(tetris.curPiece.kind));
            fw.write(' ');
            for (int i = 0; i < tetris.curPiece.shapeSize; i++) {
                for (int j = 0; j < tetris.curPiece.shapeSize; j++) {
                    fw.write(Integer.toString(tetris.curPiece.shape[i][j]));
                    fw.write(' ');
                }
            }
            fw.write(Integer.toString(tetris.curPiece.curRow));
            fw.write(' ');
            fw.write(Integer.toString(tetris.curPiece.curCol));
            fw.write(' ');

            // 다음 블럭의 정보 저장
            fw.write(Integer.toString(tetris.nextPiece.kind));
            fw.write(' ');
            for (int i = 0; i < tetris.nextPiece.shapeSize; i++) {
                for (int j = 0; j < tetris.nextPiece.shapeSize; j++) {
                    fw.write(Integer.toString(tetris.nextPiece.shape[i][j]));
                    fw.write(' ');
                }
            }
            fw.write(Integer.toString(tetris.nextPiece.curRow));
            fw.write(' ');
            fw.write(Integer.toString(tetris.nextPiece.curCol));
            fw.write(' ');

            // 기타 게임 정보 저장
            fw.write(Integer.toString(tetris.score));
            fw.write(' ');
            fw.write(Integer.toString(tetris.level));
            fw.write(' ');
            fw.write(Integer.toString(tetris.dropInterval));
            fw.write(' ');
            fw.write(Integer.toString(tetris.deletedLines));
            fw.write(' ');

            fw.close();
            GameScreen.getInstance().logging("현재 상태를 저장했습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        File file = null;
        Scanner sc = null;
        tetris.isGamePaused = true;

        try {
            file = new File(".\\saveFile");
            if (!file.exists() || !file.isFile()) throw new IOException();

            sc = new Scanner(file);

            // grid 정보 불러오기
            for (int i = 0; i < Tetris.GRID_ROW + 5; i++) {
                for (int j = 0; j < Tetris.GRID_COLUMN + 2; j++) {
                    tetris.grid[i][j] = sc.nextInt();
                }
            }

            // 블럭의 순서 정보 불러오기
            tetris.pieceCycle = sc.nextInt();
            for (int i = 0; i < 7; i++) {
                tetris.pieceArr.set(i, sc.nextInt());
            }

            // 현재 블럭의 정보 불러오기
            Piece curPiece = new Piece(sc.nextInt());
            for (int i = 0; i < curPiece.shapeSize; i++) {
                for (int j = 0; j < curPiece.shapeSize; j++) {
                    curPiece.shape[i][j] = sc.nextInt();
                }
            }
            curPiece.curRow = sc.nextInt();
            curPiece.curCol = sc.nextInt();
            tetris.curPiece = curPiece;

            // 다음 블럭의 정보 불러오기
            Piece nextPiece = new Piece(sc.nextInt());
            for (int i = 0; i < nextPiece.shapeSize; i++) {
                for (int j = 0; j < nextPiece.shapeSize; j++) {
                    nextPiece.shape[i][j] = sc.nextInt();
                }
            }
            nextPiece.curRow = sc.nextInt();
            nextPiece.curCol = sc.nextInt();
            tetris.nextPiece = nextPiece;

            // 기타 정보 불러오기
            tetris.score = sc.nextInt();
            tetris.level = sc.nextInt();
            tetris.dropInterval = sc.nextInt();
            ;
            tetris.deletedLines = sc.nextInt();
            ;
            tetris.isGameOver = false;

            GameScreen.getInstance().render(tetris);
            GameScreen.getInstance().logging("파일의 상태를 불러왔습니다.");
            sc.close();
        } catch (IOException e) {
            GameScreen.getInstance().logging("불러올 파일이 존재하지 않습니다.");
            e.printStackTrace();
        }
    }
}
