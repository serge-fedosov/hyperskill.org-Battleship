package battleship;

import java.util.Scanner;

public class Battleship {

    private final char[][][] field = new char[4][10][10];
    private final String[] ship = new String[] {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
    private final int[] shipSize = new int[] {5, 4, 3, 3, 2};
    private int[][][] shipCoordinates = new int[2][5][];
    private int[][][] shipSank = new int[2][5][];

    char SYM_SHIP = 'O';
    char SYM_MISS = 'M';
    char SYM_HIT = 'X';
    char SYM_UNKNOWN = '~';

    int PLAYER1_FIELD = 0;
    int PLAYER2_FIELD = 1;
    int PLAYER1_SHOT = 2;
    int PLAYER2_SHOT = 3;

    public void init() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[PLAYER1_SHOT][i][j] = SYM_UNKNOWN;
                field[PLAYER1_FIELD][i][j] = SYM_UNKNOWN;
                field[PLAYER2_SHOT][i][j] = SYM_UNKNOWN;
                field[PLAYER2_FIELD][i][j] = SYM_UNKNOWN;
            }
        }

        shipCoordinates[PLAYER1_FIELD][0] = new int[5];
        shipCoordinates[PLAYER1_FIELD][1] = new int[4];
        shipCoordinates[PLAYER1_FIELD][2] = new int[3];
        shipCoordinates[PLAYER1_FIELD][3] = new int[3];
        shipCoordinates[PLAYER1_FIELD][4] = new int[2];
        shipCoordinates[PLAYER2_FIELD][0] = new int[5];
        shipCoordinates[PLAYER2_FIELD][1] = new int[4];
        shipCoordinates[PLAYER2_FIELD][2] = new int[3];
        shipCoordinates[PLAYER2_FIELD][3] = new int[3];
        shipCoordinates[PLAYER2_FIELD][4] = new int[2];

        shipSank[PLAYER1_FIELD][0] = new int[5];
        shipSank[PLAYER1_FIELD][1] = new int[4];
        shipSank[PLAYER1_FIELD][2] = new int[3];
        shipSank[PLAYER1_FIELD][3] = new int[3];
        shipSank[PLAYER1_FIELD][4] = new int[2];
        shipSank[PLAYER2_FIELD][0] = new int[5];
        shipSank[PLAYER2_FIELD][1] = new int[4];
        shipSank[PLAYER2_FIELD][2] = new int[3];
        shipSank[PLAYER2_FIELD][3] = new int[3];
        shipSank[PLAYER2_FIELD][4] = new int[2];
    }

    private void showField(int value) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char ch = 'A';
        for (int i = 0; i < 10; i++) {
            System.out.print(ch++);
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + field[value][i][j]);
            }
            System.out.println();
        }
    }

    private boolean isPositionCorrect(int value, int y, int x) {
        char o = SYM_SHIP;
        return (y - 1 < 0 || x - 1 < 0 || field[value][y - 1][x - 1] != o) && (y - 1 < 0 || field[value][y - 1][x] != o)
                && (y - 1 < 0 || x + 1 > 9 || field[value][y - 1][x + 1] != o) && (x - 1 < 0 || field[value][y][x - 1] != o)
                && (x + 1 > 9 || field[value][y][x + 1] != o) && (y + 1 > 9 || x - 1 < 0 || field[value][y + 1][x - 1] != o)
                && (y + 1 > 9 || field[value][y + 1][x] != o) && (y + 1 > 9 || x + 1 > 9 || field[value][y + 1][x + 1] != o);
    }

    private void arrangeShips(int value) {
        Scanner scanner = new Scanner(System.in);

        showField(value);
        for (int i = 0; i < ship.length; i++) {

            System.out.println("\nEnter the coordinates of the " + ship[i] + " (" + shipSize[i] + " cells):\n");

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
                    System.out.println("\nError! Wrong length of the " + this.ship[i] + "! Try again:\n");
                    error = true;
                } else {
                    if (dY == 1) {
                        for (int j = xMin; j <= xMax; j++) {
                            if (!isPositionCorrect(value, y0, j)) {
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                                error = true;
                                break;
                            }
                        }
                    } else {
                        for (int j = yMin; j <= yMax; j++) {
                            if (!isPositionCorrect(value, j, x0)) {
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
                    field[value][y0][j] = SYM_SHIP;
                    shipCoordinates[value][i][j - xMin] = y0 * 10 + j;
                }
            } else {
                for (int j = yMin; j <= yMax; j++) {
                    field[value][j][x0] = SYM_SHIP;
                    shipCoordinates[value][i][j - yMin] = j * 10 + y0;
                }
            }

            showField(value);
        }
    }

    private boolean isGameOver(int value) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (field[value][i][j] == SYM_SHIP) {
                    return false;
                }
            }
        }

        return true;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nPlayer 1, place your ships on the game field\n");
        arrangeShips(PLAYER1_FIELD);

        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();

        System.out.println("\nPlayer 2, place your ships to the game field\n");
        arrangeShips(PLAYER2_FIELD);

        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();

        int player = PLAYER1_FIELD;
        int playerShot = PLAYER2_SHOT;
        int playerField = PLAYER2_FIELD;

        boolean gameOver;
        do {
            gameOver = false;

            showField(playerShot);
            System.out.println("---------------------");
            showField(player);

            if (player == PLAYER1_FIELD) {
                System.out.println("\nPlayer 1, it's your turn:");
            } else {
                System.out.println("\nPlayer 2, it's your turn:");
            }

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

            if (field[playerField][y0][x0] == SYM_SHIP || field[playerField][y0][x0] == SYM_HIT) {
                field[playerField][y0][x0] = SYM_HIT;
                field[playerShot][y0][x0] = SYM_HIT;
                gameOver = isGameOver(playerField);
                if (gameOver) {
                    System.out.println("\nYou sank the last ship. You won. Congratulations!\n");
                } else {

                    boolean sank = false;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < shipCoordinates[PLAYER1_FIELD][i].length; j++) {
                            if (shipCoordinates[PLAYER1_FIELD][i][j] == y0 * 10 + x0) {
                                shipSank[PLAYER1_FIELD][i][j] = 1;

                                int sum = 0;
                                for (int k = 0; k < shipSank[PLAYER1_FIELD][i].length; k++) {
                                    sum += shipSank[PLAYER1_FIELD][i][k];
                                }

                                if (sum == shipSank[PLAYER1_FIELD][i].length) {
                                    sank = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (sank) {
                        System.out.println("\nYou sank a ship!");
                    } else {
                        System.out.println("\nYou hit a ship!");
                    }

                    System.out.println("Press Enter and pass the move to another player\n");
                    scanner.nextLine();
                }
            } else {
                field[playerField][y0][x0] = SYM_MISS;
                field[playerShot][y0][x0] = SYM_MISS;
                gameOver = isGameOver(playerField);
                if (gameOver) {
                    System.out.println("\nYou sank the last ship. You won. Congratulations!\n");
                } else {
                    System.out.println("\nYou missed!");
                    System.out.println("Press Enter and pass the move to another player\n");
                    scanner.nextLine();
                }
            }

            if (player == PLAYER1_FIELD) {
                player = PLAYER2_FIELD;
                playerShot = PLAYER1_SHOT;
                playerField = PLAYER1_FIELD;
            } else {
                player = PLAYER1_FIELD;
                playerShot = PLAYER2_SHOT;
                playerField = PLAYER2_FIELD;
            }

        } while (!gameOver);

//        showField();
    }
}
