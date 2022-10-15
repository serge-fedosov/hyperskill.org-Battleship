package battleship;

import java.util.Scanner;

public class Battleship {

    private final char[][] field = new char[10][10];
    private final String[] ships = new String[] {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
    private final int[] shipSize = new int[] {5, 4, 3, 3, 2};

    public void init() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = '~';
            }
        }
    }

    private void showField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char ch = 'A';
        for (int i = 0; i < 10; i++) {
            System.out.print(ch++);
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + field[i][j]);
            }
            System.out.println();
        }
    }


    private boolean isPositionCorrect(int y, int x) {
        char o = 'O';
        return (y - 1 < 0 || x - 1 < 0 || field[y - 1][x - 1] != o) && (y - 1 < 0 || field[y - 1][x] != o)
                && (y - 1 < 0 || x + 1 > 9 || field[y - 1][x + 1] != o) && (x - 1 < 0 || field[y][x - 1] != o)
                && (x + 1 > 9 || field[y][x + 1] != o) && (y + 1 > 9 || x - 1 < 0 || field[y + 1][x - 1] != o)
                && (y + 1 > 9 || field[y + 1][x] != o) && (y + 1 > 9 || x + 1 > 9 || field[y + 1][x + 1] != o);
    }

    public void arrangeShips() {
        Scanner scanner = new Scanner(System.in);

        showField();
        for (int i = 0; i < ships.length; i++) {

            System.out.println("\nEnter the coordinates of the " + ships[i] + " (" + shipSize[i] + " cells):\n");

            int x0, x1, xMin, xMax, dX;
            int y0, y1, yMin, yMax, dY;
            boolean error;
            do {
                error = false;

                String[] ship = scanner.nextLine().toUpperCase().split(" ");
                System.out.println();

                y0 = ship[0].charAt(0) - 'A';
                x0 = Integer.parseInt(ship[0].substring(1)) - 1;

                y1 = ship[1].charAt(0) - 'A';
                x1 = Integer.parseInt(ship[1].substring(1)) - 1;

                xMin = Math.min(x0, x1);
                xMax = Math.max(x0, x1);

                yMin = Math.min(y0, y1);
                yMax = Math.max(y0, y1);

                dX = xMax - xMin + 1;
                dY = yMax - yMin + 1;

                if (dX != 1 && dY != 1) {
                    System.out.println("Error! Wrong ship location! Try again:\n");
                    error = true;
                } else if (!(dX == 1 && dY == shipSize[i] || dY == 1 && dX == shipSize[i])) {
                    System.out.println("\nError! Wrong length of the " + ships[i] + "! Try again:\n");
                    error = true;
                } else {
                    if (dY == 1) {
                        for (int j = xMin; j <= xMax; j++) {
                            if (!isPositionCorrect(y0, j)) {
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                                error = true;
                                break;
                            }
                        }
                    } else {
                        for (int j = yMin; j <= yMax; j++) {
                            if (!isPositionCorrect(j, x0)) {
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                                error = true;
                                break;
                            }
                        }
                    }
                }

            } while (error);


            if (dY == 1) {
                for (int j = xMin; j <= xMax; j++) {
                    field[y0][j] = 'O';
                }
            } else {
                for (int j = yMin; j <= yMax; j++) {
                    field[j][x0] = 'O';
                }
            }

            showField();
        }
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nThe game starts!\n");
        showField();
        System.out.println("\nTake a shot!\n");

        int x0, y0;
        boolean error;
        do {
            error = false;

            String shot = scanner.nextLine().trim().toUpperCase();
            System.out.println();

            y0 = shot.charAt(0) - 'A';
            x0 = Integer.parseInt(shot.substring(1)) - 1;

            if (y0 < 0 || y0 > 9 || x0 < 0 || x0 > 9) {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
                error = true;
            }

        } while (error);

        if (field[y0][x0] == 'O') {
            field[y0][x0] = 'X';
            showField();
            System.out.println("\nYou hit a ship!\n");
        } else {
            field[y0][x0] = 'M';
            showField();
            System.out.println("\nYou missed!");
        }
    }
}
