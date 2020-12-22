/*
GameScreen.java
프로그램의 화면을 표시하는 클래스
 */


package tk.aymnas.finalassignment.ui;

import tk.aymnas.finalassignment.Main;
import tk.aymnas.finalassignment.logic.Tetris;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private Color[] colorset = new Color[8];

    private JPanel tetrisPanel;
    private JButton[][] tiles;

    private JPanel infoPanel;
    private JPanel nextBlockArea;
    private JButton[][] nextBlock;
    private JTextField scoreField;
    private JTextField levelField;

    private JTextArea loggingArea;

    private GameScreen() {
        // 패널들 초기화
        setLayout(new GridLayout(1, 2));
        setTetrisPanel();
        setInfoPanel();
    }

    private static class LazyHolder {
        public static final GameScreen instance = new GameScreen();
    }

    public static GameScreen getInstance() {
        return LazyHolder.instance;
    }

    // 테트리스 패널
    private void setTetrisPanel() {
        // 변수 초기화
        colorset[0] = Color.LIGHT_GRAY;
        colorset[1] = new Color(0, 255, 255);
        colorset[2] = Color.YELLOW;
        colorset[3] = Color.RED;
        colorset[4] = Color.GREEN;
        colorset[5] = Color.BLUE;
        colorset[6] = Color.ORANGE;
        colorset[7] = new Color(255, 0, 255);

        tetrisPanel = new JPanel();
        tetrisPanel.setLayout(new GridLayout(Tetris.GRID_ROW, Tetris.GRID_COLUMN));
        tiles = new JButton[Tetris.GRID_ROW][Tetris.GRID_COLUMN];
        for (int i = 0; i < Tetris.GRID_ROW; i++) {
            for (int j = 0; j < Tetris.GRID_COLUMN; j++) {
                tiles[i][j] = new JButton();
                tiles[i][j].setEnabled(false);
                tetrisPanel.add(tiles[i][j]);
            }
        }
        add(tetrisPanel);
    }

    // 정보 패널
    private void setInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 0));

        // 다음 블럭
        JPanel northPanel = new JPanel();
        JLabel northLabel = new JLabel("다음 블럭");
        northLabel.setFont(new Font(northLabel.getFont().getName(), northLabel.getFont().getStyle(), 20));
        nextBlockArea = new JPanel();
        nextBlockArea.setLayout(new GridLayout(4, 4));
        nextBlock = new JButton[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                nextBlock[i][j] = new JButton();
                nextBlock[i][j].setEnabled(false);
                nextBlock[i][j].setBackground(colorset[0]);
                nextBlockArea.add(nextBlock[i][j]);
            }
        }
        nextBlockArea.setPreferredSize(new Dimension(150, 150));

        northPanel.add(northLabel);
        northPanel.add(nextBlockArea);
        infoPanel.add(northPanel);

        // 점수와 레벨
        JPanel centerPanel = new JPanel();
        JLabel centerLabel1 = new JLabel("점수");
        centerLabel1.setFont(new Font(northLabel.getFont().getName(), northLabel.getFont().getStyle(), 20));
        scoreField = new JTextField();
        scoreField.setEnabled(false);
        scoreField.setPreferredSize(new Dimension(200, 30));
        JLabel centerLabel2 = new JLabel("레벨");
        centerLabel2.setFont(new Font(northLabel.getFont().getName(), northLabel.getFont().getStyle(), 20));
        levelField = new JTextField();
        levelField.setEnabled(false);
        levelField.setPreferredSize(new Dimension(200, 30));

        centerPanel.add(centerLabel1);
        centerPanel.add(scoreField);
        centerPanel.add(centerLabel2);
        centerPanel.add(levelField);
        infoPanel.add(centerPanel);

        // 로그 패널
        JPanel southPanel = new JPanel();
        loggingArea = new JTextArea();
        loggingArea.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(loggingArea);
        scrollPane.setVisible(true);
        scrollPane.setPreferredSize(new Dimension(250, 150));

        southPanel.add(scrollPane);
        infoPanel.add(southPanel);

        add(infoPanel);
    }

    // 게임 화면을 갱신한다.
    public void render(Tetris tetris) {

        // 테트리스 패널 갱신
        for (int i = 4; i < Tetris.GRID_ROW + 4; i++) {
            for (int j = 1; j <= Tetris.GRID_COLUMN; j++) {
                tiles[i - 4][j - 1].setBackground(colorset[Math.abs(tetris.grid[i][j])]);
            }
        }

        // 다음 블럭 갱신
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                nextBlock[i][j].setBackground(colorset[0]);
            }
        }
        for (int i = 0; i < tetris.nextPiece.shapeSize; i++) {
            for (int j = 0; j < tetris.nextPiece.shapeSize; j++) {
                if (tetris.nextPiece.shape[i][j] != 0)
                    nextBlock[i][j].setBackground(colorset[Math.abs(tetris.nextPiece.shape[i][j])]);
            }
        }

        // 점수와 레벨 갱신
        scoreField.setText(Integer.toString(tetris.score));
        levelField.setText(Integer.toString(tetris.level));
    }

    // 로그 화면에 내용을 출력한다.
    public void logging(String str) {
        loggingArea.append(str + '\n');
        loggingArea.setCaretPosition(loggingArea.getDocument().getLength());
    }
}
