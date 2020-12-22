/*
Piece.java
테트리스 블럭에 대한 정보를 나타낸다.
 */


package tk.aymnas.finalassignment.logic;

public class Piece {
    public static int[][] curGrid;

    public int kind;
    public int shapeSize;
    public int[][] shape;
    public int curRow = 0;
    public int curCol = 1;

    public Piece(int kinds) {
        // 블럭의 모양을 초기화한다.
        kind = kinds;
        switch (kinds) {
            case 0:
                shape = new int[][]{{1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}};
                shapeSize = 4;
                break;
            case 1:
                shape = new int[][]{{2, 2}, {2, 2}};
                shapeSize = 2;
                break;
            case 2:
                shape = new int[][]{{3, 3, 0}, {0, 3, 3}, {0, 0, 0}};
                shapeSize = 3;
                break;
            case 3:
                shape = new int[][]{{0, 4, 4}, {4, 4, 0}, {0, 0, 0}};
                shapeSize = 3;
                break;
            case 4:
                shape = new int[][]{{0, 5, 0}, {0, 5, 0}, {5, 5, 0}};
                shapeSize = 3;
                break;
            case 5:
                shape = new int[][]{{0, 6, 0}, {0, 6, 0}, {0, 6, 6}};
                shapeSize = 3;
                break;
            case 6:
                shape = new int[][]{{0, 7, 0}, {7, 7, 7}, {0, 0, 0}};
                shapeSize = 3;
                break;
        }

        // 랜덤으로 방향 결정
        for (int i = 0; i < (int) (Math.random() * 4); i++) rotateLeft();
        // 랜덤으로 열 결정
        for (int i = 0; i < (int) (Math.random() * (Tetris.GRID_COLUMN - shapeSize + 2)); i++) right();
    }

    public boolean left() {
        return checkShape(shape, curRow, curCol - 1);
    }

    public boolean right() {
        return checkShape(shape, curRow, curCol + 1);
    }

    public boolean drop() {
        return checkShape(shape, curRow + 1, curCol);
    }

    public void hardDrop() {
        do {
            // 가능할 때 까지 drop한다.
        } while (drop());
    }

    public boolean rotateLeft() {
        // 반시계방향으로 90도 회전
        int[][] rotated = new int[shapeSize][shapeSize];
        for (int i = 0; i < shapeSize; i++) {
            for (int j = 0; j < shapeSize; j++) {
                rotated[shapeSize - (j + 1)][i] = shape[i][j];
            }
        }

        return checkShape(rotated, curRow, curCol);
    }

    public boolean rotateRight() {
        //시계방향으로 90도 회전
        int[][] rotated = new int[shapeSize][shapeSize];
        for (int i = 0; i < shapeSize; i++) {
            for (int j = 0; j < shapeSize; j++) {
                rotated[j][shapeSize - (i + 1)] = shape[i][j];
            }
        }

        return checkShape(rotated, curRow, curCol);
    }

    public boolean drawShape() {
        return checkShape(shape, curRow, curCol);
    }

    private boolean checkShape(int[][] newShape, int newRow, int newCol) {
        // 가능한 배치인지 확인
        for (int i = 0; i < shapeSize; i++) {
            for (int j = 0; j < shapeSize; j++) {
                if (newShape[i][j] != 0 && curGrid[newRow + i][newCol + j] < 0) {
                    return false;
                }
            }
        }

        // 가능한 배치이면 갱신
        for (int i = 0; i < shapeSize; i++) {
            for (int j = 0; j < shapeSize; j++) {
                if (shape[i][j] != 0)
                    curGrid[curRow + i][curCol + j] = 0;
            }
        }

        for (int i = 0; i < shapeSize; i++) {
            for (int j = 0; j < shapeSize; j++) {
                if (newShape[i][j] != 0)
                    curGrid[newRow + i][newCol + j] = newShape[i][j];
            }
        }

        curRow = newRow;
        curCol = newCol;
        shape = newShape;
        return true;
    }

    public void fixed() {
        for (int i = 0; i < shapeSize; i++) {
            for (int j = 0; j < shapeSize; j++) {
                if (shape[i][j] != 0)
                    curGrid[curRow + i][curCol + j] = -shape[i][j];
            }
        }
    }
}
