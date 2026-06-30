package game;

public class GameAI {
    public static String getOpponentMove(String[] moves) {
        int moveNum = 0;
        for (int i = 0; i < moves.length; i++) {
            if (!moves[i].equals("------")) {
                moveNum++;
            }
        }
        String opponentMove = moves[(int) (Math.random() * moveNum)];
        GameInterface.setOpponentMove(opponentMove);
        return opponentMove;
    }
    //Assumes that all battle moves are before any blank moves
}
