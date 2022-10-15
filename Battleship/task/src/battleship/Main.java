package battleship;

public class Main {

    public static void main(String[] args) {
        Battleship battleship = new Battleship();
        battleship.init();
        battleship.arrangeShips();
        battleship.startGame();
    }
}
