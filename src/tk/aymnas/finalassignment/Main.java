/*
Main.java
게임을 실행하는 창을 띄우고 게임을 실행하는 함수
프로그램의 시작점이다.
 */

package tk.aymnas.finalassignment;

import tk.aymnas.finalassignment.logic.Player;
import tk.aymnas.finalassignment.logic.Tetris;
import tk.aymnas.finalassignment.ui.GameScreen;
import tk.aymnas.finalassignment.ui.MenuBar;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("테트리스");

        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 설명서
        GameScreen.getInstance().logging("좌우 아래 이동: 방향키");
        GameScreen.getInstance().logging("시계/반시계 방향 회전: A/D");
        GameScreen.getInstance().logging("블럭 바닥으로 떨어트리키: SPACE");
        GameScreen.getInstance().logging("게임 시작/일시정지: P");
        GameScreen.getInstance().logging("저장 파일은 현재 경로에 savefile이라는 파일로 저장됩니다.");
        GameScreen.getInstance().logging("파일을 불러오는 경우 savefile을 대체하시면 됩니다.");
        // 메뉴 추가
        setJMenuBar(MenuBar.getInstance());
        // 게임 화면 추가
        add(GameScreen.getInstance());
        // 키 리스너 추가
        addKeyListener(new Player());

        setVisible(true);
    }

    public static void main(String[] args) {
        // 테트리스 게임 시작
        Tetris tetris = new Tetris();
        // UI 시작
        new Main();
    }
}
