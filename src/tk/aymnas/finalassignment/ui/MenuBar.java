/*
MenuBar.java
프로그램의 메뉴바를 표시한다.
 */

package tk.aymnas.finalassignment.ui;

import tk.aymnas.finalassignment.file.SaveFile;
import tk.aymnas.finalassignment.logic.Tetris;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    public static Tetris tetris;

    MenuBar() {
        JMenu menu = new JMenu("메뉴");
        JMenuItem restartBtn = new JMenuItem("재시작");
        restartBtn.addActionListener(restartAction);
        JMenuItem saveBtn = new JMenuItem("저장");
        saveBtn.addActionListener(saveAction);
        JMenuItem loadBtn = new JMenuItem("불러오기");
        loadBtn.addActionListener(loadAction);
        JMenuItem quitBtn = new JMenuItem("종료");
        quitBtn.addActionListener(quitAction);
        menu.add(restartBtn);
        menu.add(saveBtn);
        menu.add(loadBtn);
        menu.add(quitBtn);

        add(menu);
        setVisible(true);
    }

    private static class LazyHolder {
        public static final MenuBar instance = new MenuBar();
    }

    public static MenuBar getInstance() {
        return MenuBar.LazyHolder.instance;
    }

    private ActionListener restartAction = e -> {
        tetris.isGamePaused = true;
        Tetris newTetris = new Tetris();
        GameScreen.getInstance().logging("게임 재시작");
        tetris.isGamePaused = false;
    };

    private ActionListener saveAction = e -> {
        SaveFile.save();
    };

    private ActionListener loadAction = e -> {
        SaveFile.load();
    };

    private ActionListener quitAction = e -> {
        tetris.isGamePaused = true;
        System.exit(0);
    };
}
